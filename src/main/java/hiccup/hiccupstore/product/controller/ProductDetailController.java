package hiccup.hiccupstore.product.controller;

import hiccup.hiccupstore.product.dto.Product;
import hiccup.hiccupstore.product.dto.ProductImage;
import hiccup.hiccupstore.product.dto.page.Page;
import hiccup.hiccupstore.product.dto.page.ViewCriteria;
import hiccup.hiccupstore.product.service.ProductService;
import hiccup.hiccupstore.product.util.ImageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
@Slf4j
public class ProductDetailController {
    //TODO : 상품 리뷰 & QnA 정보 반환하는 BoardSevice 연결
    //TODO : 상품 리뷰 & QnA 리스트 -> Pagination 생성 필요 ( [리뷰&문의] 절댓값 : 1페이지 당 10개 / 최신순 정렬 ++ [리뷰] 선택값 : 전체 & 포토 )
    //TODO : Security 활용 AJAX 적용

    private final ProductService productService ;
    @GetMapping("/create") // 상품등록 폼 페이지 이동
    public void createForm (){
    }

    @RequestMapping("/delete")
    public String deleteProduct(Page page,
                                @RequestParam(name = "pid") String productId){
        productService.delProduct(Integer.parseInt(productId));
        return page.getListLink();
    }

    //TODO : User & Security 적용 이후, 계정 정보 랜더링 시킨 다음에 detail.html ThymeLeaf 수정하기
    @GetMapping("/detail")
    public String detailView(Model model,
                             @RequestParam(name = "pid") String productId,
                             @CookieValue(name = "productList", required = false) String productList,
                             HttpServletRequest request,
                             HttpServletResponse response){

        cookieProductList(productId, productList, request, response);


        Product product = productService.getProductById(Integer.parseInt(productId));
        ArrayList<ProductImage> productImages = productService.getProductImageListById(Integer.parseInt(productId));
        model.addAttribute("product", product);
        model.addAttribute("productImage",productImages.stream().
                filter(Image -> Image.getImageType().equals(ImageType.PRODUCT.getValue())).
                findFirst().orElse(new ProductImage()).getImagePath());
        model.addAttribute("detailImage",productImages.stream().
                filter(Image -> Image.getImageType().equals(ImageType.DETAIL.getValue())).
                findFirst().orElse(new ProductImage()).getImagePath());
        return "product/detail";
    }


    private void cookieProductList(String productId, String productList, HttpServletRequest request, HttpServletResponse response) {
        if(productList == null){
            Cookie productCookie = new Cookie("productList", productId);
            productCookie.setPath("/");
            response.addCookie(productCookie);
        } else {
            Cookie[] cookies = request.getCookies();
            for(int i = 0;i<cookies.length;i++){
                if(cookies[i].getName().equals("productList")){
                    String cookieValue = cookies[i].getValue(); //1
                    String[] cookieProductList = cookieValue.split("/");

                    for (String listValue : cookieProductList) {
                        if(listValue.equals(productId)){
                            return;
                        }
                    }
                    log.info("length = {} ",cookieValue.split("/").length);
                    if(cookieValue.split("/").length < 6){
                        cookieValue = productId +"/"+cookieValue;
                    } else {
                        cookieValue = productId +"/"+cookieValue;
                        cookieValue = cookieValue.substring(0, cookieValue.lastIndexOf("/"));
                    }

                    log.info("cookievlaue = {} ",cookieValue);
                    cookies[i].setValue(cookieValue);
                    cookies[i].setPath("/");
                    response.addCookie(cookies[i]);
                    return;
                }
            }
        }
    }
}

