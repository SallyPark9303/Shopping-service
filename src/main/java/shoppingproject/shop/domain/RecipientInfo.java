package shoppingproject.shop.domain;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Embeddable
@Getter
@Setter
public class RecipientInfo {

    private String recipientName;

    private Integer rphone1;
    private Integer rphone2;
    private Integer rphone3;
    private String rzipcode;
    @Nationalized
    private String raddr1;
    @Nationalized
    private String raddr2;
    @Nationalized
    private String deliveryMsg;

}
