package jpabook.archiveB.repository;

import jpabook.archiveB.domain.Comment;
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

    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }
    public void deletePost (Post post) {
        em.remove(post);
    }
}
