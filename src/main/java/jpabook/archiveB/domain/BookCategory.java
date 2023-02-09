package jpabook.archiveB.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class BookCategory {

    @Id @GeneratedValue
    @Column(name = "book_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

   public void setBook(Book book) {
       this.book =book;
       book.getCategories().add(this);
   }



}
