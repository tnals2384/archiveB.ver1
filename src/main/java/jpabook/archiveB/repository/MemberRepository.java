package jpabook.archiveB.repository;

import jpabook.archiveB.domain.Comment;
import jpabook.archiveB.domain.Member;

import jpabook.archiveB.domain.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) { //id로 찾기
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select u from Member u",Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) { //이름으로 찾기
        return em.createQuery("select u from Member u where u.name= :name",
                Member.class).setParameter("name", name).getResultList();
    }

    public Optional<Member> findByEmail(String email) { //email으로 찾기
       Member member = em.createQuery("select u from Member u where u.email= :email",
               Member.class).setParameter("email", email).getSingleResult();
        return Optional.ofNullable(member);
    }

    public void deleteUser(Member member) {
        em.remove(member);
    }

}
