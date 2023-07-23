package shoppingproject.shop.domain.common;

import jakarta.persistence.*;
import lombok.*;
import shoppingproject.shop.domain.item.Item;

import java.util.ArrayList;
import java.util.List;

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

    public static List<UploadFile> createFile(List<UploadFile> files, Item item){
        List<UploadFile> newFiles = files;
        for(UploadFile file : newFiles){
            file.item = item;
        }
        return newFiles;
    }

}
