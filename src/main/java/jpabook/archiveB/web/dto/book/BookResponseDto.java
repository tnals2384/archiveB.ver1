package jpabook.archiveB.web.dto.book;

import jpabook.archiveB.domain.Book;
import jpabook.archiveB.domain.Category;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;


//book list를 반환하는 dto
@Getter
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private String coverImg;
    private Category category;
    private String plot;
    private String publisher;
    private String isbn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;

    private Double starAvg;


    public BookResponseDto(Book entity) {
        this.id= entity.getId();
        this.title=entity.getTitle();
        this.author=entity.getAuthor();
        this.coverImg=entity.getCoverImg();
        this.category=entity.getCategory();
        this.plot=entity.getPlot();
        this.publisher=entity.getPublisher();
        this.isbn=entity.getIsbn();
        this.publicationDate=entity.getPublicationDate();
    }


    public void setStarAvg(Double starAvg) {
        this.starAvg = starAvg;
    }
}
