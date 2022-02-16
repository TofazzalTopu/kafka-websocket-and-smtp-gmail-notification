package com.tofazzal.notification.service.impl.user;

import com.tofazzal.notification.model.User;
import com.tofazzal.notification.repository.UserRepository;
import com.tofazzal.notification.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Tofazzal
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getList() throws Exception{
        List<User> userList = userRepository.findAll();
        return userList;
    }

    @Override
    public User save(User user)  throws Exception{
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user = userRepository.save(user);
            user.setPassword(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not exist with the id: "+ id));
    }

    @Override
    public Optional<User> findByUserName(String username) throws Exception {
        return userRepository.findByUsername(username);
    }

    @Override
    public User update(Long id, User user) throws Exception {
        try {
            User existUser = findById(id);
            existUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user = userRepository.save(existUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
