package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;


    // 값 타입의 경우 처음 생성한 이후에 변경이 되면 안되기 때문에 Getter만 제공하고 Setter는 제공하지 않는 것이 바람직하다.
    @Embeddable
    @Getter
    public class Address {
        private String city;
        private String street;
    private String zipcode;

    protected Address() { // jpa스펙상 작성한 것이고, 사용은 하면 안되는 것이다. (setter가 없으니까)
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
