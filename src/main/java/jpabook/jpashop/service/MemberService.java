package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 읽기 전용 -> 성능을 최적화
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        //중복 이름 검증
        validateDuplicateMember(member);
        memberRepository.save(member);

        return member.getId();
    }

    // 멀티쓰레드 상황을 고려해서 설계할 필요가 있음
    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers =
                memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 한 건 조회
     */
    public Member findMember(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
