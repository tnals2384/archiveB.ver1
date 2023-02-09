package jpabook.archiveB.domain;


import lombok.Getter;
import lombok.Setter;

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
    private Date publicationDate;

    private double starAvg;  //평균별점 ..어카죵?

    @OneToMany(mappedBy = "book")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy ="book")
    private List<BookCategory> categories = new ArrayList<>();


}
