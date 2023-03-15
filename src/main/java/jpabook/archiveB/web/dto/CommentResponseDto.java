package jpabook.archiveB.web.dto;

import jpabook.archiveB.domain.Book;
import jpabook.archiveB.domain.Comment;
import jpabook.archiveB.domain.Member;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private Member member;
    private Book book;
    private String comment;
    private int star;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date; //코멘트 작성 날짜


    public CommentResponseDto(Comment entity) {
        this.id =entity.getId();
        this.book=entity.getBook();
        this.member=entity.getMember();
        this.comment=entity.getComment();
        this.star=entity.getStar();
        this.date=entity.getDate();
    }
}
