package hiccup.hiccupstore.user.service;

import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.NoticeDto;
import hiccup.hiccupstore.user.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final UserMapper userMapper;

    public List<ProductDto> getProductDtoList(){
        List<ProductDto> productlist = userMapper.getProductDtoList();
        return productlist;
    }

    public List<ProductDto> getProductDtoList2(){
        List<ProductDto> productlist = userMapper.getProductDtoList2();
        return productlist;
    }

    public List<NoticeDto> getNoticeDtoList(){
        List<NoticeDto> noticeDtoList = userMapper.getNoticeDtoList();
        return noticeDtoList;
    }

}
