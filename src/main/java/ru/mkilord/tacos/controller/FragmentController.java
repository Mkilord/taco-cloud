package ru.mkilord.tacos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class FragmentController {
    @GetMapping("/menu")
    public String menuPage(){
        return "fragments/menu";
    }
}
