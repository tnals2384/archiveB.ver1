package jpabook.archiveB.web.dto;


import jpabook.archiveB.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequestDto {
    private Long id;

    @Builder
    public MemberRequestDto(Member member) {
        this.id=member.getId();
    }
}
