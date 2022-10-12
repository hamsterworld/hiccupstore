package hiccup.hiccupstore.product.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductImage {
    private int imageId;
    private int productId;
    private String imageName;
    private String imagePath;
    private String imageType;
}
