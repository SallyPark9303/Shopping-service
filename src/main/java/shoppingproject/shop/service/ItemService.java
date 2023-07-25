package shoppingproject.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingproject.shop.domain.common.UploadFile;
import shoppingproject.shop.domain.item.Category;
import shoppingproject.shop.domain.item.Color;
import shoppingproject.shop.domain.item.Item;
import shoppingproject.shop.domain.item.Size;
import shoppingproject.shop.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private  final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Item item, List<UploadFile> files, Category cate){   // jpa 에 의해 커밋됨. 변경감지 기능
        Item newItem = itemRepository.findOne(item.getId());

        //상세설명 줄바꿈 처리
        newItem.setDescription(item.getDescription().replace("<br>","\r\n"));

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
        newItem.setPrice(item.getPrice());
        newItem.setItemName(item.getItemName());
        newItem.setCategory(cate);
        //파일
        List<UploadFile> newFiles = UploadFile.createFile(files, item);
        //newItem.setUploadFiles(newFiles);
        // 새로운 파일 저장
        for(UploadFile file :newFiles){
            newItem.getUploadFiles().add(file);
        }
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

}
