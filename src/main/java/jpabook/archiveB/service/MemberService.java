package jpabook.archiveB.service;


import jpabook.archiveB.exception.DataNotFoundException;
import jpabook.archiveB.domain.Member;
import jpabook.archiveB.domain.Role;
import jpabook.archiveB.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(String name, String email, String password,Role role) {
        Member member = Member.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();

        memberRepository.save(member);

        return member.getId();
    }

    //전체 회원 조회
    public List<Member> findUsers() {
        return memberRepository.findAll();
    }

    //회원 아이디로 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }


    public Member getUser(String email) {
        Optional<Member> member = this.memberRepository.findByEmail(email);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("member not found");
        }
    }
}
