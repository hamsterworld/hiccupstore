package hiccup.hiccupstore.user.controller.mypage;

import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.dto.order.OrderDto;
import hiccup.hiccupstore.user.dto.order.OrderFormDto;
import hiccup.hiccupstore.user.dto.order.OrderLatelyProductDto;
import hiccup.hiccupstore.user.service.mypage.MypageRefundService;
import hiccup.hiccupstore.user.util.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MyPageRefundController {

    private final MypageRefundService mypageRefundService;
    private final FindSecurityContext findSecurityContext;
    private Integer pageSize = 5;

    @GetMapping("/mypage/mypagerefund")
    public String myPageRefund(){
        return "mypage/mypagerefund";
    }

    @GetMapping("/mypage/mypagerefundresult")
    public String myPageRefundResult(){
        return "mypage/mypagerefundresult";
    }

    /** 최근주문상품페이지에서 날짜에맞게 주문상품을 조회하는 매서드입니다.*/
    @GetMapping("/mypage/mypagerefundorderlistsearch")
    public String myPageOrderListPost(String startdate,
                                      String lastdate,
                                      String mode,
                                      Model model,
                                      @RequestParam(defaultValue = "1") Integer page){

        if(mode.equals("cancel")){
            searchOrderRefundList(startdate,lastdate,model,page);
            return "mypage/mypagerefund";
        } else {
            searchOrderRefundResultList(startdate,lastdate,model,page);
            return "mypage/mypagerefundresult";
        }


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

    void searchOrderRefundList(String startdate,
                         String lastdate,
                         Model model,
                         Integer page){

        Map<String, Object> OrderListCountAndOrderListMap = null;
        ArrayList<OrderFormDto> orderFormList = null;
        Paging paging = null;

        UserDto user = findSecurityContext.getUserDto();

        List<OrderLatelyProductDto> orderLatelyProductList =
                mypageRefundService.getOrderLatelyRefundProductListByDate(startdate,lastdate,user,page,pageSize);

        if (orderLatelyProductList.size() != 0) {
            OrderListCountAndOrderListMap = mypageRefundService.getRefundOrderDtoList(user, page, pageSize);
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
    }

    void searchOrderRefundResultList(String startdate,
                               String lastdate,
                               Model model,
                               Integer page){

        Map<String, Object> OrderListCountAndOrderListMap = null;
        ArrayList<OrderFormDto> orderFormList = null;
        Paging paging = null;
        String[] searchCondtion = null;

        UserDto user = findSecurityContext.getUserDto();

        List<OrderLatelyProductDto> orderLatelyProductList =
                mypageRefundService.getOrderLatelyRefundResultProductListByDate(startdate,lastdate,user,page,pageSize);

        if (orderLatelyProductList.size() != 0) {
            OrderListCountAndOrderListMap = mypageRefundService.getRefundResultOrderDtoList(user, page, pageSize);
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
    }

}
