package hiccup.hiccupstore.user;


import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JoinTest {

    @Autowired
    UserMapper userMapper;

    @Test
    @DisplayName("회원가입시 정상적으로 DB에 회원정보가 반영되는지 확인")
    @Transactional
    void memberSave(){

        //given
        UserDto userDto = UserDto.builder().userName("잘들어가겟지").build();

        //when
        Integer save = userMapper.save(userDto);

        //then
        assertThat(1).isEqualTo(save);

    }

    @Test
    @DisplayName("DB에서 유저를 정상적으로 가지고오는지 확인")
    @Transactional
    void getMember(){

        //given
        UserDto userDto = UserDto.builder().userName("잘들어가겟지").build();
        userMapper.save(userDto);

        //when
        UserDto member = userMapper.getUser(userDto.getUserName());

        //then
        assertThat(userDto.getUserName()).isEqualTo(member.getUserName());

    }


}
