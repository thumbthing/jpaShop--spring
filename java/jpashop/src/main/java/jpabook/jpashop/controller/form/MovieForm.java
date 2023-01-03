package jpabook.jpashop.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MovieForm {
    private Long id;
    @NotEmpty(message = "영화명 입력은 필수")
    private String name;
    private int price;
    private int stockQuantity;
    private String director;
    private String actor;
}
