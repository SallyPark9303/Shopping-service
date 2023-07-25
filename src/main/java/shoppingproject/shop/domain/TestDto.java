package shoppingproject.shop.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestDto {
    private int testId;
    private SenderInfo senderInfo = new SenderInfo();
}
