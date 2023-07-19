package shoppingproject.shop.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ModelAttribute;
import shoppingproject.shop.domain.common.BaseEntity;
import shoppingproject.shop.domain.common.UploadFile;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class Item extends BaseEntity {
 private Long Id;
 private String ItemName;
 private Integer Price;
 private String Description;
 private List<UploadFile> imageFiles;
 private List<String> sizeList;
 private List<String> colorList;


}

