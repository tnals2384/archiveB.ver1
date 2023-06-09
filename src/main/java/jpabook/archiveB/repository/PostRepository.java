package jpabook.archiveB.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.archiveB.domain.Post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static jpabook.archiveB.domain.QPost.post;

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


    public final JPAQueryFactory queryFactory;
    //페이징
    public List<Post> findPostsByPage(int offset, int limit) {
        List<Post> result = queryFactory
                .selectFrom(post)
                .orderBy(post.createdAt.desc())
                .offset(offset)
                .limit(limit)
                .fetch();

        return result;
    }
}

