package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    // 주문 조회 (동적 조회)
    // 회원명과 주문 상태에 따라 동적으로 달라짐
    public List<Order> findAll(OrderSearch orderSearch) {
        // 서버로 jpql을 보내줘야함
        // 주문을 한 회원의 모든 목록을 join 해서 보내줘야함
        // 이거는 우리가 쿼리를 만드는거임

        String jpql = "select o From Order o join o.member m";
        boolean isFirstCondition = true;


        // 회원과 주문상태를 확인해서
        // 동적 쿼리를 생성하고
        // db에게 그 쿼리를 넘겨준다
        // jpa에게 전달할것이기 때문에
        // jpql을 작성해서 jpa에게 전달하면 된다

        // 주문상태가 있으면 if문을 실행
        if (orderSearch.getOrderStatus() != null) {
            if(isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        //      text 있는지 확인하는것
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }
                                                                    // 클라이언트가 볼 행의 최대 수
        TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000);

        if(orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if(StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
        // 이거보다 간단하게 작성할 수 있는 querydsl이라는게 있으니 한번 알아보자
    }


}
