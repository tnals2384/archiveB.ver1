package jpabook.archiveB.web.dto;

import jpabook.archiveB.domain.Member;
import jpabook.archiveB.domain.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private Member member;
    private LocalDateTime postDate;

    public PostResponseDto(Post entity) {
        this.id=entity.getId();
        this.title=entity.getTitle();
        this.content=entity.getContent();
        this.member=entity.getMember();
        this.postDate=entity.getPostDate();
    }
}
