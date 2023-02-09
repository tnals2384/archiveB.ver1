package jpabook.archiveB.repository;

import jpabook.archiveB.domain.Book;
import jpabook.archiveB.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {
    private final EntityManager em;

    public void save(Book book) { //책정보저장
        em.persist(book);
    }

    public Book findOne(Long id) {
        return em.find(Book.class,id);
    }

    public List<Book> findAll() {
        return em.createQuery("select b from Book b",Book.class)
                .getResultList();
    }

    public void deleteBook(Book book) {
        em.remove(book);
    }





}
