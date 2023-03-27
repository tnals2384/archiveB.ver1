package jpabook.archiveB.service;

import jpabook.archiveB.domain.Book;
import jpabook.archiveB.domain.Comment;

import jpabook.archiveB.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class CommentServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

    public void 코멘트조회() throws Exception {

    }

/*
    @Test
    public void 코멘트작성() throws Exception {
        //given
        User user =createUser();
        Book book = createBook("hello","jay");
        int star = 4;
        String text = "너무 재미있어요";

        Long commentId = commentService.writeComment(user.getId(),book.getId(),star,text);

        //then
        Comment getComment = commentRepository.findOne(commentId);

        assertEquals("hi",getComment.getUser().getName());
        assertEquals("너무 재미있어요", getComment.getComment());
        assertEquals(4,getComment.getStar());
    }

    @Test
    public void 코멘트삭제() throws Exception {
        User user =createUser();
        Book book = createBook("hello","jay");
        int star = 4;
        String text = "너무 재미있어요";

        Long commentId = commentService.writeComment(user.getId(),book.getId(),star,text);
        Comment comment = commentRepository.findOne(commentId);
        commentService.deleteComment(comment);

        assertEquals(commentRepository.findAll().size(),0);
    }

    @Test
    public void 코멘트수정() throws Exception {
        User user =createUser();
        Book book = createBook("hello","jay");
        int star = 4;
        String text = "너무 재미있어요";

        Long commentId = commentService.writeComment(user.getId(),book.getId(),star,text);
        Comment comment = commentRepository.findOne(commentId);
        commentService.updateComment(comment.getId(), 3,"재미없어요");
        assertEquals(commentRepository.findOne(comment.getId()).getComment(),"재미없어요");
        assertEquals(commentRepository.findOne(comment.getId()).getStar(),3);

    }
*/


}