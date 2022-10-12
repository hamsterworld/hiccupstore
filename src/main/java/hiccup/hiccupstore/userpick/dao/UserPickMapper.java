package hiccup.hiccupstore.userpick.dao;

import hiccup.hiccupstore.userpick.dto.UserPick;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Mapper
@Repository
public interface UserPickMapper {

    ArrayList<UserPick> findAllByUserId(Integer userId);
    void insertUserPick(Integer userId, Integer productId);
    void deleteUserPick(Integer userId, Integer productId);
    UserPick findByUserIdAndProductId(Integer userId, Integer productId);






}




