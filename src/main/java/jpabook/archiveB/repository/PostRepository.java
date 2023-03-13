package jpabook.archiveB.repository;

import jpabook.archiveB.domain.Comment;
import jpabook.archiveB.domain.Member;
import jpabook.archiveB.domain.Post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public Optional<Post> findOne(Long id) {
        Post post= em.find(Post.class, id);
        return Optional.ofNullable(post);
    }

    public List<Post> findAll(Long memberId) {
        return em.createQuery("select p from Post p where p.member.id= :memberId", Post.class)
                .setParameter("memberId",memberId).getResultList();


    }
    public void deletePost (Post post) {
        em.remove(post);
    }
}
