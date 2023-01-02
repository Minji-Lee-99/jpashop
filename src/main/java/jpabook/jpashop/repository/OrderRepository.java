package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }


// 동적 쿼리
// 방법1: 값이 들어오는지 아닌지에 따라서 if문을 통해서 문자열을 +로 이어붙이는 것은 엄청 복잡하고 파라미터가 많아지면 거의 불가능에 가까움(버그도 많음, 발견도 어려움)
// 방법2: JPA Criteria(=> 공식적으로 제공하는 방식), 유지보수가 거의 불가능에 가깝다는 치명적인 단점
// query dsl
//    public List<Order> finalAll(OrderSearch orderSearch) {
//
//    }
}
