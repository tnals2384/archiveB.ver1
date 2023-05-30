package jpabook.archiveB.domain;

import jpabook.archiveB.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> posts= new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    private String image;

    @Builder
    public Member(String name, String email, String password,String image,Role role) {
        this.name=name;
        this.email= email;
        this.password=password;
        this.image =image;
        this.role=role;
    }

    public Member update(String name, String image) {
        this.name= name;
        this.image = image;

        return this;
    }



}
