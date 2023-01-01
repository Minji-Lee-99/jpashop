package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext // entitymanager를 생성해서 할당해준다. (원래는 따로 설ㅈ정해서 생성해야 하지만 스프링 부트가 해줌)
        private EntityManager em;

        public Long save(Member member) {
            em.persist(member);
            return member.getId();
        }

        public Member find(Long id) {
            return em.find(Member.class, id);
    }




}
