package com.login.controller;


import com.login.domain.Member;
import com.login.form.MemberForm;
import com.login.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping ("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/create";
    }

    @PostMapping("/members/create")
    public String join(@Valid MemberForm form, HttpServletResponse response) {
        Member member = new Member();
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        memberService.join(member);

        Cookie idCookie = new Cookie("membername" , String.valueOf(member.getName()));
        response.addCookie(idCookie);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(@CookieValue(name = "membername", required = false) String membername, Model model) {
        if (membername == null) {
            return "/";
        }

        Member loginMember = memberService.findOne(membername);
        if(loginMember == null) {
            return "/";
        }

        model.addAttribute("member", loginMember);
        return "home";

    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        expiredCookie(response, "memeberId");
        return "redirect:/";
    }

    private void expiredCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
