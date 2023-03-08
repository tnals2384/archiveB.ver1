package jpabook.archiveB.service;

import jpabook.archiveB.domain.Book;
import jpabook.archiveB.domain.Post;
import jpabook.archiveB.repository.BookRepository;
import jpabook.archiveB.repository.BookSearch;
import jpabook.archiveB.repository.CommentRepository;
import jpabook.archiveB.web.dto.PostResponseDto;
import jpabook.archiveB.web.dto.book.BookResponseDto;
import jpabook.archiveB.web.dto.book.BookSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    //id로 책 찾기
    public BookResponseDto findById(Long id) {
        Book entity = bookRepository.findOne(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 책이 없습니다. id="+id));

        return new BookResponseDto(entity);
    }

    //책 정보 저장
    @Transactional
    public Long saveBook(BookSaveRequestDto bookDto) {
        Book book = bookDto.toEntity();
        bookRepository.save(book);
        return book.getId();
    }

    //책 정보 update
    @Transactional
    public Long updateBook(Long id, BookSaveRequestDto dto) {
        Book book =bookRepository.findOne(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 책이 없습니다. id="+id));

        book.updateBook(dto.getTitle(), dto.getAuthor(),
                dto.getIsbn(), dto.getCoverImg(),
                dto.getPlot(), dto.getPublicationDate(),dto.getCategory());

        return id;
    }


    //책 정보 삭제
    @Transactional
    public void deleteBook(Long id) {
        Book book =bookRepository.findOne(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 책이 없습니다. id="+id));

        bookRepository.deleteBook(book);
    }



    //book list를 불러오기 위한 findBooks
    public List<BookResponseDto> findBooks() {
        //Book stream을 map을 통해 BookListResponseDto로 변환하여 list로 반환
        return bookRepository.findAll().stream().map(BookResponseDto::new).collect(Collectors.toList());

    }

    public List<BookResponseDto> searchBook(BookSearch bookSearch) {
        return bookRepository.Search(bookSearch).stream().map(BookResponseDto::new).collect(Collectors.toList());

    }




}
