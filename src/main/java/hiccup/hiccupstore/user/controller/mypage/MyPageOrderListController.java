package hiccup.hiccupstore.user.controller.mypage;

import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.user.dto.*;
import hiccup.hiccupstore.commonutil.security.service.Oauth2UserContext;
import hiccup.hiccupstore.user.dto.order.OrderDto;
import hiccup.hiccupstore.user.dto.order.OrderFormDto;
import hiccup.hiccupstore.user.dto.order.OrderLatelyProductDto;
import hiccup.hiccupstore.user.dto.order.OrderStatusChangedDto;
import hiccup.hiccupstore.user.service.mypage.MyPageOrderListService;
import hiccup.hiccupstore.user.util.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MyPageOrderListController {

    private final MyPageOrderListService myPageOrderListService;
    private final FindSecurityContext findSecurityContext;
    private final Integer pageSize = 5;

    /** 최근주문상품페이지로 이동하는 매서드입니다. */
    @GetMapping("/mypage/mypageorderlist")
    public String myPageOrderList(){

        return "mypage/mypageorderlist";

    }

    /** 최근주문상품페이지에서 날짜에맞게 주문상품을 조회하는 매서드입니다.*/
    @GetMapping("/mypage/mypageorderlistsearch")
    public String myPageOrderListPost(String startdate,
                                      String lastdate,
                                      Model model,
                                      @RequestParam(defaultValue = "1") Integer page){

        Map<String, Object> OrderListCountAndOrderListMap = null;
        ArrayList<OrderFormDto> orderFormList = null;
        Paging paging = null;

        UserDto user = findSecurityContext.getUserDto();

        /**
         *  최근주문에대한 DB를 가져옵니다. 예를들어서
         *  A라는 사람이 3개의 주문을 했다 그리고 그 주문은 각각 3(1,2,3)개 2(1,4)개 3(2,3,7)개종류의 아이템을 샀다고 가정해보자.
         *  그러면 밑에 Service에서 return되는 List는 이렇게 값을 받아온다.
         *  A - 1번주문 - 1번아이템
         *  A - 1번주문 - 2번아이템
         *  A - 1번주문 - 3번아이템
         *
         *  A - 2번주문 - 1번아이템
         *  A - 2번주문 - 4번아이템
         *
         *  A - 3번주문 - 2번아이템
         *  A - 3번주문 - 3번아이템
         *  A - 3번주문 - 7번아이템
         *
         *  이렇게 총 리스트에 7개의 SIZE를 가진 LIST가 반환된다.
         *
         * */
        List<OrderLatelyProductDto> orderLatelyProductList =
                myPageOrderListService.getOrderLatelyProductListByDate(startdate,lastdate,user,page,pageSize);

        /** getOderDtoList에서는
         *  A - 1번주문
         *  A - 2번주문
         *  A - 3번주문
         *
         * 가 의 SIZE 3을지닌 LIST가 반환된다.
         *
         * */
        if (orderLatelyProductList.size() != 0) {
            OrderListCountAndOrderListMap = myPageOrderListService.getOrderDtoList(user, page, pageSize);
            /** MakeOrderList에서 내가 만들고자 싶은것은
             *  orderForm이라는 객체안에 1번주문에 대한 product가 몇개주문했는지 그리고 그product에대한 List를 담기를 원한다.
             *
             *  즉, orderForm 안에 필드를 본다면
             *  private Integer orderid = 1  1번주문에대해서
             *  private List<OrderLatelyProductDto>가 담겨져있다.
             *  --> 좀더 쉽게풀자면 1번주문에대해서 1번아이템의 productid와 imagename,몇개주문햇는지? 등등이 들어있다.
             * */
            orderFormList = makeOrderList(orderLatelyProductList,
                    (List<OrderDto>) OrderListCountAndOrderListMap.get("OrderList"));
            paging = new Paging(
                    (Integer) OrderListCountAndOrderListMap.get("OrderListCount"),
                    page,
                    pageSize);

            model.addAttribute("orderFormList",orderFormList);
        }

        model.addAttribute("page",page);
        model.addAttribute("paging",paging);
        model.addAttribute("startdate",startdate);
        model.addAttribute("lastdate",lastdate);

        return "mypage/mypageorderlist";
    }

    /** 구매확정을 결정짓는 매서드입니다. ajax입니다. */
    @PostMapping("/mypage/purchaseconfirm")
    @ResponseBody
    public void purchaseconfirm(@RequestBody OrderStatusChangedDto orderStatusChangedDto){
        myPageOrderListService.purchaseConfirm(orderStatusChangedDto.getOrderid());

    }


    /** productLatelyDto랑 orderDto를 합쳐서 ProductDtoList를 만든다.
     * 해당 매서드에대한 주석은 MyPageCotroller쪽을 참고해주세요.
     * */
    ArrayList<OrderFormDto> makeOrderList(List<OrderLatelyProductDto> orderLatelyProductList,List<OrderDto> orderList){

        ArrayList<OrderFormDto> OrderFormDtoList = new ArrayList<>();
        HashMap<Integer, OrderFormDto> objectObjectHashMap = new HashMap<>();
        HashMap<Integer, List<OrderLatelyProductDto>> objectObjectHashMap1 = new HashMap<>();

        for(int i = 0; i < orderList.size();i++){
            OrderFormDto orderFormDto = OrderFormDto.builder().orderId(orderList.get(i).getOrderid()).
                    orderdate(orderList.get(i).getOrderdate()).
                    status(orderList.get(i).getStatus()).
                    build();

            objectObjectHashMap.put(orderList.get(i).getOrderid(),orderFormDto);
            List<OrderLatelyProductDto> OrderLatelyProductDto = new ArrayList<>();
            objectObjectHashMap1.put(orderList.get(i).getOrderid(),OrderLatelyProductDto);
            OrderFormDtoList.add(orderFormDto);

        }

        for (OrderLatelyProductDto orderLatelyProductDto : orderLatelyProductList) {

            OrderFormDto orderFormDto = objectObjectHashMap.get(orderLatelyProductDto.getOrderid());
            String result = orderLatelyProductDto.getImagepath();
            orderLatelyProductDto.setImagepath(result);

            List<OrderLatelyProductDto> orderLatelyProductDtoList =
                    objectObjectHashMap1.get(orderLatelyProductDto.getOrderid());
            orderLatelyProductDtoList.add(orderLatelyProductDto);

            if(orderFormDto.getOrderLatelyProductDto() == null){
                orderFormDto.setOrderLatelyProductDto(orderLatelyProductDtoList);
            }
        }

        return OrderFormDtoList;
    }

}
