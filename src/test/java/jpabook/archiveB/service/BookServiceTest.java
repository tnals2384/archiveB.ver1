package jpabook.archiveB.service;

import jpabook.archiveB.domain.Book;
import jpabook.archiveB.domain.Category;
import jpabook.archiveB.repository.BookRepository;
import jpabook.archiveB.web.dto.book.BookSaveRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class BookServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired BookService bookService;
    @Autowired
    BookRepository bookRepository;



    @Test
    void saveBook() throws ParseException, IOException {

        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2021-10-09");
        //given
        BookSaveRequestDto dto = BookSaveRequestDto.builder()
                .title("므레모사").author("김초엽")
                .build();

        Long id=bookService.saveBook(dto);

        Book book = bookRepository.findOne(id).orElseThrow(()
                -> new IllegalArgumentException("해당 책이 없습니다. id"+id));

        Assertions.assertEquals(book.getId(),id);
        Assertions.assertEquals("므레모사",book.getTitle());
        Assertions.assertEquals("김초엽",book.getAuthor());
    }

    @Test
    void BookPaging() throws Exception {
        bookRepository.save(new Book("책1","tnals","","","", LocalDate.of(2022,02,01), Category.NOVEL));
        bookRepository.save(new Book("책2","tnals","","","", LocalDate.of(2022,02,01), Category.NOVEL));
        bookRepository.save(new Book("책3","tnals","","","", LocalDate.of(2022,02,01), Category.NOVEL));
        bookRepository.save(new Book("책4","tnals","","","", LocalDate.of(2022,02,01), Category.NOVEL));
        bookRepository.save(new Book("책5","tnals","","","", LocalDate.of(2022,02,01), Category.NOVEL));

        int offset=0;
        int limit=3;

        List<Book> books= bookRepository.findBooksByPage(offset,limit);

        Assertions.assertEquals(books.size(),3);

    }
}