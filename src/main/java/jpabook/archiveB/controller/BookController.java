package jpabook.archiveB.controller;


import jpabook.archiveB.base.BaseException;
import jpabook.archiveB.domain.Member;
import jpabook.archiveB.service.BookService;
import jpabook.archiveB.service.CommentService;
import jpabook.archiveB.service.MemberService;
import jpabook.archiveB.web.dto.CommentRequestDto;
import jpabook.archiveB.web.dto.CommentResponseDto;
import jpabook.archiveB.web.dto.book.BookResponseDto;
import jpabook.archiveB.web.dto.book.BookSaveRequestDto;
import jpabook.archiveB.web.dto.book.BookSearch;
import jpabook.archiveB.web.dto.book.BookUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
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
    public String BookList(Model model,@RequestParam(defaultValue = "0") int page) throws BaseException {
        model.addAttribute("books",bookService.findBooks(page));
        return "books/bookList";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/books/list")
    public String AdminBookList(Model model, int page) throws BaseException {
        model.addAttribute("books",bookService.findBooks(page));
        return "books/adminBookList";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("admin/books/add")
    public String BookSave(Model model)  {
        model.addAttribute("bookSaveRequestDto",new BookSaveRequestDto());
        return "books/save";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("admin/books/add")
    public String BookSave(@Valid BookSaveRequestDto requestDto) throws IOException {
        Long bookId = bookService.saveBook(requestDto);
        return "redirect:/admin/books/"+bookId;
    }

    @GetMapping("/books/{bookId}")
    public String BookDetail(@PathVariable("bookId") Long bookId,
                             @RequestParam(defaultValue = "0") int commentPage,
                             Model model,Principal principal)
            throws BaseException,IOException {
        // book 조회
        BookResponseDto bookDto = bookService.findById(bookId);
        model.addAttribute("book",bookDto);

        // 댓글 조회
        List<CommentResponseDto> comments = commentService.findAllbyBookId(bookId,commentPage);
        model.addAttribute("comments", comments);
        //코멘트 입력을 받아올 dto model에 추가
        model.addAttribute("commentDto",new CommentRequestDto());
        //현재 로그인한 사용자 정보
        Member currentMember;
        try {
         currentMember = memberService.getUser(principal.getName());
        model.addAttribute("currentMember",currentMember);

        } catch (Exception e) {
             currentMember = null;
        }
        return "books/detail";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/books/{bookId}")
    public String BookUpdate(@PathVariable("bookId") Long bookId,
                             @RequestParam(defaultValue = "0") int commentPage,
                             Model model,Principal principal)
            throws BaseException,IOException {
        // book 조회
        BookResponseDto bookDto = bookService.findById(bookId);

        BookUpdateRequestDto updateDto = BookUpdateRequestDto.builder()
                .id(bookId)
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .isbn(bookDto.getIsbn())
                .plot(bookDto.getPlot())
                .publicationDate(bookDto.getPublicationDate())
                .category(bookDto.getCategory())
                .build();
        model.addAttribute("bookImg",bookDto.getCoverImg());
        model.addAttribute("updateDto",updateDto);


        // 댓글 조회
        List<CommentResponseDto> comments = commentService.findAllbyBookId(bookId,commentPage);
        model.addAttribute("comments", comments);

        return "books/adminDetail";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/books/{bookId}")
    public String BookUpdate(@PathVariable("bookId") Long bookId, @ModelAttribute("updateDto") @Valid BookUpdateRequestDto updateDto)
            throws BaseException,IOException {
        bookService.updateBook(bookId,updateDto);
        return "redirect:/admin/books/"+bookId;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/books/{bookId}/delete")
    public String bookDelete(@PathVariable Long bookId) throws BaseException, IOException{
        // book 조회
        BookResponseDto bookDto = bookService.findById(bookId);

        bookService.deleteBook(bookDto.getId());

        return "redirect:/admin/books/list";
    }


    @GetMapping("/books/search")
    public String BookSearch(@ModelAttribute("bookSearch")BookSearch bookSearch, Model model) {
        model.addAttribute("searchBooks", bookService.searchBook(bookSearch));
        return "books/search";
    }


}
