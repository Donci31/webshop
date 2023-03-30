package hu.bme.aut.webshop.alf2023javant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/home")
    @ResponseBody
    public String home() {
        return "You are now logged in.";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "You are now logged into admin.";
    }
}
