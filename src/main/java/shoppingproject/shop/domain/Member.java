package shoppingproject.shop.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private String password;
    @NotBlank
    private String name;
    @NotBlank
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

    @OneToOne
    private Cart carts;



}
