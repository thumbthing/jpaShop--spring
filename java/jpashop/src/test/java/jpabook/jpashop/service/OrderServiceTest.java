package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    ItemService itemService;
    @Autowired
    MemberService memberService;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ItemRepository itemRepository;

    // 테스트 끝나고 실행해 줄 것
    // 비워주는 걸 하고 싶은데 현재 이 프로젝트는
    // 쿼리를 날려줘야하기 때문에 조금 복잡하다
    // @BeforeEach

//    @AfterEach
//    public void afterEach() {
//        em.clear();
//    }

    @Test
    public void 주문취소() {
        // 일단은 먼저 주문을 하고
        // 그걸 취소
        Member member = new Member();
        member.setName("testname");
        Long memberId = memberService.join(member);

        Item item = new Book();
        item.setName("testbook");
        item.setPrice(999);
        item.setStockQuantity(888);
        Long itemId = itemService.saveItem(item);

        Long orderId = orderService.order(memberId, itemId, 1);
        System.out.println("testbook 의 재고 " + item.getStockQuantity());

        //when
        orderService.cancelOrder(orderId);

        //then

        Order order = orderRepository.findOne(orderId);

        // 검증
        // 1. orderstatus 가 cancel로 변경
        // 2. 주문 취소된 상품은 그만큼 재고가 그만큼 증가 되어야함
        assertEquals(order.getStatus(), OrderStatus.CANCEL);
        System.out.println(order.getStatus());
        assertEquals(item.getStockQuantity(), 888);
        System.out.println("testbook 의 재고 " + item.getStockQuantity());

        System.out.println("------------------------------------------------------------------------");
    }

    @Test
    void order() {
        // 회원 등록을하고
        Member member = new Member();
        member.setName("orderNametest");
        Long memberId = memberService.join(member);

        // 상품 등록을 하고
        Item item = new Book();
        item.setName("orderbooknametest");
        item.setPrice(9999);
        item.setStockQuantity(9999);
        Long itemId = itemService.saveItem(item);

        // 그것이 잘 들어갔는지 확인을 해야함
        Long orderId = orderService.order(memberId, itemId, 9);

        Order order = orderRepository.findOne(orderId);

        assertEquals(order.getStatus(), OrderStatus.ORDER);
        System.out.println(order.getStatus());
        assertEquals(item.getStockQuantity(), 9990);
        System.out.println(item.getStockQuantity());





    }
}