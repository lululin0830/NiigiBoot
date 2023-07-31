package tw.idv.tibame.users.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import tw.idv.tibame.users.entity.Users;
import tw.idv.tibame.users.service.UserService;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("Login")
    public Users login(HttpServletRequest request, @RequestBody Map<String, String> requestBody) {
        String userAcct = requestBody.get("userAcct");
        String password = requestBody.get("password");
        
        Users users = new Users();

        users.setUserAcct(userAcct);
        users.setPassword(password);
        users = userService.login(users);
        if (users.isSuccessful()) {
            if (request.getSession(false) != null) {
                request.changeSessionId();
            }
            final HttpSession httpSession = request.getSession();
            httpSession.setAttribute("loggedin", true);
            httpSession.setAttribute("users", users);
        }
        return users;
    }
}

