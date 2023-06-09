package com.thomas.EWD_PROJECT_LIBR;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout, Model model) {

        if (error != null) {
            model.addAttribute("error", PropertyLoader.getProperty("FoutieveLogin"));
        }
        if (logout != null) {
            model.addAttribute("msg", PropertyLoader.getProperty("SuccesLogout"));
        }

        model.addAttribute("pl", new PropertyLoader());
        model.addAttribute("ign" , PropertyLoader.getProperty("ign"));
        model.addAttribute("pw" , PropertyLoader.getProperty("pw"));
        model.addAttribute("mainHeader" , PropertyLoader.getProperty("mainHeader"));
        model.addAttribute("Title" , PropertyLoader.getProperty("Title"));

        return "login";
    }
}