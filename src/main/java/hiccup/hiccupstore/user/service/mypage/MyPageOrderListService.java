package hiccup.hiccupstore.user.service.mypage;

import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.order.OrderDto;
import hiccup.hiccupstore.user.dto.order.OrderLatelyProductDto;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.util.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageOrderListService {

    private final UserMapper userMapper;

    /** 날짜에맞는 최근주문상품들을 가져옵니다.*/
    public List<OrderLatelyProductDto> getOrderLatelyProductListByDate(String startDate,
                                              String lastDate,
                                              UserDto user,
                                              Integer page,Integer pageSize){

        return userMapper.getOrderLatelyProductListPageByDate(startDate,lastDate,user.getUserId(),page-1,pageSize);

    }

    public Map<String,Object> getOrderDtoList(UserDto user, Integer page, Integer pagesize){

        Map<String, Object> OrderListCountAndOrderListMap = new HashMap<>();

        OrderListCountAndOrderListMap.put("OrderListCount",userMapper.getOrderListCount(user.getUserId()));
        OrderListCountAndOrderListMap.put("OrderList",userMapper.getOrderListPage(user.getUserId(), page-1, pagesize));

        return OrderListCountAndOrderListMap;

    }

    public void purchaseConfirm(Integer orderid){

        userMapper.updateOrderStatus(orderid,"구매확정");

    }

}
