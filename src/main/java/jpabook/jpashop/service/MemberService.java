package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
// 읽기만 하는 메소드의 경우는 readonly속성을 true로 주면 jpa가 내부적으로 최적화를 해준다. (데이터를 변경하는 곳에는 해당 속성을 주면 변경이 안된다. default는 false)
@Transactional(readOnly = true)  // jpa에서 데이터 변경은 항상 transaction안에서 이루어져야 한다. javax꺼가 있고, spring꺼가 있는데, spring꺼 사용하기!!!!!!!
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional // class레벨에서 readonly=true로 설정했기 때문에 데이터 변경하는 곳에서는 따로 설정해주기!
    public Long join(Member member) {
        validateDuplicateMember(member);  // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();  // db에 들어간 후가 아니더라도 jpa에 의해서 값이 미리 할당이 됨
    }

    // 회원 가입 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName()); // 이렇게 검증을 해줘도 동시에 가입할 수도 있기 때문에, 최후의 안전수단으로 DB상에서 유니크 조건을 주는 것이 좋다.!!
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 단건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
