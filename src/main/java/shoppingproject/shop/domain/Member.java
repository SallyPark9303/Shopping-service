package shoppingproject.shop.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.Nationalized;
import org.hibernate.validator.constraints.Length;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id") // pk 이름
    private Long id;
    @NotBlank
    private String loginId;
    @NotBlank
    //@Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
         //   message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @Nationalized
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Length(min= 3,max=20,message = "최소 3자리부터 20자리까지 입력가능합니다.")
    private String name;
    @NotBlank
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;
    private String contact;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Gender gender = Gender.Man;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserType userType = UserType.USER;
    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updateDate = LocalDateTime.now();

    @OneToMany
    private List<Order> orders = new ArrayList<>();
    @OneToMany
    private List<Cart> carts = new ArrayList<>();

    private String emailCheckToken; // 이메일 인증 토큰값
    @Builder.Default
    private Boolean isEmailVerified  =false;

    public void  generateEmailCheckToken(){
        this.emailCheckToken = UUID.randomUUID().toString();

    }
    public void completeSignUp() {
        this.isEmailVerified= true;

    }


    public boolean isValidToken(String token) {
       return this.emailCheckToken.equals(token);
    }
}
