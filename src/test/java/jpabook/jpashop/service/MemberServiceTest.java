package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        assertThat(savedId).isEqualTo(member.getId());
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member memberA = new Member();
        memberA.setName("kim");

        Member memberB = new Member();
        memberB.setName("kim");

        //when
        memberService.join(memberA);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            memberService.join(memberB);
        });
    }
}