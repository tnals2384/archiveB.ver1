package jpabook.archiveB.controller;

import jpabook.archiveB.service.MemberService;
import jpabook.archiveB.web.dto.MemberCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;


    /*
    ** 회원 가입
     */
    @GetMapping("/members/signup")
    public String signup(Model model) {
        model.addAttribute("memberCreateForm", new MemberCreateForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/signup")
    public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "members/createMemberForm";
        }

        //비밀번호 길이 검증
        if(memberCreateForm.getPassword().length() <8 ||
                memberCreateForm.getPassword().length()>16) {
            bindingResult.addError(new FieldError("memberCreateForm","password",
                    "비밀번호는 8자 이상 16자 이하여야합니다."));

            return "members/createMemberForm";
        }


        //비밀번호확인이 비밀번호와 일치한지 검증
        if(!memberCreateForm.getPassword().equals(memberCreateForm.getPasswordCheck())) {
            bindingResult.addError(new FieldError("memberCreateForm",
                    "passwordCheck","비밀번호가 일치하지 않습니다."));
            return "members/createMemberForm";
        }

        //회원가입 수행
        //이미 존재하는 email이면 이미 존재하는 회원 에러
        try {
            memberService.join(memberCreateForm.getName(), memberCreateForm.getEmail(), memberCreateForm.getPassword());
        } catch (DataIntegrityViolationException e) {
            bindingResult.addError((new FieldError("memberCreateForm", "email","이미 존재하는 회원입니다.")));
            return "members/createMemberForm";
        }
        return "redirect:/";
    }


    /*
    ** 로그인(스프링 시큐리티)
     */
    @GetMapping("/members/login")
    public String login() {
        return "members/loginForm";
    }
}
