package com.boardapplication.controller;

import com.boardapplication.configuration.JoinValidator;
import com.boardapplication.dto.JoinDto;
import com.boardapplication.repository.UserRepository;
import com.boardapplication.service.UserService;
import com.core.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final JoinValidator joinValidator;
    private final UserService userService;
    private final UserRepository userRepository;

    @InitBinder("joinDto")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(joinValidator);
    }

    @GetMapping("/")
    public String index(Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/boardList";
        } else {
            return "/login";
        }

    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("joinDto", new JoinDto());
        return "/join";
    }



    @PostMapping("/join")
    public String joinDto(@Valid JoinDto joinDto, Errors errors) {
        //System.out.println(errors.getAllErrors());
        if (errors.hasErrors()) {
            return "/join";
        }
        User user = userService.processNewUser(joinDto);

        return "redirect:/boardList";
    }

    /*
    @GetMapping("/checkusername")
    public ResponseEntity<String> checkUsername(@RequestParam String username) {
        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.ok("이미 사용중인 아이디입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 아이디입니다.");
        }
    }

     */


    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }


}
