package jpabook.archiveB.controller;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter @Setter
public class MemberCreateForm {

    @Email(message = "이메일 형식으로 입력해주세요.")
    @NotEmpty(message = "email을 입력하세요.")
    private String email;

    @Size(min=8,max=16)
    @NotEmpty(message = "password를 설정하세요.")
    private String password;

    private String passwordCheck;

    private String name;

}
