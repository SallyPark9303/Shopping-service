package shoppingproject.shop.domain.email;

import org.springframework.context.annotation.Bean;

import java.util.Map;

public interface EmailUtil {
    Map<String, Object> sendEmail(String toAddress, String subject, String body);
}
