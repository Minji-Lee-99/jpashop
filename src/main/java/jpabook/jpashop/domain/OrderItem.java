package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;


    //    생성 메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); // 재고 반영
        return orderItem;
    };

//    비지니스 로직
//    주문 취소(개수 원상복귀)
    public void cancel() {
        getItem().addStock(count);  // 원래는 이렇게 값이 변경되면, update sql을 날려야 하지만, jpa는 이렇게 값만 변경해도 알아서 update 쿼리를 날려준다.

    }

//    조회 로직
//    특정 상품의 총 가격(특정 상품의 가격 * 개수)
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
