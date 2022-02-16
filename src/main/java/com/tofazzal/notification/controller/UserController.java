package com.tofazzal.notification.controller;

import com.tofazzal.notification.config.secuirty.JwtTokenUtil;
import com.tofazzal.notification.config.secuirty.UserDetailsServiceImpl;
import com.tofazzal.notification.constants.AppConstants;
import com.tofazzal.notification.model.User;
import com.tofazzal.notification.service.UserService;
import com.tofazzal.notification.service.viewModel.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Tofazzal
 */

@CrossOrigin
@RestController
@RequestMapping(value = "api/" + AppConstants.API_VERSION +"/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;

    public UserController(UserService userService, UserDetailsServiceImpl userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<Response<User>> save(@RequestBody User user) throws Exception{
        if(userService.findByUserName(user.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body(new Response<>(HttpStatus.BAD_REQUEST.value(),
                    AppConstants.USER_NAME_ALREADY_EXIST + user.getUsername(), null));
        }

        return ResponseEntity.ok().body(new Response<>(HttpStatus.CREATED.value(),
                AppConstants.USER_REGISTERED_SUCCESS, userService.save(user)));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Response<User>> update(@PathVariable Long id, @RequestBody User user) throws Exception{
        Optional<User> optionalUser = userService.findByUserName(user.getUsername());
        if(optionalUser.isPresent()) {
            if (user.getId() != optionalUser.get().getId()) {
                return ResponseEntity.badRequest().body(new Response<>(HttpStatus.BAD_REQUEST.value(),
                        AppConstants.USER_NAME_ALREADY_EXIST + user.getUsername(), null));
            }
        }

        return ResponseEntity.ok().body(new Response<>(HttpStatus.CREATED.value(),
                AppConstants.USER_REGISTERED_SUCCESS, userService.update(id, user)));
    }
    @MessageMapping("/push-message-mapping")
    @SendTo("/topic/request-list")
    @GetMapping
    public ResponseEntity<Response<List<User>>> list()  throws Exception{
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.USER_FETCH_SUCCESS, userService.getList()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<User>> findById(@PathVariable Long id)  throws Exception{
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.USER_FETCH_SUCCESS, userService.findById(id)));
    }

}
