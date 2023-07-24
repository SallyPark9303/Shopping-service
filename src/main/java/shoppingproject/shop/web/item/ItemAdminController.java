package shoppingproject.shop.web.item;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shoppingproject.shop.domain.common.FileUtils;
import shoppingproject.shop.domain.common.UploadFile;
import shoppingproject.shop.domain.item.Category;
import shoppingproject.shop.domain.item.Color;
import shoppingproject.shop.domain.item.Item;
import shoppingproject.shop.domain.item.Size;
import shoppingproject.shop.repository.ItemRepository;
import org.springframework.core.io.*;
import shoppingproject.shop.service.ItemService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin/item")
@RequiredArgsConstructor
public class ItemAdminController {
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final FileUtils fileutils;




    @ModelAttribute("colors") public Map<String, String> Colors() {
       Map<String, String> colors = new LinkedHashMap<>();
           colors.put("red","red");
           colors.put("yellow","yellow");colors.put("blue","blue");
           colors.put("black","black");
           colors.put("white","white");return colors;
       }
    @ModelAttribute("sizes")
    public Map<String, String> sizes() {
        Map<String, String> sizes = new LinkedHashMap<>();
        sizes.put("s","s");
        sizes.put("m","m");
        sizes.put("l","l");
        return sizes;
    }

    @ModelAttribute("categories")
    public List<Category> categories() {
        return itemRepository.findAllCategories();
    }
    //목록 폼
    @GetMapping("/list")
    public String list(Model model ){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "/admin/item/list";
    }

    //등록 폼
    @GetMapping("/add")
    public String addFrom(@ModelAttribute("item") Item item,Model model){
        log.info("item.add");

        return "/admin/item/add";
    }

    //등록 로직
    @PostMapping("/save")
    public String saveItem(@ModelAttribute("item") Item form, RedirectAttributes
            redirectAttributes, Model model) throws IOException {

        List<UploadFile> storeImageFiles = fileutils.storeFiles(form.getImageFiles());
        //카테고리 찾기
        Category findone = itemRepository.findCategoryOne(form.getCategory_id());

        Item itm = Item.createItem(form, storeImageFiles,findone);
        itemService.saveItem(itm);
        return "redirect:/admin/item/list";
    }

    //상세 폼
    @GetMapping("/{id}/detail")
    public String detailForm(Model model, @PathVariable Long id){
        Item item = itemRepository.findOne(id);
        Item.getDetailItem(item);

        model.addAttribute("item",item);
        return "/admin/item/detail";
    }


    //수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id,Model model){
        Item item = itemRepository.findOne(id);
        Item.getDetailItem(item);

        model.addAttribute("item",item);
        return "/admin/item/edit";
    }

    //수정 로직
    @PostMapping("/edit")
    public String edit(@ModelAttribute Item form) throws IOException {
        //카테고리 찾기

        List<UploadFile> storeImageFiles = fileutils.storeFiles(form.getImageFiles());
        //카테고리 찾기
        Category findone = itemRepository.findCategoryOne(form.getCategory_id());

        Item itm = Item.getDetailItem(form, storeImageFiles,findone);
        itemService.saveItem(itm);
        return "redirect:/admin/item/list";
    }

    //삭제 로직
    @PostMapping("/delete")
    public String delete(@ModelAttribute("item") Item item){
      //  itemRepository.delete(item.getId());
        return "redirect:/admin/item/list";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws  MalformedURLException {
        log.info("filename ="+filename);
        return  new UrlResource("file:" + fileutils.getFullPath(filename));
    }

}
