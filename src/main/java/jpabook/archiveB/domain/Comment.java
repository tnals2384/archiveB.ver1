package jpabook.archiveB.domain;

import jpabook.archiveB.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private String comment;
    private int star; //별점

    private LocalDateTime date; //코멘트 작성 날짜

    //==연관관계 메서드==//
    public void setMember(Member user) {
        this.member = member;
        member.getComments().add(this);
    }

    public void setBook(Book book) {
        this.book=book;
        book.getComments().add(this);
    }



    @Builder
    public Comment(Member member, Book book, int star, String comment) {
        this.member=member;
        this.book=book;
        this.star=star;
        this.comment=comment;
    }


    public void change(int star, String text) {
        this.star= star;
        this.comment=text;
    }

}
