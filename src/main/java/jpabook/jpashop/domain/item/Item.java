package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

//    비지니스 로직
//    재고 수량 늘리기
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

//    재고 수량 줄이기
    public void removeStock(int quantity) {
        int restStack = this.stockQuantity - quantity;
        if (restStack < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStack;
    }

}
