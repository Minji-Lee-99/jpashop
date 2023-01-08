package jpabook.jpashop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.QOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
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
// 권장하는 방법: query dsl
// 아래 방법은 Criteria
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }

    public List<Order> findAllQueryDsl(OrderSearch orderSearch) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QOrder o = QOrder.order;

        BooleanBuilder builder = new BooleanBuilder();


        if (orderSearch.getOrderStatus() != null) {
            builder.and(o.status.eq(orderSearch.getOrderStatus()));
        }

        if (orderSearch.getMemberName() != null && !orderSearch.getMemberName().trim().isEmpty()) {
            builder.and(o.member.name.eq(orderSearch.getMemberName()));
        }

        List<Order> orders = queryFactory.selectFrom(o)
                .where(builder)
                .fetch();
        return orders;
    }
}
