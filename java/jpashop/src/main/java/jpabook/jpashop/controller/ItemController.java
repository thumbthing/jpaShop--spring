package jpabook.jpashop.controller;

import jpabook.jpashop.controller.form.AlbumForm;
import jpabook.jpashop.controller.form.BookForm;
import jpabook.jpashop.controller.form.MovieForm;
import jpabook.jpashop.domain.Album;
import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Movie;
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
    // 3가지 카테고리 페이지 띄워주기
    @GetMapping("/items/new")
    public String newItem(Model model) {
        return "items/createItemForm";
    }

    // book getmapping
    @GetMapping("/items/newBook")
    public String newBook(Model model) {
        BookForm bookForm = new BookForm();
        model.addAttribute("form", bookForm);
        return "items/bookCreate";
    }
    // book post
    @PostMapping("/items/book/create")
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

        itemService.saveItem(book);
        return "redirect:/";
    }

    // album getmapping
    @GetMapping("/items/newAlbum")
    public String newAlbum(Model model) {
        AlbumForm albumForm = new AlbumForm();
        model.addAttribute("form", albumForm);
        return "items/albumCreate";
    }
    // album post
    @PostMapping("/items/album/create")
    public String join(@Valid AlbumForm form, BindingResult result) {
        if(result.hasErrors()) {
            System.out.println(result.getTarget());
            return "items/createItemForm";
        }
        Album album = new Album();
        album.setName(form.getName());
        album.setPrice(form.getPrice());
        album.setStockQuantity(form.getStockQuantity());
        album.setArtist(form.getArtist());
        album.setEtc(form.getEtc());

        itemService.saveItem(album);
        return "redirect:/";
    }


    // movie getmapping
    @GetMapping("/items/newMovie")
    public String newMovie(Model model) {
        MovieForm movieForm = new MovieForm();
        model.addAttribute("form", movieForm);
        return "items/movieCreate";
    }
    // movie post
    @PostMapping("/items/movie/create")
    public String join(@Valid MovieForm form, BindingResult result) {
        if(result.hasErrors()) {
            System.out.println(result.getTarget());
            return "items/createItemForm";
        }
        Movie movie = new Movie();
        movie.setName(form.getName());
        movie.setPrice(form.getPrice());
        movie.setStockQuantity(form.getStockQuantity());
        movie.setDirector(form.getDirector());
        movie.setActor(form.getActor());

        itemService.saveItem(movie);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String itemList(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{id}/edit")
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

    @PostMapping("/items/{id}/edit")
    public String updateItem(@PathVariable("id") Long id, BookForm item) {
      // db 에서 가져와야하 한다
        Book book = (Book) itemService.findItem(id);

        book.setName(item.getName());
        book.setPrice(item.getPrice());
        book.setStockQuantity(item.getStockQuantity());
        book.setAuthor(item.getAuthor());
        book.setIsbn(item.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";

        // redirect 뒤에 /를 안붙이면 url 뒤에 붙는다
        // 위에 있는 컨트롤러로 가게 하던지
        // 아니면 경로를 제대로 입력하던지 그렇게 해야할듯

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
