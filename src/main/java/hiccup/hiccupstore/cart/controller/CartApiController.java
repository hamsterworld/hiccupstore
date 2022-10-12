package hiccup.hiccupstore.cart.controller;

import hiccup.hiccupstore.cart.dto.CartForm;
import hiccup.hiccupstore.cart.service.CartService;
import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.commonutil.security.service.Oauth2UserContext;
import hiccup.hiccupstore.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RequiredArgsConstructor
@RestController
public class CartApiController {
    private final CartService cartService;
    private final FindSecurityContext findSecurityContext;
    @PostMapping("/api/cart/delete")
    public Boolean deleteCart(@RequestBody HashMap<String, Integer> productMap){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = findSecurityContext.getUserDto();
        Integer productId = productMap.get("productId");
        if(null !=user.getUserId() && null != productId ){
            cartService.deleteCartByProductId(productId, user.getUserId());
            return true;
        }
        return false;
    }
    @PostMapping("/api/cart/deleteAll")
    public boolean deleteAllCart(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = findSecurityContext.getUserDto();
        if(null !=user.getUserId()) {
            cartService.deleteAllCart(user.getUserId());
            return true;
        }
        return false;
    }
    @PostMapping("/api/cart/modify")
    public Boolean modifyCart(@RequestBody CartForm cartForm){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDto user = findSecurityContext.getUserDto();
        Integer productId = cartForm.getProductId();
        Integer quantity = cartForm.getQuantity();
        if(null !=user.getUserId() && null != productId ){
            cartService.modifyQuantity(productId,quantity);
            return true;
        }
        return false;
    }
    @PostMapping("/api/cart/insert")
    public Boolean insert( @RequestBody CartForm cartForm)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = findSecurityContext.getUserDto();
        // TODO 나중에 등록하는 것도 추가해야한다.
        cartForm.setUserId(user.getUserId());
        if(null !=user.getUserId() && null != cartForm ){
            boolean success =cartService.insert(cartForm);
            return success;
        }
        return false;
    }
}
