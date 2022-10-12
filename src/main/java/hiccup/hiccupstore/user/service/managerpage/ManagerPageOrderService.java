package hiccup.hiccupstore.user.service.managerpage;

import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.order.OrderDto;
import hiccup.hiccupstore.user.dto.order.OrderLatelyProductDto;
import hiccup.hiccupstore.user.dto.order.OrderStatusChangedDto;
import hiccup.hiccupstore.user.util.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerPageOrderService {

    private final UserMapper userMapper;

    public List<OrderLatelyProductDto> FirstManagerPageOrderList(Integer page, Integer pagesize){

        return userMapper.getOrderLatelyProductListtManagerPage(page-1,pagesize);

    }

    public List<OrderDto> FirstManagerPageOrderList2(Integer page, Integer pagesize,Model model){

        Integer orderListAllCount = userMapper.getOrderListAllCount();

        Paging paging = new Paging(orderListAllCount,page,pagesize);
        model.addAttribute("paging",paging);

        List<OrderDto> orderList = userMapper.getOrderListAllPage(page-1, pagesize);

        return orderList;

    }

    public Integer updateOrderStatus(OrderStatusChangedDto orderStatusChangedDto){

        Integer integer = userMapper.updateOrderStatus(orderStatusChangedDto.getOrderid(), orderStatusChangedDto.getOrderstatus());

        return integer;

    }


    public List<OrderLatelyProductDto> getOrderLatelyProductListByDate(String startdate, String lastdate, Integer page, Integer pagesize){

        return userMapper.getOrderLatelyProductListManagerPage(startdate,lastdate,page-1,pagesize);

    }

    public List<OrderDto> getOrderListByDate(String startdate, String lastdate,Integer page, Integer pagesize, Model model){

        Integer orderListCount = userMapper.getOrderManagerListCount();

        Paging paging = new Paging(orderListCount,page,pagesize);
        model.addAttribute("paging",paging);

        List<OrderDto> orderList = userMapper.getOrderListManagerPage(startdate,lastdate,page-1, pagesize);

        return orderList;

    }

    public List<OrderLatelyProductDto> FirstManagerPageOrderListBysearchUserId(Integer searchUserId, Integer page, Integer pageSize) {

        return userMapper.getOrderLatelyProductListManagerPageByUserId(searchUserId,page-1,pageSize);

    }

    public List<OrderDto> FirstManagerPageOrderList2BysearchUserId(Integer searchUserId, Integer page, Integer pageSize, Model model) {

        Integer orderListCount = userMapper.getOrderManagerListCountbyUserId(searchUserId);

        Paging paging = new Paging(orderListCount,page,pageSize);
        model.addAttribute("paging",paging);

        List<OrderDto> orderList = userMapper.getOrderListManagerPageByUserId(searchUserId,page-1, pageSize);

        return orderList;

    }
}
