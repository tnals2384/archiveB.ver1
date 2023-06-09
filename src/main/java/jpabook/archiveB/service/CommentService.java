package jpabook.archiveB.service;

import jpabook.archiveB.domain.Book;
import jpabook.archiveB.domain.Comment;
import jpabook.archiveB.domain.Member;
import jpabook.archiveB.domain.Post;
import jpabook.archiveB.repository.BookRepository;
import jpabook.archiveB.repository.CommentRepository;
import jpabook.archiveB.repository.MemberRepository;

import jpabook.archiveB.web.dto.CommentRequestDto;
import jpabook.archiveB.web.dto.CommentResponseDto;
import jpabook.archiveB.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;


    public List<CommentResponseDto> findAllbyBookId(Long bookId,int pageNo) {
        //Comment stream을 map을 통해 CommentResponseDto로 변환하여 list로
        return commentRepository.findCommentsByPage(bookId,pageNo,10).stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

    public List<CommentResponseDto> findAllbyMemberId(Long memberId,int pageNo) {
        //Comment stream을 map을 통해 CommentResponseDto로 변환하여 list로 반환
        return commentRepository.findMyCommentsByPage(memberId,pageNo,10).stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

    public CommentResponseDto findById(Long id) {
        Comment entity = commentRepository.findOne(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 댓글이 없습니다. id="+id));

        return new CommentResponseDto(entity);
    }


    //코멘트 작성
    @Transactional
    public Long writeComment(Long userId, Long bookId, CommentRequestDto requestDto) {
        Member member = memberRepository.findOne(userId);
        Book book = bookRepository.findOne(bookId).orElseThrow(()
                -> new IllegalArgumentException("해당 책이 없습니다. id"+userId));

        Comment comment = Comment.builder().member(member)
                .book(book)
                .star(requestDto.getStar())
                .comment(requestDto.getContent()).build();

        commentRepository.save(comment);
        return comment.getId();
    }

    //코멘트 수정
    @Transactional
    public void updateComment(Long commentId,int star, String text ) {
        Comment findComment = commentRepository.findOne(commentId)
                .orElseThrow(()->new IllegalArgumentException("해당 comment가 없습니다. id="+commentId));
        findComment.change(star,text);
    }


    //코멘트 삭제
    @Transactional
    public void deleteComment(Long commentId) {

        Comment comment= commentRepository.findOne(commentId).orElseThrow(
                ()->new IllegalArgumentException("해당 댓글이 없습니다. id"+commentId)
        );
        commentRepository.deleteComment(comment);
    }



}
