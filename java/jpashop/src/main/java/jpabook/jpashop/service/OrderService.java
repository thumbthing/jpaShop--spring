package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.TypedQuery;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {


        // 매개변수로 db에서 가지고 와야함
        // entity를 조회해서 가지고 옴
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        // 주문 상품 생성
        // 상품을 통해서 생성하면 됨
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 이제 드디어 주문을 생성할 수 있음
        Order order = Order.createOrder(member, delivery, orderItem);

        // 이제 db에 넣으면 된다
        orderRepository.save(order);
        return order.getId();
    }

    // 이제 주문 취소 만들꺼임
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public Order findOne (Long orderId) {
        return  orderRepository.findOne(orderId);
    }
    // 주문 조회 (동적 조회)
    // 회원명과 주문 상태에 따라 동적으로 달라짐
    public List<Order> searchOrders(OrderSearch orderSearch) {
       return orderRepository.findAll(orderSearch);
    }

}
