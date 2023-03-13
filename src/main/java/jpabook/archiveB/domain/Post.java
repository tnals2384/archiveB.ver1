package jpabook.archiveB.domain;

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
public class Post {

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime postDate;

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    @Builder
    public Post(Member member, String title, String content,LocalDateTime postDate) {
        this.member=member;
        this.title=title;
        this.content=content;
        this.postDate=postDate;
    }

    public void update(String title,String content) {
        this.title= title;
        this.content=content;
    }
}
