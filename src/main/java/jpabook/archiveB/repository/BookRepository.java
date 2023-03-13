package jpabook.archiveB.repository;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.archiveB.domain.Book;
import jpabook.archiveB.domain.QBook;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static jpabook.archiveB.domain.QBook.book;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class BookRepository {
    private final EntityManager em;

    public void save(Book book) { //책정보저장
        em.persist(book);
    }

    public Optional<Book> findOne(Long id) {
        Book book=em.find(Book.class,id);
        return Optional.ofNullable(book);
    }

    public List<Book> findAll() {
        return em.createQuery("select b from Book b",Book.class)
                .getResultList();
    }

    //페이징
    public List<Book> findByPage(int offset, int limit) {
        return em.createQuery("select b from Book b order by b.publicationDate desc")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public void deleteBook(Book book) {
        em.remove(book);
    }

    public final JPAQueryFactory queryFactory;

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








}
