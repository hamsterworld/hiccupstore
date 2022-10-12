package hiccup.hiccupstore.product.dto;

import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.util.UriComponentsBuilder;

@Data
@Builder
public class Product {
    private int productId ; // 수정될 수 없음
    private int categoryId ; // 수정될 수 없음 -> Enum 조회할 때 index로 사용하기
    private String productName ;
    @NumberFormat(pattern = "###,###")
    private int price;
    private int quantity ;
    private float alcoholContent;
    private String brand ;
    private String description ;
    private Long sellCount ; // 판매량

    public String getDetailLink(){
        UriComponentsBuilder uriComponent = UriComponentsBuilder.fromPath("/product/detail")
                .queryParam("pid", this.productId);
        return uriComponent.toUriString();
    }
}
