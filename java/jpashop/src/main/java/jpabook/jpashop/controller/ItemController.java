package jpabook.jpashop.controller;

import jpabook.jpashop.controller.form.BookForm;
import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("items/new")
    public String newItem(Model model) {

        model.addAttribute("form", new BookForm());

        return "items/createItemForm";
    }

    @PostMapping("items/new")
    public String join(@Valid BookForm form, BindingResult result) {
        if(result.hasErrors()) {
            System.out.println(result.getTarget());
            return "items/createItemForm";
        }
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        System.out.println(book);
        System.out.println(book.getAuthor());
        System.out.println(book.getId());
        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("items")
    public String itemList(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{id}/edit")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        Item item = itemService.findItem(id);
        // 강제형변환으로 해도 될듯
//        Book item = (Book) itemService.findItem(id);
        model.addAttribute("item", item);
        // view 쪽의 th:object="${form}"
        // 이거를 item으로 바꿔주는게 제일 깔끔할듯

        return "items/editItem";
    }

    @PostMapping("items/{id}/edit")
    public String updateItem(@PathVariable("id") Long no, BookForm item) {

        Book book = itemService.findItem(no);

        book.setName(item.getName());
        book.setPrice(item.getPrice());
        book.setStockQuantity(item.getStockQuantity());
        book.setAuthor(item.getAuthor());
        book.setIsbn(item.getIsbn());


        System.out.println("--------------------");
        System.out.println("컨트롤러");
        System.out.println(book.getId());
        System.out.println(book.getName());
        System.out.println(book.getPrice());
        System.out.println(book.getIsbn());
        System.out.println(book.getStockQuantity());
        System.out.println(book.getAuthor());
        System.out.println("---------------------");

        itemService.update(book);
        return "redirect:items/itemList";
    }







//    @GetMapping("/items/new")
//    @ResponseBody
//    public Item join() {
//
//        item.setName("itemTest");
//        item.setPrice(900);
//        item.setStockQuantity(200);
//
//        return itemService.findItem(item.getId());
//
//    }

//    @GetMapping("/items/new")
//    @ResponseBody
//    public Item newItem(@RequestParam String name) {
//        Book book = new Book();
//        book.setName(name);
//
//
//        return itemService.findItem(itemService.saveItem(book));
//
//    }

}
