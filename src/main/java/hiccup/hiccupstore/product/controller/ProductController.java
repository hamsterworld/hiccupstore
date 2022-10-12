package hiccup.hiccupstore.product.controller;


import hiccup.hiccupstore.commonutil.file.FileStore;
import hiccup.hiccupstore.product.dto.ProductCategory;
import hiccup.hiccupstore.product.dto.ProductForView;
import hiccup.hiccupstore.product.dto.page.Page;
import hiccup.hiccupstore.product.dto.page.PageCriteria;
import hiccup.hiccupstore.product.dto.page.ViewCriteria;
import hiccup.hiccupstore.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Integer.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product/productlist")
public class ProductController {
    private final ProductService productService ;
    private final FileStore fileStore;

    // 나중에 REST API 적용하면 "PutMapping" / "DeleteMapping" / "PathMapping" 등을 활용해봅시다.


    // RequestParam 에서 Request에서는 다른 key값으로 값을 받았다면 어노테이션 속성 중 value로 Client에서의 키 변수값을 입력하면된다.

    /*
    * 카테고리 기본조회
     */

    /*
     * Parameter : 조회 기준 카테고리 & 조회 정렬 기준
     * Return : Parameter 카테고리 & 정렬 기준에 맞는 상품 리스트가 담긴 Model
     * [설명]
     * dropdown 메뉴가 아닌 기본 Header 의
     */
    @GetMapping("/category")
    public void categoryAjax(@RequestParam(name="type") String categoryId,
                            HttpServletResponse response){
        try{
            String total = productService.getTotalByCategory(categoryId);
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print(total);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @GetMapping("/liquor")
    public String defaultCategoryView(ViewCriteria criteria,
                                      Model model,
                                      @RequestParam(name="pageNum", defaultValue = "1",required = false) String pageNum,
                                      @RequestParam(name="type", defaultValue = "-1", required=false) String type,
                                      @RequestParam(name="sort", defaultValue = "default", required=false) String sortValue,
                                      @RequestParam(name = "viewCnt", defaultValue = "16", required = false) String viewCount) {
//        Enum을 쓸 필요가 없어짐.
//        if (type.length() > 2) {
//            criteria.setType(ProductCategory.valueOf(type.toUpperCase()).ordinal());
//        }else
        if(type.equals("-1")){
            criteria.setType(-1);
        }else {
            criteria.setType(parseInt(type));
        }
        criteria.setSort(sortValue);
        criteria.calcStartEnd(parseInt(pageNum), parseInt(viewCount));


        ArrayList<ProductForView> list = productService.getProductListByCategory(criteria);
        model.addAttribute("list",list) ;
        model.addAttribute("page", new Page(productService.getListSize(criteria), parseInt(pageNum), parseInt(viewCount), criteria));
        return "product/productlist/liquor";
    }

    /*
    * 가격대별 기본 조회
     */

    @GetMapping("/priceRange")
    public void priceRangeAjax(@RequestParam(name="p") String p,
                         HttpServletResponse response){
        try{
            String total = productService.getTotalByPriceRange(p);
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print(total);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @GetMapping("/price")
    public String defaultPriceView(ViewCriteria criteria,
                                   Model model,
                                   @RequestParam(name="pageNum", defaultValue = "1",required = false) String pageNum,
                                   @RequestParam(name="p", defaultValue = "all", required=false) String priceRange,
                                   @RequestParam(name="sort", defaultValue = "default", required=false) String sortValue,
                                   @RequestParam(name="viewCnt", defaultValue = "16", required=false) String viewCount){

        if(priceRange.equals("all")){
            criteria.setP(-1);
        }else {
            criteria.setP(parseInt(priceRange));
        }

        criteria.setSort(sortValue);
        criteria.calcStartEnd(parseInt(pageNum), parseInt(viewCount));


        ArrayList<ProductForView> list = productService.getProductListByPriceRange(criteria);
        model.addAttribute("list",list) ;
        System.out.println(list.size());
//        System.out.println("조회된 총 상품 갯수 : " + productService.getListSize(criteria));
        System.out.println("조회된 총 상품 갯수 : " +  productService.getListSize(criteria));

        model.addAttribute("page", new Page(productService.getListSize(criteria), parseInt(pageNum), parseInt(viewCount), criteria));
        return "product/productlist/price";
    }





    @GetMapping("/search")
    public String searchView(Model model,
                             @RequestParam String keyword,
                             @RequestParam(defaultValue = "rate", required=false) String sortValue,
                             @RequestParam(defaultValue = "16", required=false) String viewCount){
        return "redirect:/product/productlist/search";
    }


}
