package shoppingproject.shop.web.item;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import shoppingproject.shop.domain.common.BaseEntity;
import shoppingproject.shop.domain.common.UploadFile;

import java.util.List;

@Data
public class ItemSaveForm extends BaseEntity {
    @NotBlank
    private String ItemName;
    @NotBlank
    private Integer Price;
    @NotBlank
    private String Description;
    @NotBlank
    private List<MultipartFile> imageFiles;

    private List<MultipartFile> removedFiles;
    private List<String> sizeList;
    private List<String> colorList;
}
