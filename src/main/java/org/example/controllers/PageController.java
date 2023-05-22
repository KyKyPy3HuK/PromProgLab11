package org.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Neil Alishev
 */
@Controller
public class PageController {
    @GetMapping("/hello-world")
    public String sayHello(@RequestParam(value = "arg", required = false) String arg, Model model) {
        model.addAttribute("arg",arg);

        return "pages/hello_world";
    }
    @GetMapping("/hell")
    public String sayHelloHell() {
        return "pages/hello_hell";
    }
}