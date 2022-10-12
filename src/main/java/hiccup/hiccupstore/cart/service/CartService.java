package hiccup.hiccupstore.cart.service;

import hiccup.hiccupstore.cart.dao.CartMapper;
import hiccup.hiccupstore.cart.dto.Cart;
import hiccup.hiccupstore.cart.dto.CartForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartMapper cartMapper;

    public ArrayList<Cart> findAll(){
        return cartMapper.findAll();
    }
    public boolean insert(CartForm cartForm){
        ArrayList<Cart> cartList = cartMapper.GetCartListByUserId(cartForm.getUserId());
        for (Cart cart: cartList) {
            if (cart.getProductId() == cartForm.getProductId()){

                return false;
            }
        }
        cartMapper.insertCart(cartForm);
        return true;
    }
    public ArrayList<Cart> GetCartListByUserId(Integer userId){
        return cartMapper.GetCartListByUserId(userId);
    }
    public int sumPrice(ArrayList<Cart> carts){
        int price =0 ;
        for (Cart cart: carts) {
            price += cart.getPrice()* cart.getQuantity();
        }
        return price;
    }
    public void deleteCartByProductId(Integer productId,Integer userId){
        cartMapper.deleteCartByProductId(productId,userId);
    }
    public void deleteAllCart(Integer userId){
        cartMapper.deleteAllCart(userId);
    }

    public void modifyQuantity(Integer productId, Integer quantity){
        cartMapper.modifyQuantity(productId,quantity);
    }
}
