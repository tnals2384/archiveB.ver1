package jpabook.archiveB.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.archiveB.domain.Book;
import jpabook.archiveB.domain.Comment;
import jpabook.archiveB.domain.QComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static jpabook.archiveB.domain.QComment.comment1;
import static jpabook.archiveB.domain.QPost.post;

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

    //책 comment
    public List<Comment> findCommentsByPage(Long bookId,int offset, int limit) {
        QComment comment = comment1;

        List<Comment> result = queryFactory
                .selectFrom(comment)
                .where(comment.book.id.eq(bookId))
                .orderBy(comment.createdAt.desc())
                .offset(offset*limit)
                .limit(limit)
                .fetch();

        return result;
    }

    public int commentsCount(Long bookId) {
        QComment comment =comment1;

        return queryFactory.selectFrom(comment)
                .where(comment.book.id.eq(bookId))
                .fetch().size();
    }



    //나의 comment
    public List<Comment> findMyCommentsByPage(Long memberId,int offset, int limit) {
        QComment comment = comment1;

        List<Comment> result = queryFactory
                .selectFrom(comment)
                .where(comment.member.id.eq(memberId))
                .orderBy(comment.createdAt.desc())
                .offset(offset*limit)
                .limit(limit)
                .fetch();

        return result;
    }




}
