package jpabook.jpashop.controller;

import jpabook.jpashop.controller.form.BookForm;
import jpabook.jpashop.controller.form.MemberForm;
import jpabook.jpashop.controller.form.OrderForm;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

//    @GetMapping("/order")
//    @ResponseBody
//    public Order order() {
//        // 멤버 생성
//        Member member = new Member();
//        member.setName("이름asd들z복제");
//        memberService.join(member);
//        // 상품 생성
//        // 상품 가격 (상품 갯수가 맞지 않나?)
//        Book book = new Book();
//        book.setName("bktezxxxsssstasdftest");
//        book.setPrice(1111);
//        book.setStockQuantity(20);
//        itemService.saveItem(book);
//        // 주문
////        orderService.order(member.getId(), book.getId(), 2);
//
//        // 해당 주문 조회 (client에게 쏴주면 된다)
//        return orderService.findOne(
//                orderService.order(member.getId(), book.getId(), 2)
//                );
//
//        // 현재는 jpa가 이상하게 읽는다
//        // 어노테이션 하나 달아줘야함
//        // 어디에? =>  @JsonIgnore onetomany manytomany 위에 적어준다 (관계가 있는 것들 위에 적어주면 됨)
//    }


    @GetMapping("/orders/new")
    public String newOrder(Model model) {
        model.addAttribute("members", memberService.findMembers());
        model.addAttribute("items", itemService.findItems());
        return "/orders/orderForm";
    }

    @PostMapping("/order")
    public String createOrder(OrderForm orderForm) {
        Member member = memberService.findOne(orderForm.getMemberId());
        Item item = itemService.findItem(orderForm.getItemId());
        int count = orderForm.getCount();

        orderService.order(member.getId(), item.getId(), count);

        System.out.println("-----------------------------------");
        System.out.println(orderService.order(member.getId(), item.getId(), count));
        return "redirect:/";
    }

    @GetMapping("/orders")
    public String orderList(Model model) {
        OrderSearch orderSearch = new OrderSearch();

        List<Order> orders = orderService.searchOrders(orderSearch);



        model.addAttribute("orders", orders);
        model.addAttribute("orderSearch", orderSearch);


        return "/orders/orderList";
    }

}
