package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// db랑 매핑이 되는것
// order by ... 라는 쿼리 문이 있기 때문에 orders로 적어준다
@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    // member를 FK로 설정
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // 이건 지연 로딩 해주는것 LAZY 바로 가져오는 것보다 이게 더 안전하다
    // 테이블과 join 할 것인데 (member와)
    @JoinColumn(name = "member_id") // db의 FK
    private Member member; //fk (멤버를 가르키고 있다)
    // 근데 list는 db에 어케 담아야함????
    // FK 가 있어야 가능함
    // 1:n 관계일 경우 n에 해당하는 테이블에 FK를 설정해줘야함
    @JsonIgnore
    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL) // 연관 관계의 주인이 아닌 곳은 mappedby 해줘야한다
    private List<OrderItem> orderItems = new ArrayList<>();

    @JsonIgnore //                            cascade는 상태변경을 연관 테이블 에도 적용을 시켜줘야한다 entity의 변환 상태
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // order를 주인으로 한다 그래서 fetch 해주는 거임
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태를 나타냄 (order, cancel)

    // domain 주도 개발
    // 기존에는 필드만 선언했었는데
    // 지금은 domain 안에 메소드를 선언
    // 연관관계에 대해서 작성할 꺼임
    // ==== 연관관계 메소드 ====
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this); // 여기서 this는 order    member가 order를 가질 수 있도록 add 해주는 것임
        // 상대 테이블에서도 알려줘야한다
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // 비즈니스 로직

    // 주문 등록
    // 주문내역 조회
    //                                                                  여러개 받으려고 ... 써줌
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }
    // 주문 취소
    public void cancel() {
        if (this.delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송 완료 되었습니다");
        }
        this.setStatus(OrderStatus.CANCEL);
        // 상품 취소시 재고 증가 시켜야함
        // OrderItem을 증가시키면 된다
        // 주문에 대한 주문 상품들이 여러개 있을 수 있다
        for(OrderItem orderItem : orderItems) {

            orderItem.cancel();
        }
    }

    // 전체주문 가격 조회
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem: orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    // 주문 상품
}
