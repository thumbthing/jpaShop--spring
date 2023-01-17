package com.login.service;

import com.login.domain.Member;
import com.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member memeber) {
        memberRepository.save(memeber);
        return memeber.getId();
    }

    public Member findOne(String membername) {
        return memberRepository.findOne(membername);
    }

}
