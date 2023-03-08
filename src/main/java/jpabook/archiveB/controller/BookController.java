package jpabook.archiveB.controller;


import jpabook.archiveB.domain.Book;
import jpabook.archiveB.repository.BookSearch;
import jpabook.archiveB.service.BookService;
import jpabook.archiveB.web.dto.book.BookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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


    @GetMapping("/books/detail/{bookId}")
    public String BookDetail(@PathVariable("bookId") Long bookId, Model model) {
        BookResponseDto bookDto = bookService.findById(bookId);
        model.addAttribute("book",bookDto);
        return "books/detail"; 
    }

    @GetMapping("/books/search")
    public String BookSearch(@ModelAttribute("bookSearch")BookSearch bookSearch, Model model) {
        model.addAttribute("searchBooks", bookService.searchBook(bookSearch));
        return "books/search";
    }


}
