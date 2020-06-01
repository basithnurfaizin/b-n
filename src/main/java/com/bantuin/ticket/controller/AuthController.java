package com.bantuin.ticket.controller;

import com.bantuin.ticket.constants.Generated;
import com.bantuin.ticket.model.User;
import com.bantuin.ticket.response.UserResponse;
import com.bantuin.ticket.service.UserService;
import com.bantuin.ticket.util.ErrorDefinition;
import com.bantuin.ticket.util.LogUtil;
import com.bantuin.ticket.util.SecureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController extends BaseApiController {

    public static final String TAG = "LoginController";

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String greeting(){
        return "Greeting from spring boot, version: " + Generated.VERSION;
    }

    @RequestMapping("/api/auth/login")
    public ResponseEntity<Map> login(
            HttpServletRequest request,
            @RequestParam("username")String username,
            @RequestParam("password")String password) throws Exception {
        LogUtil.debug(TAG, "do login");
        User user = userService.getByUsername(username);
        Map response = new HashMap();
        if(user == null) {
            response.put("message",ErrorDefinition.ERROR.USER_NOT_FOUND.name());
            return ok(response);
        }

        if(!(user.getPassword() != null && SecureUtil.getMd5(password).equals(user.getPassword()))) {
            response.put("message",ErrorDefinition.ERROR.PASSWORD_NOT_VALID.name());
            response.put("error", ErrorDefinition.ERROR.PASSWORD_NOT_VALID.getMessage());
            return ok(response);
        }

        String tokenString = SecureUtil.generateRandomToken(user.getName() + user.getPassword());

        user.setToken(tokenString);

        userService.save(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getToken(), null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        com.bantuin.ticket.response.User responseUser = new com.bantuin.ticket.response.User();
        user.setId(user.getId());
        user.setUsername(user.getUsername());


        response.put("token", tokenString);
        response.put("user", responseUser);

        UserResponse userResponse = new UserResponse();
        userResponse.setUser(responseUser);
        userResponse.setToken(user.getToken());

        return ok(response);
    }
}
