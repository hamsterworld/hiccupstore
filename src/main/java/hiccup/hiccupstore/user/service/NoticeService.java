package hiccup.hiccupstore.user.service;

import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.commonutil.file.UploadFile;
import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.NoticeDto;
import hiccup.hiccupstore.user.dto.NoticeUpdateDto;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.dto.board.Board1vs1Form;
import hiccup.hiccupstore.user.dto.board.BoardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeService {

    private final UserMapper userMapper;
    private final FindSecurityContext findSecurityContext;

    public NoticeDto getNoticeBoardSee(Integer noticeBoardId){

        NoticeDto noticeDto = userMapper.getnoticeBoardOne(noticeBoardId);

        return noticeDto;

    }

    public List<NoticeDto> getNoticeBoardList(Integer page, Integer pageSize) {

        return userMapper.getNoticeDtoListPaging((page-1)*10,pageSize);

    }

    public Integer getTotalNoticeCount() {
        return userMapper.getTotalNoticeCount();
    }

    public void SaveNoticeBoard(Board1vs1Form board1vs1Form, List<UploadFile> storeImageFiles) {

        userMapper.saveNotice(board1vs1Form.getBoardtitle(),board1vs1Form.getBoardcontent(),board1vs1Form.getBoardcategory(),
                storeImageFiles.get(0).getStoreFileName());
    }

    public void deleteNoticeBoard(Integer noticedId) {
        userMapper.deleteNotice(noticedId);
    }

    public void updateNoticeBoard(Integer noticeId, NoticeUpdateDto noticeUpdateDto,List<UploadFile> storeImageFiles) {

        userMapper.updateNotice(noticeId,noticeUpdateDto.
                getBoardtitle(),
                noticeUpdateDto.getBoardcontent(),
                storeImageFiles.get(0).getStoreFileName());

    }

    public void updateNoticeBoardNotImageUpdate(Integer noticeId, NoticeUpdateDto noticeUpdateDto) {
        userMapper.updateNoticeNotImageUpdate(noticeId,noticeUpdateDto.getBoardtitle(), noticeUpdateDto.getBoardcontent());
    }

    public void updateNoticeBoardDeleteImageUpdate(Integer noticeId, NoticeUpdateDto noticeUpdateDto) {
        userMapper.updateNoticeDeleteImageUpdate(noticeId,noticeUpdateDto.getBoardtitle(), noticeUpdateDto.getBoardcontent(),null);
    }

    public Integer SearchNoticeBoardCountBySearchNoticeContent(String searchNoticeCategory, String searchNoticeContent){
        return userMapper.searchNoticeBoardCountBySearchNoticeContent(searchNoticeCategory,searchNoticeContent);
    }

    public List<NoticeDto> SearchNoticeBoardBySearchNoticeContent(String searchNoticeCategory, String searchNoticeContent,Integer page,Integer pagesize) {
        return userMapper.searchNoticeBoardBySearchNoticeContent(searchNoticeCategory,searchNoticeContent,page,pagesize);
    }
}
