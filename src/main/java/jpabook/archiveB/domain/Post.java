package jpabook.archiveB.domain;

import jpabook.archiveB.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Date startDate;
    private Date endDate;


    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    @Builder
    public Post(Member member, String title, String content) {
        this.member=member;
        this.title=title;
        this.content=content;
    }

    public void update(String title,String content) {
        this.title= title;
        this.content=content;
    }
}
