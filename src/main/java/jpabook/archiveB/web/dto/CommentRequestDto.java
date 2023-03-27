package jpabook.archiveB.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private Long id;
    private String content;

    @Builder
    public CommentRequestDto(Long id,String content) {
        this.id =id;
        this.content =content;
    }

}
