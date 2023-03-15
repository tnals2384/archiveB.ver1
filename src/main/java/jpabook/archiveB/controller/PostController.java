package jpabook.archiveB.controller;


import jpabook.archiveB.base.BaseException;
import jpabook.archiveB.service.MemberService;
import jpabook.archiveB.service.PostService;
import jpabook.archiveB.web.dto.PostResponseDto;
import jpabook.archiveB.web.dto.PostSaveRequestDto;
import jpabook.archiveB.web.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
    public String postsSave(Model model) {
        model.addAttribute("postSaveRequestDto", new PostSaveRequestDto());
        return "posts/save";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/posts/write")
    public String postsSave(@Valid PostSaveRequestDto postSaveRequestDto, Principal principal) {
       Long memberId= memberService.getUser(principal.getName()).getId();
       Long postId =postService.save(postSaveRequestDto, memberId);
       return "redirect:/posts/" + postId;
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/posts/{postId}/edit")
    public String postsUpdate(@PathVariable Long postId, Model model,Principal principal) {

        PostResponseDto postDto = postService.findById(postId);
        if(postDto.getMember()!=memberService.getUser(principal.getName())) {
            return "redirect:/posts/list";
        }
        PostUpdateRequestDto dto = PostUpdateRequestDto.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
        model.addAttribute("post",dto);
        return "posts/update";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/posts/{postId}/edit")
    public String postsUpdate(@PathVariable Long postId, @Valid PostUpdateRequestDto updateDto,
                              Principal principal) {
        Long id = postService.update(postId, updateDto);
        return "redirect:/posts/" + id;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/posts/{postId}/delete")
    public String postsDelete(@PathVariable Long postId,Principal principal) {
        try {
            postService.delete(postId, principal);
            return "redirect:/posts/list";
        }
        catch (BaseException e) {
            //error mesage
            return "redirect:/posts/list";
        }
    }
}
