package hiccup.hiccupstore.board.controller;

import hiccup.hiccupstore.board.dto.BoardWriteForm;
import hiccup.hiccupstore.board.dto.Comment;
import hiccup.hiccupstore.board.dto.Image;
import hiccup.hiccupstore.board.service.BoardService;
import hiccup.hiccupstore.commonutil.file.FileStore;
import hiccup.hiccupstore.commonutil.file.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ApiBoardController {
    private final FileStore fileStore;
    private final BoardService boardService;





    @ResponseBody
    @GetMapping("/board/{filename}")
    public Resource viewImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:"+fileStore.getFullPath(filename));
    }
}
