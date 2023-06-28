package jpabook.archiveB.web.dto.book;


import jpabook.archiveB.domain.Book;
import jpabook.archiveB.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.Date;

@Setter
@NoArgsConstructor
@Getter
public class BookSaveRequestDto {
    private String title;
    private String author;
    private String isbn;
    private MultipartFile coverImg;
    private String plot;
    private String publisher;

    @Enumerated(EnumType.STRING)
    private Category category;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;

    @Builder
    public BookSaveRequestDto(String title, String author, String isbn, MultipartFile coverImg,
                              String plot, String publisher,LocalDate publicationDate, Category category) {
        this.title=title;
        this.author=author;
        this.isbn =isbn;
        this.coverImg=coverImg;
        this.plot=plot;
        this.publisher=publisher;
        this.publicationDate=publicationDate;
        this.category=category;
    }


    public Book toEntity(String filePath) {
        return Book.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .coverImg(filePath)
                .plot(plot)
                .publisher(publisher)
                .publicationDate(publicationDate)
                .category(category)
                .build();
    }

}
