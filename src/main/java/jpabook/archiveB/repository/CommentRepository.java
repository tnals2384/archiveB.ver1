package jpabook.archiveB.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.archiveB.domain.Comment;
import jpabook.archiveB.domain.QComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static jpabook.archiveB.domain.QComment.comment1;
@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final EntityManager em;
    public final JPAQueryFactory queryFactory;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Optional<Comment> findOne(Long id) {
        Comment comment= em.find(Comment.class, id);
        return Optional.ofNullable(comment);
    }


    //책의 Comment
    public List<Comment> findBookComments(Long bookId) {
        return em.createQuery("select c from Comment c where c.book.id= :bookId", Comment.class)
                .setParameter("bookId",bookId).getResultList();
    }

    //나의 Comment
    public List<Comment> findMyComment(Long memberId) {
        return em.createQuery("select c from Comment c where c.member.id= :memberId", Comment.class)
                .setParameter("memberId",memberId).getResultList();
    }


    public void deleteComment(Comment comment) {
        em.remove(comment);
    }

    public double getAverageRatingByBookId(Long bookId) {
        QComment comment = comment1;

        Double averageRating = queryFactory
                .select(comment.star.avg())
                .from(comment)
                .where(comment.book.id.eq(bookId))
                .fetchOne();

        return averageRating != null ? averageRating : 0.0;
    }

    //페이징
    public List<Comment> findCommentsByPage(int offset, int limit) {
        QComment comment = comment1;

        List<Comment> result = queryFactory
                .selectFrom(comment)
                .orderBy(comment.createdAt.desc())
                .offset(offset)
                .limit(limit)
                .fetch();

        return result;
    }




}
