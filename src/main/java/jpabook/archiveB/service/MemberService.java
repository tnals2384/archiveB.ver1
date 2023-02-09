package jpabook.archiveB.service;


import jpabook.archiveB.domain.Member;
import jpabook.archiveB.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(String name, String email, String password) {
        Member member = Member.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        memberRepository.save(member);

        return member.getId();
    }

  /*  private void validateDuplicateUser(Member member) {
        List<Member> findUsers = memberRepository.findOne(member.getId());
        if(!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }*/

    //전체 회원 조회
    public List<Member> findUsers() {
        return memberRepository.findAll();
    }

    //회원 아이디로 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
