package jpabook.archiveB.controller;


import jpabook.archiveB.base.BaseException;
import jpabook.archiveB.base.BaseResponse;
import jpabook.archiveB.domain.Book;
import jpabook.archiveB.domain.Comment;
import jpabook.archiveB.domain.Member;
import jpabook.archiveB.service.CommentService;
import jpabook.archiveB.service.MemberService;
import jpabook.archiveB.web.dto.CommentRequestDto;
import jpabook.archiveB.web.dto.CommentResponseDto;
import jpabook.archiveB.web.dto.book.BookSearch;
import jpabook.archiveB.service.BookService;
import jpabook.archiveB.web.dto.book.BookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
//@RestController << baseResponse 응답
@Controller
public class BookController {
    private final BookService bookService;
    private final CommentService commentService;
    private final MemberService memberService;

/*    //book list 불러오기
    @GetMapping("/books/list")
    public BaseResponse<List<BookResponseDto>> BookList()  {
        try {
            List<BookResponseDto> books = bookService.findBooks();
            return new BaseResponse<>(books);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    @GetMapping("/books/{bookId}")
    public BaseResponse<BookResponseDto> BookDetail(@PathVariable("bookId") Long bookId) {
        try {
            BookResponseDto bookDto = bookService.findById(bookId);
            return new BaseResponse<>(bookDto);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }*/

    //book list 불러오기
    @GetMapping("/books/list")
    public String BookList(Model model) throws BaseException {
        model.addAttribute("books",bookService.findBooks());
        return "books/bookList";
    }


    @GetMapping("/books/{bookId}")
    public String BookDetail(@PathVariable("bookId") Long bookId, Model model,Principal principal) throws BaseException {
        // book 조회
        BookResponseDto bookDto = bookService.findById(bookId);
        model.addAttribute("book",bookDto);

        // 댓글 조회
        List<CommentResponseDto> comments = commentService.findAllbyBookId(bookId);
        model.addAttribute("comments", comments);

        //현재 로그인한 사용자 정보
        Member currentMember = memberService.getUser(principal.getName());
        model.addAttribute("currentMember",currentMember);

        return "books/detail";
    }
    @GetMapping("/books/search")
    public String BookSearch(@ModelAttribute("bookSearch")BookSearch bookSearch, Model model) {
        model.addAttribute("searchBooks", bookService.searchBook(bookSearch));
        return "books/search";
    }


}
