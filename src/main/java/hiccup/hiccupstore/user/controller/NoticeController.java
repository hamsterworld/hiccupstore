package hiccup.hiccupstore.user.controller;

import hiccup.hiccupstore.commonutil.file.UploadFile;
import hiccup.hiccupstore.user.dto.NoticeDto;
import hiccup.hiccupstore.user.dto.NoticeUpdateDto;
import hiccup.hiccupstore.user.dto.board.Board1vs1Form;
import hiccup.hiccupstore.user.dto.board.User1vs1BoardDto;
import hiccup.hiccupstore.user.service.NoticeService;
import hiccup.hiccupstore.user.util.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final Integer pageSize = 10;
    private final hiccup.hiccupstore.commonutil.aws.awsS3Service awsS3Service;

    @GetMapping("/notice")
    public String notice(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(required = false) String SearchNoticeContent,
                         @RequestParam(required = false) String SearchNoticeCategory,
                         Model model){

        if(SearchNoticeCategory != null && SearchNoticeContent != null){
            Integer totalNoticeCount = noticeService.SearchNoticeBoardCountBySearchNoticeContent(SearchNoticeCategory, SearchNoticeContent);
            List<NoticeDto> noticeDtoList = noticeService.SearchNoticeBoardBySearchNoticeContent(SearchNoticeCategory, SearchNoticeContent, page-1, pageSize);

            Paging paging = new Paging(totalNoticeCount,page-1,pageSize);

            model.addAttribute("paging",paging);
            model.addAttribute("page",page);
            model.addAttribute("noticedtolist",noticeDtoList);
            model.addAttribute("noticecategory",SearchNoticeCategory);
            model.addAttribute("noticecontent",SearchNoticeContent);

            return "notice";
        }

        List<NoticeDto> noticeDtoList = noticeService.getNoticeBoardList(page,pageSize);
        Integer totalNoticeCount = noticeService.getTotalNoticeCount();

        Paging paging = new Paging(totalNoticeCount,page-1,pageSize);

        model.addAttribute("paging",paging);
        model.addAttribute("page",page);
        model.addAttribute("noticedtolist",noticeDtoList);
        model.addAttribute("noticecategory","boardtitle");

        return "notice";
    }

    @GetMapping("/noticesee/{noticeBoardId}")
    public String noticeSee(@PathVariable Integer noticeBoardId, @RequestParam(defaultValue = "1") Integer page, Model model){

        NoticeDto noticeBoardSee = noticeService.getNoticeBoardSee(noticeBoardId);
        NoticeDto noticeBoardsee = awsS3Service.getStoredNoticeFileUrl("noticeboard", noticeBoardSee);
//        List<User1vs1BoardDto> user1vs1BoardDtoList = myPage1vs1Service.SeeBoard(boardId);
//        List<User1vs1BoardDto> user1vs1Boards = awsS3Service.getStoredFileUrl("user1vs1image",user1vs1BoardDtoList);

        model.addAttribute("noticedto",noticeBoardsee);
        model.addAttribute("page",page);
        checkIfImageIsOrNot(model, noticeBoardsee);
        return "noticesee";
    }

    @GetMapping("/notice/write")
    public String noticeWrite(){
        return "noticewrite";
    }

    @PostMapping("/notice/noticewrite")
    public String noticeWritePost(@ModelAttribute Board1vs1Form board1vs1Form  , RedirectAttributes redirectAttributes) throws IOException {

        board1vs1Form.setBoardcategory("새소식");
        List<UploadFile> storeImageFiles = awsS3Service.uploadImage("noticeboard",board1vs1Form.getImageFiles());
        noticeService.SaveNoticeBoard(board1vs1Form,storeImageFiles);

        redirectAttributes.addFlashAttribute("success","success");

        return "redirect:/notice";
    }

    @GetMapping("/notice/update/{noticeid}")
    public String noticeUpdate(@PathVariable Integer noticeid,Model model){

        NoticeDto noticeBoardSee = noticeService.getNoticeBoardSee(noticeid);
        noticeBoardSee = awsS3Service.getStoredNoticeFileUrl("noticeboard", noticeBoardSee);

        model.addAttribute("noticedto",noticeBoardSee);
        if(noticeBoardSee.getImagename() != null ){
            model.addAttribute("image",true);
        } else {
            model.addAttribute("image",false);
        }
        return "noticeupdate";
    }

    @PostMapping("/notice/update/{noticeid}")
    public String noticeUpdatePost(@PathVariable Integer noticeid, @ModelAttribute NoticeUpdateDto noticeUpdateDto,RedirectAttributes redirectAttributes) throws IOException {

//        List<UploadFile> storeImageFiles = fileStore.storeFiles(noticeUpdateDto.getImageFiles());

        List<UploadFile> storeImageFiles = awsS3Service.uploadImage("noticeboard",noticeUpdateDto.getImageFiles());
        /** 이미지 삭제 안했을때 */
        if(noticeUpdateDto.getDeleteImageFiles() == null){

            if("".equals(noticeUpdateDto.getImagename())){
                noticeService.updateNoticeBoard(noticeid,noticeUpdateDto,storeImageFiles);
            } else {
                /** 이미지 파일을 추가하면
                 *  이미지가 있었을때.
                 * */
                if(storeImageFiles.size() != 0){
                    redirectAttributes.addFlashAttribute("maximage1","maximage1");
                    return "redirect:/notice/update/"+noticeid;
                } /** 이미지 파일을 추가안했으면 */ else {
                    noticeService.updateNoticeBoardNotImageUpdate(noticeid,noticeUpdateDto);
                }
            }
            /** 이미지 파일을 추가하면
             *  이미지가 있었을때.
             * */
        } else {
            /** 이미지 파일 교체  안했을때 */
            if(storeImageFiles.size() == 0){
                //fileStore.deleteFile(fileStore.getFullPath(noticeUpdateDto.getDeleteImageFiles()));
                awsS3Service.deleteFile(noticeUpdateDto.getDeleteImageFiles(),"noticeboard");
                noticeService.updateNoticeBoardDeleteImageUpdate(noticeid,noticeUpdateDto);
            } /** 이미지 파일 교체 했을때 */else{
                //fileStore.deleteFile(fileStore.getFullPath(noticeUpdateDto.getDeleteImageFiles()));
                awsS3Service.deleteFile(noticeUpdateDto.getDeleteImageFiles(),"noticeboard");
                noticeService.updateNoticeBoard(noticeid,noticeUpdateDto,storeImageFiles);
            }
        }
        redirectAttributes.addFlashAttribute("update_success","update_success");
        return "redirect:/notice";
    }

    @GetMapping("/notice/delete/{noticeid}")
    public String noticeDelete(@PathVariable Integer noticeid,RedirectAttributes redirectAttributes){

        noticeService.deleteNoticeBoard(noticeid);
        redirectAttributes.addFlashAttribute("del_success","del_success");

        return "redirect:/notice";
    }

    private void checkIfImageIsOrNot(Model model, NoticeDto noticeDto) {
        if(noticeDto.getImagename() != null ){
            model.addAttribute("image",true);
        } else {
            model.addAttribute("image",false);
        }
    }

}
