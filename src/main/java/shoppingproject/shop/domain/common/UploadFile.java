package shoppingproject.shop.domain.common;

import jakarta.persistence.*;
import lombok.*;
import shoppingproject.shop.domain.item.Item;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class UploadFile {
    @Id
    @GeneratedValue
    @Column(name = "upload_id")
    private Long id;
    private String uploadFileName;
    private String storeFileName;
    private String isRemove;

    @ManyToOne
   @JoinColumn(name="item_id")
   private Item item;


    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

}
