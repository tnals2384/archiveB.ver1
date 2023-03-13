package jpabook.archiveB.controller;

import jpabook.archiveB.domain.Member;
import jpabook.archiveB.service.MemberService;
import jpabook.archiveB.service.PostService;
import jpabook.archiveB.web.dto.PostResponseDto;
import jpabook.archiveB.web.dto.book.BookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/posts/list")
    public String PostList (Model model, Principal principal) {
        Long memberId= memberService.getUser(principal.getName());
        model.addAttribute("posts",postService.findPosts(memberId));
        return "posts/postList";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/posts/{postId}")
    public String PostDetail(@PathVariable("postId") Long postId, Model model) {
        PostResponseDto postDto = postService.findById(postId);
        model.addAttribute("post",postDto);
        return "posts/detail";
    }
}
