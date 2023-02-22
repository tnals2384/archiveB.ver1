package jpabook.archiveB.controller;


import jpabook.archiveB.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class BookController {
    private final BookService bookService;


    //book list 불러오기
    @GetMapping("/books/list")
    public String BookList(Model model) {
        model.addAttribute("books",bookService.findBooks());
        return "books/bookList";
    }
}
