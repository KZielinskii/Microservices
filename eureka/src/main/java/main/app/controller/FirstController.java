package main.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first")
public class FirstController {

    @GetMapping("/message")
    public String test() {
        System.out.println("First Service");
        return "Hello!\nThis is the First Service";
    }

    @GetMapping("/shout")
    public String shout() {
        return "roar";
    }
}