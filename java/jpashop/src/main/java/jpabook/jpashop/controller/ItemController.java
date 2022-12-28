package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

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

    @GetMapping("/items/new")
    @ResponseBody
    public Item newItem(@RequestParam String name) {
        Book book = new Book();
        book.setName(name);


        return itemService.findItem(itemService.saveItem(book));

    }

}
