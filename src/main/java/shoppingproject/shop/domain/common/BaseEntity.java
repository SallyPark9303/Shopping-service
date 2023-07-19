package shoppingproject.shop.domain.common;

import lombok.Data;

import java.time.DateTimeException;
import java.util.Date;

@Data
public class BaseEntity {

    private Date createDate;
    private Date updateDate;
    private String createUser;
    private String updateUser;
    private Boolean isUse;
    private Boolean isDelete;
}
