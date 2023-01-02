package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")  // Member와 orders테이블은 일대다 양방향 관계이다. 그래서 JPA가 두 테이블 모두에서 FK에 접근을 할 수 있기 떄문에 어떤 것을 기준으로 할지 정해주어야 한다.(주인) 이 경우 FK가 있는 곳이 주인이 될것임(orders가 주인). 주인이 아닌 곳에  mappedby라는 속성을 주어서 나는 orders의 member에 의해서 매핑된다는 것을 설정해주는 것
    private List<Order> orders = new ArrayList<>();
}
