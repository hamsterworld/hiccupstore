package hiccup.hiccupstore.product.dao;

import hiccup.hiccupstore.product.dto.Product;
import hiccup.hiccupstore.product.dto.ProductForView;
import hiccup.hiccupstore.product.dto.ProductImage;
import hiccup.hiccupstore.product.dto.page.PageCriteria;
import hiccup.hiccupstore.product.dto.page.ViewCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface ProductMapper {

    // INSERT
    void insertProduct(Product product) ;
    void insertProductImage(ProductImage productImage) ;

    //DELETE
    void deleteProduct(int productId) ;
    void deleteProductImage(int productImageId) ;

    //UPDATE
    void updateProduct(Product product);
    void updateProductImage(ProductImage productImage);


    /* SELECT */
    // 상품 사진 조화
    ArrayList<ProductImage> getProductImageListById(int productId);
    // 상세 상품 조회
    Product selectById(int productId) ;
    int selectProductIdByName(String productName) ;

    // getTotal~ : AJAX 전용 - get~ : HTML 내 전체상품 출력 전용 ( !!리팩토링 필요!! )

    int getTotalByCategory(int categoryId);
    int getTotalByPriceRangeOverFour(int p);
    int getTotalByPriceRangeUnderFour(int p);


    int getAllListSize();
    int getCategoryListSize(ViewCriteria criteria);
    int getPriceRangeListSize(ViewCriteria criteria);
    int getPriceRangeListSizeOverFour(ViewCriteria criteria);
    int getPriceRangeListSizeUnderFour(ViewCriteria criteria);
    // 상품 조회 (카테고리)
    ArrayList<ProductForView> selectByCategory(ViewCriteria criteria) ;
    // 상품 조회 (가격 범위)
    ArrayList<ProductForView> selectByPriceRange(ViewCriteria criteria) ;

    ArrayList<ProductForView> selectBySearch(HashMap<String, Object> map) ;




}

