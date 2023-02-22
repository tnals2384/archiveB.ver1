package jpabook.archiveB.service;

import jpabook.archiveB.domain.Book;
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
    void saveBook() {

        //given
        BookSaveRequestDto dto = BookSaveRequestDto.builder()
                .title("title1").author("author1")
                .build();

        Long id=bookService.saveBook(dto);

        Book book = bookRepository.findOne(id).orElseThrow(()
                -> new IllegalArgumentException("해당 책이 없습니다. id"+id));

        Assertions.assertEquals(book.getId(),id);
        Assertions.assertEquals("title1",book.getTitle());
        Assertions.assertEquals("author1",book.getAuthor());
    }
}