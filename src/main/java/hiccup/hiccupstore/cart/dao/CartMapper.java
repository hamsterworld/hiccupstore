package hiccup.hiccupstore.cart.dao;

import hiccup.hiccupstore.cart.dto.Cart;
import hiccup.hiccupstore.cart.dto.CartForm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
@Repository
public interface CartMapper {

    ArrayList<Cart> findAll();
    void insertCart(CartForm cartForm);
    ArrayList<Cart> GetCartListByUserId(Integer userId);
    void deleteAllCart(Integer userId);
    void deleteCartByProductId(Integer productId,Integer userId);

    void modifyQuantity(Integer productId, Integer quantity);
}
