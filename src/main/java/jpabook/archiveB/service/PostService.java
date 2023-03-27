package jpabook.archiveB.service;

import jpabook.archiveB.base.BaseException;
import jpabook.archiveB.base.BaseResponseStatus;
import jpabook.archiveB.domain.Member;
import jpabook.archiveB.domain.Post;
import jpabook.archiveB.repository.MemberRepository;
import jpabook.archiveB.repository.PostRepository;

import jpabook.archiveB.web.dto.MemberRequestDto;
import jpabook.archiveB.web.dto.PostResponseDto;
import jpabook.archiveB.web.dto.PostSaveRequestDto;
import jpabook.archiveB.web.dto.PostUpdateRequestDto;
import jpabook.archiveB.web.dto.book.BookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public Long save(PostSaveRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findOne(memberId);
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


    //post list를 불러온다
    public List<PostResponseDto> findPosts(Long memberId) {
        //Post stream을 map을 통해 PostResponseDto로 변환하여 list로 반환
        return postRepository.findAll(memberId).stream().map(PostResponseDto::new).collect(Collectors.toList());
    }


    @Transactional
    public Long delete(Long id) throws BaseException {
        Post post = postRepository.findOne(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시물이 없습니다. id=" +id)
        );

        postRepository.deletePost(post);

        return id;
    }

}
