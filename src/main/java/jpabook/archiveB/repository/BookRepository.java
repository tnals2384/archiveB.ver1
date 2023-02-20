package jpabook.archiveB.repository;

import jpabook.archiveB.domain.Book;
import jpabook.archiveB.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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

    public void deleteBook(Book book) {
        em.remove(book);
    }





}
