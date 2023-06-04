package jpabook.archiveB.service;

import jpabook.archiveB.base.BaseException;
import jpabook.archiveB.base.BaseResponseStatus;
import jpabook.archiveB.base.FileStore;
import jpabook.archiveB.domain.Book;
import jpabook.archiveB.repository.BookRepository;
import jpabook.archiveB.web.dto.book.BookSearch;
import jpabook.archiveB.repository.CommentRepository;
import jpabook.archiveB.web.dto.book.BookResponseDto;
import jpabook.archiveB.web.dto.book.BookSaveRequestDto;
import jpabook.archiveB.web.dto.book.BookUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final FileStore fileStore;
    //id로 책 찾기
    public BookResponseDto findById(Long id) throws BaseException{
        Book entity = bookRepository.findOne(id)
                .orElseThrow(()-> new BaseException(BaseResponseStatus.POSTS_EMPTY_POST_ID));

        return new BookResponseDto(entity);
    }

    //book list를 불러오기 위한 findBooks
    public List<BookResponseDto> findBooks() throws BaseException {
        //Book stream을 map을 통해 BookListResponseDto로 변환하여 list로 반환
        try {
            List<BookResponseDto> books = bookRepository.findAll().stream().map(BookResponseDto::new).collect(Collectors.toList());
            return books;
        }
        catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }


    //책 정보 저장
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public Long saveBook(BookSaveRequestDto bookDto) throws IOException {
        String filePath = fileStore.saveFile(bookDto.getCoverImg());
        Book book = bookDto.toEntity(filePath);
        bookRepository.save(book);
        return book.getId();
    }

    //책 정보 update
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public Long updateBook(Long id, BookUpdateRequestDto dto) throws IOException{
        Book book = bookRepository.findOne(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 책이 없습니다. id="+id));

        if(!dto.getCoverImg().isEmpty()) {
            fileStore.deleteFile(book.getCoverImg());
            String filePath = fileStore.saveFile(dto.getCoverImg());
            book.updateCoverImg(filePath);
        }

        book.updateBook(dto.getTitle(), dto.getAuthor(),
                dto.getIsbn(),
                dto.getPlot(), dto.getPublicationDate(),dto.getCategory());

        return id;
    }


    //책 정보 삭제
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteBook(Long id) throws IOException {
        Book book =bookRepository.findOne(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 책이 없습니다. id="+id));

        bookRepository.deleteBook(book);
        fileStore.deleteFile(book.getCoverImg());
    }




    public List<BookResponseDto> searchBook(BookSearch bookSearch) {
        return bookRepository.Search(bookSearch).stream().map(BookResponseDto::new).collect(Collectors.toList());

    }

}
