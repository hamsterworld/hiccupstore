package hiccup.hiccupstore.userpick.service;

import hiccup.hiccupstore.userpick.dao.UserPickMapper;
import hiccup.hiccupstore.userpick.dto.UserPick;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserPickService {
    private final UserPickMapper userPickMapper;

    public ArrayList<UserPick> findAllByUserId(Integer userId){return  userPickMapper.findAllByUserId(userId);};
    public void insertUserPick(Integer userId, Integer productId){userPickMapper.insertUserPick(userId,productId);};
    public void deleteUserPick(Integer userId, Integer productId){userPickMapper.deleteUserPick(userId,productId);};

    public boolean existProduct(Integer userId, Integer productId){
        UserPick userPick = userPickMapper.findByUserIdAndProductId(userId, productId);
        if(userPick == null){
            return false;
        }
        else{
            return true;
        }
    }




}
