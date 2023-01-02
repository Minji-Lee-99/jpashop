package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    private Address address;

    @Enumerated(EnumType.STRING)  // 반드시 스트링으로 사용하자 default는 ordinary인데 이렇게 설정하면 각 값들이 숫자로 들어간다. 이렇게 하면 중간에 새로운 값이 들어가게 되면 DB가 다 꼬이게 된다.
    private DeliveryStatus status;
}
