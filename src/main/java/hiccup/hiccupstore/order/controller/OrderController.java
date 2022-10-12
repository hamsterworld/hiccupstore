package hiccup.hiccupstore.order.controller;

import hiccup.hiccupstore.cart.dto.Cart;
import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.order.dto.Order;
import hiccup.hiccupstore.order.dto.OrderInfo;
import hiccup.hiccupstore.order.dto.OrderProduct;
import hiccup.hiccupstore.order.dto.OrderProductInfo;
import hiccup.hiccupstore.order.service.OrderService;
import hiccup.hiccupstore.product.dto.Product;
import hiccup.hiccupstore.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final FindSecurityContext findSecurityContext;

    @GetMapping("/test")
    public String Test1(){
       return "index";
    }

    /*
        method : orderList
        param : HttpServletRequest : 로그인 확인
        Return : String
        [설명]
        주문 페이지로 들어왔을 때 필요한 db와 함께 페이지 넘겨주는 기능
    */
    @GetMapping(value = "/list") //, method = RequestMethod.Post
    public String orderList(Model model) {

        UserDto user = findSecurityContext.getUserDto();
        Integer userId = user.getUserId();

        model.addAttribute("user",user);

        //주문목록 정보
        List<Cart> carts = orderService.getCarts(userId);
        OrderInfo orderInfo = new OrderInfo();
        int total = 0;

        List<Integer> productIds = new ArrayList<Integer>();

        for(int i=0;i<carts.size();i++){
            productIds.add(carts.get(i).getProductId());
        }

        List<OrderProductInfo> orderProductInfo1 = orderService.getOrderProductList(productIds);

        for(int i = 0;i < orderProductInfo1.size();i++){
            orderProductInfo1.get(i).setQuantity(carts.get(i).getQuantity());

            total = total + carts.get(i).getQuantity() * orderProductInfo1.get(i).getPrice();

            orderInfo.getOrderProductInfo().add(orderProductInfo1.get(i));
        }
        orderInfo.setTotal(total);
        model.addAttribute("orderInfo",orderInfo);

        return "order/Order";

    }

    /*
        method : orderProduct
        param : HttpServletRequest : 로그인 정보,  order, orderProducts : db에 자료 저장하고 받아오기
        Return : 결제페이지로 이동
        [설명]
        결제 버튼을 눌렀을 때 db에 데이터를 저장해 주고 결제 완료 페이지로 이동시키는 메소드
    */
    @ResponseBody
    @PostMapping("/orderProduct")
    public int orderProduct(Model model,@RequestParam(value ="address" )String address ,@RequestBody List<OrderProduct> orderProducts ) {

        UserDto user = findSecurityContext.getUserDto();
        Integer userId = user.getUserId();

        //orderProduct,order 에 데이터 저장
        Order order = new Order();
        order.setStatus("입금대기");
        order.setAddress(address);
        order.setUserId(userId);

        orderService.insertOrder(order);

        int orderId = order.getOrderId();

        for(int i=0;i<orderProducts.size();i++){
            orderProducts.get(i).setOrderId(orderId);
        }
        orderService.insertOrderProducts(orderProducts); // orderProduct 데이터 집어넣기
        System.out.println("orderProducts : "+orderProducts);
        orderService.updateProductQuantity(orderProducts); //products quantity 줄이기
        orderService.deleteCart(userId); // cart 지우기

        return orderId;
    }

    /*
        method : orderResult
        param : HttpServletRequest : 로그인 정보,  orderId: 주문한 번호
        Return : 주문결과페이지로 이동
        [설명]
        db에 주문 결과를 무사히 넣은 후 주문 결과 화면으로 이동해주는 메서드
    */
    @GetMapping("/orderResult")
    public String orderResult(Model model,@RequestParam(value="orderId") int orderId){
        //결제된 상품 정보
        List<OrderProduct> returnOrderProduct = orderService.getOrderProduct(orderId);
        int count = returnOrderProduct.size();

        Integer total = 0;
        //user_order db에 total이 있었으면 좋겠지만 없으니 임시방편으로 사용
        for(int i=0;i<count;i++){
            total += returnOrderProduct.get(i).getOrderPrice();
        }
        total +=3000;
        Product returnProduct = orderService.getProduct(returnOrderProduct.get(0).getProductId());

        String orderMessage = returnProduct.getProductName()+" 외 "+count+"개 상품의 주문이 완료되었습니다.";

        System.out.println("check data : "+orderId+" : "+orderMessage+" : "+total);

        model.addAttribute("orderId",orderId);
        model.addAttribute("orderMessage",orderMessage); //주문완료 메시지
        model.addAttribute("total",total); // 총합계금액
        //결제 페이지로 넘어가기
        return "order/OrderResult";
    }

    /*
        method : checkOrder
        param : HttpServletRequest : 로그인 정보, orderId : 주문 번호 
        Return : 주문 확인 페이지로 이동
        [설명]
        선택한 주문목록의 상세페이지로 이동한다
    */
    @GetMapping("/check")
    public String checkOrder(Model model, @RequestParam(value="orderId")int orderId){

    UserDto user = findSecurityContext.getUserDto();
    model.addAttribute("user",user);

    //주문목록 정보
    List<OrderProduct> orderProducts = orderService.getOrderProduct(orderId);
    Order order = orderService.getOrder(orderId); //상태
    model.addAttribute("order",order); //상태 보내주기

    OrderInfo orderInfo = new OrderInfo();
    int total = 0;
    //주문정보 보내줄 데이터에 넣기
    List<Integer> productIds = new ArrayList<Integer>();

    for(int i=0;i<orderProducts.size();i++){
        productIds.add(orderProducts.get(i).getProductId());
    }

    List<OrderProductInfo> orderProductInfo1 = orderService.getOrderProductList(productIds);

    for(int i = 0;i < orderProductInfo1.size();i++){

        orderProductInfo1.get(i).setQuantity(orderProducts.get(i).getQuantity());
        total = total + orderProducts.get(i).getQuantity() * orderProductInfo1.get(i).getPrice();

        orderInfo.getOrderProductInfo().add(orderProductInfo1.get(i));
    }
    orderInfo.setTotal(total);

    model.addAttribute("orderInfo",orderInfo);

    return "order/CheckOrder";

    }

    /*
        위와 동일한 기능
    */
    @GetMapping("/managerpagecheck2")
    public String checkOrder2(Model model, @RequestParam(value="orderId")int orderId){

        UserDto user = findSecurityContext.getUserDto();
        model.addAttribute("user",user);

        //주문목록 정보
        List<OrderProduct> orderProducts = orderService.getOrderProduct(orderId);
        Order order = orderService.getOrder(orderId); //상태
        model.addAttribute("order",order); //상태 보내주기

        OrderInfo orderInfo = new OrderInfo();
        int total = 0;
        //주문정보 보내줄 데이터에 넣기
        List<Integer> productIds = new ArrayList<Integer>();

        for(int i=0;i<orderProducts.size();i++){
            productIds.add(orderProducts.get(i).getProductId());
        }

        List<OrderProductInfo> orderProductInfo1 = orderService.getOrderProductList(productIds);

        for(int i = 0;i < orderProductInfo1.size();i++){

            orderProductInfo1.get(i).setQuantity(orderProducts.get(i).getQuantity());
            total = total + orderProducts.get(i).getQuantity() * orderProductInfo1.get(i).getPrice();

            orderInfo.getOrderProductInfo().add(orderProductInfo1.get(i));
        }
        orderInfo.setTotal(total);

        model.addAttribute("orderInfo",orderInfo);

        return "order/CheckOrder";

    }


}
