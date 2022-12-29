package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // jpa 가 기본 생성자를 요구를 하기 때문에
    // 기본 생성자는 무조건 써줘야함
    // 일단은 접근제한자를 둬서 기본 생성자를 막아뒀음
    protected Address() {

    }
    // 함부로 변경하지 못하게 할려고
    // 생성자를 만들어 줬음
    // 사용하려면 생성자로 생성해서
    // 넣어주면 된다
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
