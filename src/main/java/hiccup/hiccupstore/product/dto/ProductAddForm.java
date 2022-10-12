package hiccup.hiccupstore.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAddForm {

    private int categoryId ; // 수정될 수 없음 -> Enum 조회할 때 index로 사용하기
    private String productName ;
    private Integer price;
    private Integer quantity ;
    private Long alcoholContent;
    private String brand ;
    private String description ;

    public Product toProduct(){
        return Product.builder().categoryId(this.categoryId).
                productName(this.productName)
                .price(this.price)
                .quantity(this.quantity)
                .alcoholContent(this.alcoholContent)
                .brand(this.brand)
                .description(this.description).build();
    }
}
