package shoppingproject.shop.domain.item;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import shoppingproject.shop.domain.common.UploadFile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Item {
 @jakarta.persistence.Id
 @GeneratedValue
 @Column(name = "item_id")
 private Long Id;
 private String ItemName;
 private Integer Price;
 private String Description;

 @Transient
 private List<MultipartFile> imageFiles;
 @OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
 private List<UploadFile> uploadFiles;
@OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
 private List<Size> sizeList= new ArrayList<>();
 @OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
 private List<Color> colorList = new ArrayList<>();
 @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
 @JoinColumn(name="category_id")
 private Category category;





}



