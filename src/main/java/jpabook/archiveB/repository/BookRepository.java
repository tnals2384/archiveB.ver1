package jpabook.archiveB.repository;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.archiveB.domain.Book;
import jpabook.archiveB.domain.Member;
import jpabook.archiveB.web.dto.book.BookSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static jpabook.archiveB.domain.QBook.book;

@Repository
@RequiredArgsConstructor
public class BookRepository {
    private final EntityManager em;
    public final JPAQueryFactory queryFactory;

    public void save(Book book) { //책정보저장
        em.persist(book);
    }

    public Optional<Book> findOne(Long id) {
        Book book=em.find(Book.class,id);
        return Optional.ofNullable(book);
    }

    public List<Book> findAll() {
        return em.createQuery("select u from Book u",Book.class)
                .getResultList();
    }

    public void deleteBook(Book book) {
        em.remove(book);
    }


    public List<Book> Search(BookSearch bookSearch) {

        return queryFactory.select(book)
                .from(book)
                .where(titleCt(bookSearch.getSearchWord()).or(
                        authorCt(bookSearch.getSearchWord())))
                .orderBy(book.publicationDate.desc())
                .fetch();
    }

    BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (Exception e) {
            return new BooleanBuilder();
        }
    }


    private BooleanBuilder titleCt(String titleCond) {
        return nullSafeBuilder(()-> book.title.contains(titleCond));
    }

    private BooleanBuilder authorCt(String authorCond) {
        return nullSafeBuilder(()-> book.author.contains(authorCond));
    }



    //페이징
    public List<Book> findBooksByPage(int offset, int limit) {
        List<Book> result = queryFactory
                .selectFrom(book)
                .orderBy(book.createdAt.desc())
                .offset(offset*limit)
                .limit(limit)
                .fetch();

        return result;
    }




}
