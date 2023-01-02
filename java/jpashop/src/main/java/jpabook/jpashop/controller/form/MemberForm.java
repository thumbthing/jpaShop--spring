package jpabook.jpashop.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


// 이거 쓰는 이유?
// 나중에 가면 entity가 복잡해진다
// 실제로 데이터 받을때 로직을 추가해줄 수도 있다
// 그러면 종속성이 생겨서 복잡해진다
// 그걸 방지하기 위해 데이터 받을 용도로
// form을 만들어준다
@Getter
@Setter
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;
    @NotEmpty(message = "도시명 입력은 필수 입니다.")
    private String city;
    private String street;
    @Size(min = 4,max = 5)
    private String zipcode;
}
