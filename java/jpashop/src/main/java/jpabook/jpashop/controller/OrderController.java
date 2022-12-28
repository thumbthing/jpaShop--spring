package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    @ResponseBody
    public Order order() {
        // 멤버 생성
        Member member = new Member();
        member.setName("이름asd들z복제");
        memberService.join(member);
        // 상품 생성
        // 상품 가격 (상품 갯수가 맞지 않나?)
        Book book = new Book();
        book.setName("bktezxxxsssstasdftest");
        book.setPrice(1111);
        book.setStockQuantity(20);
        itemService.saveItem(book);
        // 주문
//        orderService.order(member.getId(), book.getId(), 2);

        // 해당 주문 조회 (client에게 쏴주면 된다)
        return orderService.findOne(
                orderService.order(member.getId(), book.getId(), 2)
                );

        // 현재는 jpa가 이상하게 읽는다
        // 어노테이션 하나 달아줘야함
        // 어디에? =>  @JsonIgnore onetomany manytomany 위에 적어준다 (관계가 있는 것들 위에 적어주면 됨)
    }



}
