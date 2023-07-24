package shoppingproject.shop.domain.item;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.springframework.web.multipart.MultipartFile;
import shoppingproject.shop.domain.Cart;
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
 @Nationalized // String 데이터 타입을 mssl database에 nvarchar 데이터 타입으로 매핑
 private String ItemName;
 private Integer Price;
 @Nationalized
 private String Description;

 @Transient
 private List<MultipartFile> imageFiles;
 @Transient
 private List<String> colors;
 @Transient
 private List<String> sizes;
 @OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
 private List<UploadFile> uploadFiles;
@OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
 private List<Size> sizeList= new ArrayList<>();
 @OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
 private List<Color> colorList = new ArrayList<>();
 @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
 @JoinColumn(name="category_id")
 private Category category;

 @OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
 private List<Cart> cartList = new ArrayList<>();
 @Transient
 private Long category_id;

 // 생성 메서드 //
 public static Item createItem(Item item,List<UploadFile> files,Category cate){
  Item newItem;
  newItem = item;
  //상세설명 줄바꿈 처리
  item.setDescription(item.getDescription().replace("\r\n","<br>"));

  List<Color> newColor = new ArrayList<>();
  List<Size> newSize = new ArrayList<>();
  //색상
  for(String o : item.getColors()){
   Color  co  = new Color(item,o);
   newColor.add(co);
  }
  //사이즈
  for(String s : item.getSizes()){
   Size co  = new Size(item,s);
   newSize.add(co);
  }
  // 카테고리
  newItem.setCategory(cate);

  newItem.setColorList(newColor);
  newItem.setSizeList(newSize);

  //파일
  List<UploadFile> newFiles = UploadFile.createFile(files, item);
  newItem.setUploadFiles(newFiles);
  return newItem;
 }

 // 상세 메소드 //
 public static Item getDetailItem(Item item){
  Item newItem;
  newItem = item;
  //상세설명 줄바꿈 처리
  item.setDescription(item.getDescription().replace("<br>","\r\n"));

  List<String> newColor=new ArrayList<>();
  List<String> newSize=new ArrayList<>();
  for(Color s : item.getColorList()){
   newColor.add(s.getName());
  }
  for(Size s : item.getSizeList()){
   newSize.add(s.getName());
  }
  newItem.setColors(newColor);
  newItem.setSizes(newSize);

 return newItem;

 }

 public static Item getDetailItem(Item item,List<UploadFile> files,Category cate){
  Item newItem;
  newItem = item;
  //상세설명 줄바꿈 처리
  item.setDescription(item.getDescription().replace("<br>","\r\n"));

  List<String> newColor=new ArrayList<>();
  List<String> newSize=new ArrayList<>();
  for(Color s : item.getColorList()){
   newColor.add(s.getName());
  }
  for(Size s : item.getSizeList()){
   newSize.add(s.getName());
  }
  newItem.setColors(newColor);
  newItem.setSizes(newSize);
  newItem.setCategory(cate);
  //파일
  List<UploadFile> newFiles = UploadFile.createFile(files, item);
  newItem.setUploadFiles(newFiles);
  // 기존 파일 저장
  for(UploadFile file :item.getUploadFiles()){
   newItem.getUploadFiles().add(file);
  }

  return newItem;

 }
}



