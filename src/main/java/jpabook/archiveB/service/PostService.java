package jpabook.archiveB.service;

import jpabook.archiveB.domain.Member;
import jpabook.archiveB.domain.Post;
import jpabook.archiveB.repository.MemberRepository;
import jpabook.archiveB.repository.PostRepository;

import jpabook.archiveB.web.dto.MemberRequestDto;
import jpabook.archiveB.web.dto.PostResponseDto;
import jpabook.archiveB.web.dto.PostSaveRequestDto;
import jpabook.archiveB.web.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public Long save(PostSaveRequestDto requestDto, MemberRequestDto memberRequestDto) {
        Member member = memberRepository.findOne(memberRequestDto.getId());
        Post post = requestDto.toEntity(member);
        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public Long update(Long id, PostUpdateRequestDto requestDto) {

        Post post = postRepository.findOne(id).orElseThrow(()->
                new IllegalArgumentException("해당 게시물이 없습니다. id="+ id));

        post.update(requestDto.getTitle(),requestDto.getContent());

        return id;
    }

    public PostResponseDto findById(Long id) {
        Post entity = postRepository.findOne(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시물이 없습니다. id="+id));

        return new PostResponseDto(entity);
    }


    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findOne(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시물이 없습니다. id=" +id)
        );

        postRepository.deletePost(post);
    }

}
