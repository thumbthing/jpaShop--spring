package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    @ResponseBody
    public Member join(@RequestParam("name") String name) {
        // 회원 만들고
        // 임의로 만들고
        // db 저장해서
        // db에서 해당 회원 불러오고
        // 그 결과를 클라이언트로 응답
        Member member = new Member();
//        member.setId(1L);
        member.setName(name);
        member.setAddress(new Address("asdf","qwer","zxcv"));

        System.out.println(member);

        return memberService.findOne(memberService.join(member));
    }


}
