package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

//    public MemberService(MemberRepository memberRepository) {
//
//    }

//    @Autowired
//    MemberRepository memberRepository;
    // 필드 선언 일종의 di
    // 원래는 생성자 주입을 썼었다


    // 회원 생성
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> members = memberRepository.findByName(member.getName());
        if(!members.isEmpty()) {
            throw new IllegalStateException("member already exist");
        }
    }
    // 회원 전체 조회

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    // 개인 회원 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }



}
