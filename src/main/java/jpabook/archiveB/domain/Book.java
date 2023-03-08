package jpabook.archiveB.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
public class Book {

    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String coverImg; //책 표지
    private String plot;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publicationDate;

    @Enumerated(EnumType.STRING)
    private Category category;

    //private double starAvg;  //평균별점 ..어카죵?

    @OneToMany(mappedBy = "book")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Book(String title, String author, String isbn, String coverImg,
                String plot, Date publicationDate, Category category) {
        this.title=title;
        this.author =author;
        this.isbn=isbn;
        this.coverImg = coverImg;
        this.plot=plot;
        this.publicationDate=publicationDate;
        this.category= category;
    }

    protected Book() {

    }


    public void updateBook(String title, String author, String isbn,String coverImg,
                      String plot, Date publicationDate, Category category) {
        this.title=title;
        this.author =author;
        this.isbn=isbn;
        this.coverImg = coverImg;
        this.plot=plot;
        this.publicationDate=publicationDate;
        this.category=category;
    }


}
