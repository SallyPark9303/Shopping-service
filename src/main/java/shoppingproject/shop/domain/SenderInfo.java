package shoppingproject.shop.domain;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class SenderInfo {
    @Builder.Default
    private Integer phone1;
    private Integer phone2;
    private Integer phone3;
}
