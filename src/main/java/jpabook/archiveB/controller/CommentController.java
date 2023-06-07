package jpabook.archiveB.controller;


import jpabook.archiveB.service.CommentService;
import jpabook.archiveB.service.MemberService;
import jpabook.archiveB.web.dto.CommentRequestDto;
import jpabook.archiveB.web.dto.CommentResponseDto;
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
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/comments/list")
    public String CommentList(Model model,Principal principal) {
        Long memberId = memberService.getUser(principal.getName()).getId();
        model.addAttribute("comments",commentService.findAllbyMemberId(memberId));

        return "comments/commentList";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comments/{bookId}/add")
    public String CommentSave(@PathVariable Long bookId, @Valid CommentRequestDto requestDto
    ,Principal principal) {
        Long memberId = memberService.getUser(principal.getName()).getId();
        commentService.writeComment(memberId,bookId,requestDto);
        return "redirect:/books/{bookId}";

    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/comments/{commentId}/delete")
    public String commentDelete(@PathVariable Long commentId, Principal principal) {
        CommentResponseDto commentDto = commentService.findById(commentId);

        if (commentDto.getMember() != memberService.getUser(principal.getName())) {
            return "redirect:/books/" + commentDto.getBook().getId();
        }

        commentService.deleteComment(commentId);
        return "redirect:/books/" + commentDto.getBook().getId();
    }
}
