package hiccup.hiccupstore.user;

import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.order.OrderDto;
import hiccup.hiccupstore.user.dto.order.OrderLatelyProductDto;
import hiccup.hiccupstore.user.dto.TestDto;
import hiccup.hiccupstore.user.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MyPageTest {

    @Autowired
    UserMapper userMapper;

    @BeforeEach
    void beforeEach(){

        /** 회원정보 임시 Data */
        UserDto user = UserDto.builder().userId(1).userName("ssoboro").nickName("오인석").address("햄스터동네").birth("93/06/18").
        email("ssoboro1@gmail.com").phone("01085264714").password("4863527wyc").build();

        userMapper.save(user);

        /** 주문정보 임시 Data */
        OrderDto order = new OrderDto(1, user.getUserId(),
                "입금대기", "2022/07/28", "16034 햄스터아파트");

        userMapper.saveOrder(order);

    }

    @Test
    @DisplayName("초기화 데e이터 확인")
    @Transactional
    void 초기화_데이터_확인(){

        //when
        UserDto user = userMapper.getUser("ssoboro");
        OrderDto order = userMapper.getOrder(user.getUserId());

        //then
        assertThat("오인석").isEqualTo(user.getNickName());
        assertThat("입금대기").isEqualTo(order.getStatus());

    }

    @Test
    @DisplayName("조인 데이터 확인")
    @Transactional
    void 조인_데이터_확인(){

        //when
        UserDto user = userMapper.getUser("ssoboro");
        TestDto test = userMapper.getTest(user.getUserId());

        //then
        System.out.println("test : " + test.toString());

    }

    @Test
    @DisplayName("최근주문목록 가져오기")
    @Transactional
    void getOrderLatelyProductList(){

        //when
        //UserDto user = userMapper.getUser("ssoboro");
        List<OrderLatelyProductDto> orderLatelyProductList = userMapper.getOrderLatelyProductList(2);

        //then
        System.out.println("orderLatelyProductList : " + orderLatelyProductList.toString());

    }

}
