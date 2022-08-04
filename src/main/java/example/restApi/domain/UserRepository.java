package example.restApi.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    // 목록
    private static final Map<Long, User> users = new HashMap<>();
    private static long sequence = 0L;


    public User save (User user){
        user.setId( ++sequence );
        users.put(user.getId(), user);
        return user;
    }

    public User findById( Long id) {
        return users.get(id);
    }

    public List<User> findAll(){
        return new ArrayList<>(users.values());
    }

    public void Update(Long id, UpdateUserForm updateUser){
        User user = users.get(id);
        user.setPassWord(updateUser.getPassWord());
        user.setPhoneNumber(updateUser.getPhoneNumber());
        user.setAddress(updateUser.getAddress());
    }

}
