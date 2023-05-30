package jpabook.archiveB.web.dto;

import jpabook.archiveB.domain.Member;
import jpabook.archiveB.domain.Post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class PostSaveRequestDto {

    private String title;
    private String content;

    private Date startDate;

    private Date endDate;

    @Builder
    public PostSaveRequestDto(String title, String content) {
        this.title=title;
        this.content=content;
    }


    public Post toEntity(Member member) {
        return Post.builder().member(member)
                .title(title)
                .content(content)
                .build();
    }
}
