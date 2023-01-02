package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;


    @Test
//    @Rollback(value = false) // 아예 db에 남겨서 보고싶을 때 사용
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);

        // then
//        em.flush(); // 기본적으로 test가 끝나면 rollback을 시키는데, 그렇게 되면 우리가 쿼리문도 확인을 할 수가 없다. 따라서, 쿼리문이 보고 싶다면 flush해주기!!! 쿼리문은 보이지만, db는 롤백
        assertEquals(member, memberRepository.findOne(savedId));  // 이것이 가능한 이유는 같은 영속성 컨텍스트에서는 여러개가 생성되지 않고 1개만 생성되기 때문
    }

    @Test()
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim1");

        Member member2 = new Member();
        member2.setName("kim1");

        // when, then
        // junit5에서의 exception test방법으로 첫번째 인자는 발생할 exception class, 두번째 인자는 실행할 로직
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member1);
            memberService.join(member2);
        });
    }
}