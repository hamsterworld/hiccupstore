package hiccup.hiccupstore.user.service.mypage;

import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.dto.order.OrderLatelyProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MypageRefundService {

    private final UserMapper userMapper;

    public List<OrderLatelyProductDto> getOrderLatelyRefundProductListByDate(String startDate,
                                                                             String lastDate,
                                                                             UserDto user,
                                                                             Integer page,
                                                                             Integer pageSize) {

        return userMapper.getOrderLatelyRefundProductListPageByDate(startDate,lastDate,user.getUserId(),page-1,pageSize);

    }

    public Map<String, Object> getRefundOrderDtoList(UserDto user, Integer page, Integer pageSize) {

        Map<String, Object> OrderListCountAndOrderListMap = new HashMap<>();

        OrderListCountAndOrderListMap.put("OrderListCount",userMapper.getRefundOrderListCount(user.getUserId()));
        OrderListCountAndOrderListMap.put("OrderList",userMapper.getRefundOrderListPage(user.getUserId(), page-1, pageSize));

        return OrderListCountAndOrderListMap;

    }

    public List<OrderLatelyProductDto> getOrderLatelyRefundResultProductListByDate(String startDate, String lastDate, UserDto user, Integer page, Integer pageSize) {
        return userMapper.getOrderLatelyRefundResultProductListPageByDate(startDate,lastDate,user.getUserId(),page-1,pageSize);
    }

    public Map<String, Object> getRefundResultOrderDtoList(UserDto user, Integer page, Integer pageSize) {
        Map<String, Object> OrderListCountAndOrderListMap = new HashMap<>();

        OrderListCountAndOrderListMap.put("OrderListCount",userMapper.getRefundResultOrderListCount(user.getUserId()));
        OrderListCountAndOrderListMap.put("OrderList",userMapper.getRefundResultOrderListPage(user.getUserId(), page-1, pageSize));

        return OrderListCountAndOrderListMap;

    }
}
