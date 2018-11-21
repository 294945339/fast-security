package io.fast.modules.sys.login.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: dj
 * @Date: 2018\10\19 0019 11:36
 * @Description:
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/login.html")
    public String login() {
        return "login.html";
    }


    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login.html";
    }
}
