package com.jeong.getta.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {
    @RequestMapping("/")
    fun homeRedirect(): String = "redirect:/swagger-ui.html"
}