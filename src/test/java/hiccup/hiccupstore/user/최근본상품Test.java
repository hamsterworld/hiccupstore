package hiccup.hiccupstore.user;


import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class 최근본상품Test {

    @Autowired
    UserMapper userMapper;
    //상품상세보기에 들어갔을때
    //Integer 상품Id = 4; //가 이렇게 들어온다.
    //여기서 goods에 붙일지 아니면 --> 이게더 편할듯

    /**
     * 일단 이방법으로 사용해보자.
     * String goods = 상품Id+"/"+goods;
     */
    //이미 배열화된것에 붙일지.
    //일단 맨처음본게 3/2/1 이렇게 들어가고
    //6이 넘어가면 6/5/4/3/2/1 --> 7/6/5/4/3/2 가 되야한다.


    //만약에 꽉찻을때
    //Integer 상품Id2 = 7;//가 이렇게 들어왔을때.
    //이미 배열이 length가 6이여서 더이상 못들어갈때 맨마지막에 잇던걸 없애야된다.
    //
    @Test
    @DisplayName("최근본상품 list 6개제한 (다 꽉안찻을때 들어온다면)")
    void 최근본상품() {

        Integer mobNumLimitLength = 6;

        String goods = "3/2/1";
        String[] mobNum = goods.split("/");
        String ret1 = mobNum[0];
        String ret2 = mobNum[1];
        String ret3 = mobNum[2];

        Integer 상품Id = 4;
        goods = 상품Id + "/" + goods;

        System.out.println("최근본상품 String = " + goods);

    }

    @Test
    @DisplayName("최근본상품 list 6개제한 (꽉찻을때)")
    void 최근본상품_꽉찼을때() {

        String goods = "6/5/4/3/2/1";   //  7/6/5/4/3/2/1
        Integer 상품Id = 7;

        // '/' 개수세기
        int count = 0;
        for (int i = 0; i < goods.length(); i++) {
            if (goods.charAt(i) == '/') {
                count++;
            }
        }

        if(count == 5 ){
            goods = 상품Id + "/" + goods;
            System.out.println("현재는 7이 들어가고 1이 안사라짐 = " + goods);
            goods = goods.substring(0, goods.lastIndexOf("/"));
            System.out.println("나머지는 제거되고 최종추출물 = " + goods);
        }
    }


    @Test
    @DisplayName("최근본상품 list 6개제한")
    void 최근본상품_꽉찼을때_productId() {

        String goods = "6434/55534/43456/3323/2334/5561";   //  7/6/5/4/3/2/1
        Integer 상품Id = 77777;

        // '/' 개수세기
        int count = 0;
        for (int i = 0; i < goods.length(); i++) {
            if (goods.charAt(i) == '/') {
                count++;
            }
        }

        if(count == 5 ){
            goods = 상품Id + "/" + goods;
            System.out.println("현재는 77777이 들어가고 5561이 안사라짐 = " + goods);
            goods = goods.substring(0, goods.lastIndexOf("/"));
            System.out.println("나머지는 제거되고 최종추출물 = " + goods);
        }
    }


    @Test
    @DisplayName("최근본상품 db에서 잘 가져오는지.")
    void 최근본상품_꽉찼을때_productId_DB에서가져오기() {

        String goods = "6/5/4/3/2/1";
        String[] mobNum = goods.split("/");
        List<ProductDto> productList = userMapper.getProductList(mobNum);
        System.out.println("productList = " + productList);

    }

}
