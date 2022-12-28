package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    // 테이블 이름을 지정해 주는 것
    private Long id;
    private String name;
    @Embedded
    private Address address;

    // Order는 다른 테이블과 연관 관계를 맺고 있다 (Member - Order = 1:n)
    // 밑에 있는 어노테이션을 적어줘야한다
    //            관계의 주인이 누구냐? FK가 누구냐? 뭐냐? (뭔소리지...?\)
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
