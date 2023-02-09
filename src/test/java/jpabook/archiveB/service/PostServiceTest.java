package jpabook.archiveB.service;

import jpabook.archiveB.domain.Post;

import jpabook.archiveB.repository.PostRepository;
import jpabook.archiveB.web.dto.PostSaveRequestDto;
import jpabook.archiveB.web.dto.PostUpdateRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
class PostServiceTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

/*    @Test
    public void 게시글등록() throws Exception{
        //given
        User user =createUser();
        String title= "title";
        String content="content";
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        UserRequestDto userRequestDto= UserRequestDto.builder().user(user).build();
        Long postId= postService.save(requestDto,userRequestDto);
        //then
        Post getPost = postRepository.findOne(postId).get();
        assertEquals("title",getPost.getTitle());
        assertEquals("content", getPost.getContent());
        assertEquals("hi",getPost.getUser().getName());
    }

    @Test
    public void 게시글수정() throws Exception{
        //given
        User user =createUser();
        String title= "title";
        String content="content";
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        UserRequestDto userRequestDto= UserRequestDto.builder().user(user).build();
        Long postId= postService.save(requestDto,userRequestDto);

        PostUpdateRequestDto updateDto = PostUpdateRequestDto.builder()
                        .title("수정제목").content("수정내용").build();

        Long updateId= postService.update(postId,updateDto);

        Post getUpdatePost = postRepository.findOne(updateId).get();
        assertEquals("수정제목",getUpdatePost.getTitle());
        assertEquals("수정내용", getUpdatePost.getContent());
    }

    @Test
    public void 게시글삭제() throws  Exception {
        //given
        User user =createUser();
        String title= "title";
        String content="content";
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        UserRequestDto userRequestDto= UserRequestDto.builder().user(user).build();
        Long postId= postService.save(requestDto,userRequestDto);

        Post getPost= postRepository.findOne(postId).get();
        postService.delete(getPost.getId());

        assertEquals(0,postRepository.findAll().size());
    }

    private User createUser() {
        User user = new User();
        user.setName("hi");

        em.persist(user);
        return user;
    }*/

}