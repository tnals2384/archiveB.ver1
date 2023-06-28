package jpabook.archiveB.domain;


import jpabook.archiveB.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
public class Book extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String coverImg; //책 표지
    private String plot;

    private String publisher;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;

    @Enumerated(EnumType.STRING)
    private Category category;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Book(String title, String author, String isbn, String coverImg,
                String plot, String publisher,LocalDate publicationDate, Category category) {
        this.title=title;
        this.author =author;
        this.isbn=isbn;
        this.coverImg = coverImg;
        this.plot=plot;
        this.publicationDate=publicationDate;
        this.publisher=publisher;
        this.category= category;
    }

    protected Book() {

    }


    public void updateBook(String title, String author, String isbn,
                      String plot, String publisher,LocalDate publicationDate, Category category) {
        this.title=title;
        this.author = author;
        this.isbn = isbn;
        this.plot = plot;
        this.publicationDate = publicationDate;
        this.publisher=publisher;
        this.category = category;
    }

    public void updateCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

}
