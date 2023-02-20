package jpabook.archiveB.service;

import jpabook.archiveB.domain.Book;
import jpabook.archiveB.repository.BookRepository;
import jpabook.archiveB.repository.CommentRepository;
import jpabook.archiveB.web.dto.BookSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;


    @Transactional
    public Long saveBook(BookSaveRequestDto bookDto) {
        Book book = bookDto.toEntity();
        bookRepository.save(book);
        return book.getId();
    }

    @Transactional
    public Long updateBook(Long id, BookSaveRequestDto dto) {
        Book book= findOne(id);
        book.updateBook(dto.getTitle(), dto.getAuthor(),
                dto.getIsbn(), dto.getCoverImg(),
                dto.getPlot(), dto.getPublicationDate(),dto.getCategory());

        return id;
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book =findOne(id);

        bookRepository.deleteBook(book);
    }

    public List<Book> findBooks() {
        return bookRepository.findAll();
    }

    public Book findOne(Long id) {
        return bookRepository.findOne(id).orElseThrow(()->
                new IllegalArgumentException("해당 책이 없습니다. id"+id));
    }






}
