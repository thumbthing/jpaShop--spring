package jpabook.jpashop.controller;

import jpabook.jpashop.controller.form.BookForm;
import jpabook.jpashop.controller.form.MemberForm;
import jpabook.jpashop.controller.form.OrderForm;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// log 로 보여주는 어노테이션
@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/orders/new")
    public String createOrderForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "/orders/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count
                        ) {
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }




    //                                                  submit했을때 변하는 값을 받아주기위해
    //                                                  변수로 받아줘야한다
    //                      model.addAttribute
    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch,
                            Model model) {
        List<Order> orders = orderService.searchOrders(orderSearch);

        model.addAttribute("orders", orders);

        return "/orders/orderList";
        // submit을 하면 orderSearch의 상태가 바뀐다
        // 다시 서버로 들어간다

    }

    @PostMapping("/orders/{id}/cancel")
    public String cancelOrder(@PathVariable("id") Long id) {

        orderService.cancelOrder(id);

        return "redirect:/orders";
    }

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


//    @PostMapping("/order")
//    public String createOrder(OrderForm orderForm) {
//        Member member = memberService.findOne(orderForm.getMemberId());
//        Item item = itemService.findItem(orderForm.getItemId());
//        int count = orderForm.getCount();
//
////        log.info(orderForm.getMemberId().toString());
//
//        orderService.order(member.getId(), item.getId(), count);
//        return "redirect:/orders";
//    }
    // 주문 수량이 재고량보다 많을때의 뭔가를 만들어줘야한다

//    @GetMapping("/orders")
//    public String orderList(Model model) {
//        // orderSearch와 orders 를 가져와야한다
//
//        // 처음에 전체를 다 보내주는 걸 작성
//        OrderSearch orderSearch = new OrderSearch();
//
//        List<Order> orders = orderService.searchOrders(orderSearch);
//
//        model.addAttribute("orders", orders);
//        model.addAttribute("orderSearch", orderSearch);
//
//        return "/orders/orderList";
//    }
//    // 회원명 OrderSearch 에 주문한 회원 만 뜨도록 해야할듯
}
