package shoppingproject.shop.web.email;


import lombok.Data;

@Data
public class SendEmailForm {
    private String subject;
    private String toEmailAddress;
    private String fromEmailAddress;
    private String message;
}
