package hiccup.hiccupstore.user.service.mypage;


import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.board.User1vs1BoardDto;
import hiccup.hiccupstore.user.dto.order.OrderDto;
import hiccup.hiccupstore.user.dto.order.OrderLatelyProductDto;
import hiccup.hiccupstore.user.dto.ProductDto;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.util.StatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    private final UserMapper userMapper;

    /**  mypage의 최근주문리스트 5건을 가져오는 service 매서드입니다. */
    public List<OrderLatelyProductDto> GetOrderLatelyProductList(UserDto user){

        return userMapper.getOrderLatelyProductList(user.getUserId());

    }

    /** 최근주문리스트 5건에대한 상태를 가져오는 service 매서드입니다.*/
    public HashMap<String, List> GetOrderListAndStatusList(UserDto user){

        List<OrderDto> orderList = userMapper.getOrderList(user.getUserId());

        HashMap<String, List> OrderListHashMap = new HashMap<>();
        OrderListHashMap.put("orderList",orderList);

        ArrayList<Integer> statusList = new ArrayList<>();
        OrderListHashMap.put("statusList",statusList);

        /**
         *  각상태 예를들어서 결제대기 ,결제완료 ,배송준비중 ,배송중 ,배송완료 상태가있다면
         *  각상태에대해서 숫자0을 부여하는 과정입니다.
         * */
        for(int i = 0; i <9; i++){
            statusList.add(i);
            statusList.set(i,0);
        }

        /**
         *  orderDto안에 각주문에대한 상태data를 받아와서 '결제대기' 상태라면 statusList에대해서 +1을 추가합니다.
         *  만약에 status가 '배송완료'상태라면 else if 끝에있는 statusList.get(5)에서 +1이 추가됩니다.
         *  이런과정이 반복됩니다.
         */
        for (OrderDto orderDto : orderList) {
            if(StatusType.Deposit_waiting.equals(orderDto.getStatus())){
                statusList.set(0,statusList.get(0)+1);
                continue;
            } else if(StatusType.Complete_payment.equals(orderDto.getStatus())){
                statusList.set(1,statusList.get(1)+1);
                continue;
            } else if(StatusType.preparing_for_delivery.equals(orderDto.getStatus())){
                statusList.set(2,statusList.get(2)+1);
                continue;
            } else if(StatusType.shipping.equals(orderDto.getStatus())){
                statusList.set(3,statusList.get(3)+1);
                continue;
            } else if(StatusType.Delivery_completed.equals(orderDto.getStatus())){
                statusList.set(4,statusList.get(4)+1);
                continue;
            } else if(StatusType.Confirmation_of_purchase.equals(orderDto.getStatus())){
                statusList.set(5,statusList.get(5)+1);
                continue;
            }  else if(StatusType.OrderCancel_Completed.equals(orderDto.getStatus()) ||
                    StatusType.OrderCancel_Request.equals(orderDto.getStatus())){
                statusList.set(6,statusList.get(6)+1);
                continue;
            } else if(StatusType.Exchange_Completed.equals(orderDto.getStatus()) ||
                    StatusType.Exchange_Request.equals(orderDto.getStatus())){
                statusList.set(7,statusList.get(7)+1);
                continue;
            } else if(StatusType.Refund_Completed.equals(orderDto.getStatus()) ||
                    StatusType.Refund_Request.equals(orderDto.getStatus())){
                statusList.set(8,statusList.get(8)+1);
                continue;
            }
        }
        return OrderListHashMap;
    }

    /** 최근본상품에서의 상품에대한 productId List를 얻어와서
     *  mapper를 통해 ProductList를 받아옵니다.
     * */
    public List<ProductDto> LatelySeeProduct(String goods){
        String[] productId = goods.split("/");
        List<ProductDto> productList = userMapper.getProductList(productId);
        return productList;
    }

}
