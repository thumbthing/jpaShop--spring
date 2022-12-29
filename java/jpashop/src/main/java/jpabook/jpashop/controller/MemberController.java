package jpabook.jpashop.controller;

import jpabook.jpashop.controller.form.MemberForm;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String join(@Valid MemberForm form, BindingResult result) {
        //              MemberForm에 추가한 어노테이션 @NotEmpty
        //              이름을 입력 안하면 에러뜨도록 하는것
        //                                      bindingresult 이걸 써주면
        //                                      에러가 있는지 체크해줌
        //                                      form을 보고 판단
        //                                      결과 객체를 만듦
        //
        //      model이 없는데 view에서는 값이 살아있다
        //      그렇다면 result는 model 역할을 같이 해준다고 생각하면 될듯
        //              저기 위에 있는 매개변수, 어노테이션들은 무조건 작성을 해줘야
        //              위에 작성한 것 처럼 작동을 함
        //                              result가 model같은 역할을 해줌
        if(result.hasErrors()) {
            System.out.println(result.getTarget());
            // 다시 view를 던져준다
            return "members/createMemberForm";
            // client에게 뭔가를 보여주면 좋을 것 같다
        }


        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(new Address(
                                form.getCity(),
                                form.getStreet(),
                                form.getZipcode()));
//        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
//        member.setAddress(address);

        System.out.println("---------------------------");
        System.out.println(member.getName());
        System.out.println(member.getAddress());
        System.out.println(member.getAddress().getCity());
        System.out.println(member.getAddress().getStreet());
        System.out.println(member.getAddress().getZipcode());
        System.out.println("---------------------------");

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String memberList(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

//    @GetMapping("/members/new")
//    public Member join(@RequestParam("name") String name) {
        // view : members/createMemberForm

//        // 회원 만들고
//        // 임의로 만들고
//        // db 저장해서
//        // db에서 해당 회원 불러오고
//        // 그 결과를 클라이언트로 응답
//        Member member = new Member();
////        member.setId(1L);
//        member.setName(name);
//        member.setAddress(new Address("asdf","qwer","zxcv"));
//
//        System.out.println(member);

//        return memberService.findOne(memberService.join(member));
//    }


}
