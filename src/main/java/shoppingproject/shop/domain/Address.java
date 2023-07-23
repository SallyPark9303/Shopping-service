package shoppingproject.shop.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;

@Embeddable // 내장 객체
@Getter
public class Address {

    private String city;
    private String address1;
    private String address2;
    private String zipcode;
}
