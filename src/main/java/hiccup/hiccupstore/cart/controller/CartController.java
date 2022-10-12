package hiccup.hiccupstore.cart.controller;

import hiccup.hiccupstore.cart.dto.Cart;
import hiccup.hiccupstore.cart.service.CartService;
import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.commonutil.security.service.Oauth2UserContext;
import hiccup.hiccupstore.product.dto.ProductForView;
import hiccup.hiccupstore.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RequiredArgsConstructor
@Controller
public class CartController {
    private final CartService cartService;
    private final FindSecurityContext findSecurityContext;

    @GetMapping("/cart")
    public String getCartList(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = findSecurityContext.getUserDto();
        ArrayList<Cart>cartList = cartService.GetCartListByUserId(user.getUserId());
        int price = cartService.sumPrice(cartList);
        model.addAttribute("productList", cartList);
        model.addAttribute("price", price);
        return "cart";
    }

}
