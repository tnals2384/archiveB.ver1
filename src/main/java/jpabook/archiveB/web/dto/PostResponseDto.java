package jpabook.archiveB.web.dto;

import jpabook.archiveB.base.BaseEntity;
import jpabook.archiveB.domain.Member;
import jpabook.archiveB.domain.Post;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto  {

    private Long id;
    private String title;
    private String content;
    private Member member;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime postDate;

    public PostResponseDto(Post entity) {
        this.id=entity.getId();
        this.title=entity.getTitle();
        this.content=entity.getContent();
        this.member=entity.getMember();
        this.postDate=entity.getCreatedAt();
    }
}
