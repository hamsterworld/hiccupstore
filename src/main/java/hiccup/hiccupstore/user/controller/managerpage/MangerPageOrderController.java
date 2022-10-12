package hiccup.hiccupstore.user.controller.managerpage;

import hiccup.hiccupstore.user.dto.order.OrderDto;
import hiccup.hiccupstore.user.dto.order.OrderFormDto;
import hiccup.hiccupstore.user.dto.order.OrderLatelyProductDto;
import hiccup.hiccupstore.user.dto.order.OrderStatusChangedDto;
import hiccup.hiccupstore.user.service.managerpage.ManagerPageOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MangerPageOrderController {

    private final ManagerPageOrderService managerPageOrderService;
    private final Integer pageSize = 5;

    /** 관리자페이지 주문관리페이지로 이동하는 매서드입니다. */
    @GetMapping("/managerpage/managerpageorder")
    public String managerPageOrder(String startdate,
                                   String lastdate,
                                   @RequestParam(required = false) Integer searchUserId,
                                   @RequestParam(required = false) String searchField,
                                   Model model,
                                   @RequestParam(defaultValue = "1") Integer page){


        if(searchUserId !=null){

            getOrderFormListBysearchUserId(searchUserId,model,page);

            return "managerpage/managerpageorder";
        }

        if((startdate == null && lastdate == null) || ("".equals(startdate) && "".equals(lastdate))){

            getOrderFormList(model, page);
            return "managerpage/managerpageorder";

        } else {

            getOrderFormListByDate(startdate,lastdate,model, page);
            model.addAttribute("startdate",startdate);
            model.addAttribute("lastdate",lastdate);
            return "managerpage/managerpageorder";
        }


    }

    /** 관리자페이지 주문상태를 변경하는 매서드입니다.*/
    @PostMapping("/managerpage/changedorderstatus")
    @ResponseBody
    public String changedOrderStatus(@RequestBody OrderStatusChangedDto orderStatusChangedDto){

        managerPageOrderService.updateOrderStatus(orderStatusChangedDto);
        return "ok";

    }


    /** 관리자페이지 주문상태를 변경하는 매서드입니다.*/
    @PostMapping("/managerpage/refundchangedorderstatus")
    @ResponseBody
    public String refundChangedOrderStatus(@RequestBody OrderStatusChangedDto orderStatusChangedDto){

        managerPageOrderService.updateOrderStatus(orderStatusChangedDto);
        return "ok";

    }

    ArrayList<OrderFormDto> makeOrderList(List<OrderLatelyProductDto> orderLatelyProductList,List<OrderDto> orderList){

        ArrayList<OrderFormDto> OrderFormDtoList = new ArrayList<>();
        HashMap<Integer, OrderFormDto> OrderIdAndOrderFormDtoMap = new HashMap<>();
        HashMap<Integer, List<OrderLatelyProductDto>> OrderIdAndOrderLatelyProductDtoListMap = new HashMap<>();

        for(int i = 0; i < orderList.size();i++){

            OrderFormDto orderFormDto = OrderFormDto.builder().orderId(orderList.get(i).getOrderid()).
                    userid(orderList.get(i).getUserid()).
                    orderdate(orderList.get(i).getOrderdate()).
                    status(orderList.get(i).getStatus()).
                    build();

            OrderIdAndOrderFormDtoMap.put(orderList.get(i).getOrderid(),orderFormDto);
            List<OrderLatelyProductDto> OrderLatelyProductDto = new ArrayList<>();
            OrderIdAndOrderLatelyProductDtoListMap.put(orderList.get(i).getOrderid(),OrderLatelyProductDto);
            OrderFormDtoList.add(orderFormDto);

        }

        for (OrderLatelyProductDto orderLatelyProductDto : orderLatelyProductList) {

            OrderFormDto orderFormDto = OrderIdAndOrderFormDtoMap.get(orderLatelyProductDto.getOrderid());

            String result = orderLatelyProductDto.getImagepath();
            orderLatelyProductDto.setImagepath(result);

            List<OrderLatelyProductDto> orderLatelyProductDtoList =
                    OrderIdAndOrderLatelyProductDtoListMap.get(orderLatelyProductDto.getOrderid());
            orderLatelyProductDtoList.add(orderLatelyProductDto);

            if(orderFormDto.getOrderLatelyProductDto() == null){
                orderFormDto.setOrderLatelyProductDto(orderLatelyProductDtoList);
            }
        }

        return OrderFormDtoList;
    }


    private void getOrderFormListBysearchUserId(Integer searchUserId,Model model, Integer page) {

        List<OrderLatelyProductDto> orderLatelyProductList =
                managerPageOrderService.FirstManagerPageOrderListBysearchUserId(searchUserId,page,pageSize);

        ArrayList<OrderFormDto> orderFormList = null;

        if (orderLatelyProductList.size() != 0) {
            List<OrderDto> orderDtos =
                    managerPageOrderService.FirstManagerPageOrderList2BysearchUserId(searchUserId,page,pageSize, model);
            orderFormList = makeOrderList(orderLatelyProductList,orderDtos);
            model.addAttribute("orderFormList",orderFormList);
            model.addAttribute("page", page);
            model.addAttribute("searchUserId",searchUserId);
        }

    }

    private void getOrderFormList(Model model, Integer page) {
        List<OrderLatelyProductDto> orderLatelyProductList = managerPageOrderService.FirstManagerPageOrderList(page,pageSize);

        ArrayList<OrderFormDto> orderFormList = null;

        if (orderLatelyProductList.size() != 0) {
            List<OrderDto> orderDtos = managerPageOrderService.FirstManagerPageOrderList2(page,pageSize, model);
            orderFormList = makeOrderList(orderLatelyProductList,orderDtos);
            model.addAttribute("orderFormList",orderFormList);
            model.addAttribute("page", page);
        }
    }

    private void getOrderFormListByDate(String startDate,String lastDate,Model model,Integer page){
        List<OrderLatelyProductDto> orderLatelyProductList = managerPageOrderService.getOrderLatelyProductListByDate(startDate,lastDate,page,pageSize);

        ArrayList<OrderFormDto> orderFormList = null;

        if (orderLatelyProductList.size() != 0) {
            List<OrderDto> orderDtos = managerPageOrderService.getOrderListByDate(startDate,lastDate,page,pageSize, model);
            orderFormList = makeOrderList(orderLatelyProductList,orderDtos);
            model.addAttribute("orderFormList",orderFormList);
            model.addAttribute("page", page);
        }



    }

}
