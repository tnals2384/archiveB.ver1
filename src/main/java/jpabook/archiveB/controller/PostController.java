package jpabook.archiveB.controller;


import jpabook.archiveB.service.MemberService;
import jpabook.archiveB.service.PostService;
import jpabook.archiveB.web.dto.PostResponseDto;
import jpabook.archiveB.web.dto.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/posts/list")
    public String PostList (Model model, Principal principal) {
        Long memberId= memberService.getUser(principal.getName()).getId();
        model.addAttribute("posts",postService.findPosts(memberId));
        return "posts/postList";
    }

    
    
    //로그인한 사용자만 게시글을 볼 수 있도록 예외처리 해야함
    @PreAuthorize("isAuthenticated()") 
    @GetMapping("/posts/{postId}")
    public String PostDetail(@PathVariable("postId") Long postId, Model model,Principal principal) {
        PostResponseDto postDto = postService.findById(postId);

        if(postDto.getMember()!=memberService.getUser(principal.getName())) {
            return "redirect:/posts/list";
        }

        model.addAttribute("post",postDto);
        return "posts/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/posts/write")
    public String writePost(Model model) {
        model.addAttribute("postSaveRequestDto", new PostSaveRequestDto());
        return "posts/save";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/posts/write")
    public String writePost(@Valid PostSaveRequestDto postSaveRequestDto, Principal principal) {
       Long memberId= memberService.getUser(principal.getName()).getId();

       Long postId = postService.save(postSaveRequestDto, memberId);

       return "redirect:/posts/list";
    }
}
