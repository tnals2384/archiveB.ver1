package jpabook.archiveB.web.dto.book;


import jpabook.archiveB.domain.Book;
import jpabook.archiveB.domain.Category;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Getter
public class BookSaveRequestDto {
    //private Long id;
    private String title;
    private String author;
    private String isbn;
    private String coverImg;
    private String plot;

    @Enumerated(EnumType.STRING)
    private Category category;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publicationDate;

    @Builder
    public BookSaveRequestDto(String title, String author, String isbn, String coverImg,
                              String plot, Date publicationDate, Category category) {
        this.title=title;
        this.author=author;
        this.isbn =isbn;
        this.coverImg=coverImg;
        this.plot=plot;
        this.publicationDate=publicationDate;
        this.category=category;
    }


    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .coverImg(coverImg)
                .plot(plot)
                .publicationDate(publicationDate)
                .category(category)
                .build();
    }

}
