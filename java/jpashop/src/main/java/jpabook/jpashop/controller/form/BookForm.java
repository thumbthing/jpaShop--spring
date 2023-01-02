package jpabook.jpashop.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BookForm {
    private Long id;
    @NotEmpty(message = "도서명 입력은 필수")
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;
}
