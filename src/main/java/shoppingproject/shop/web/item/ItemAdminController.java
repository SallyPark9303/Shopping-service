package shoppingproject.shop.web.item;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shoppingproject.shop.domain.common.FileUtils;
import shoppingproject.shop.domain.common.UploadFile;
import shoppingproject.shop.domain.item.Item;
import shoppingproject.shop.domain.item.ItemRepository;
import org.springframework.core.io.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin/item")
@RequiredArgsConstructor
public class ItemAdminController {
    private final ItemRepository itemRepository;
    private final FileUtils fileutils;





    @ModelAttribute("colors")
    public Map<String, String> Colors() {
        Map<String, String> colors = new LinkedHashMap<>();
            colors.put("red","red");
            colors.put("yellow","yellow");
            colors.put("blue","blue");
            colors.put("black","black");
            colors.put("white","white");
         return colors;
        }

    @ModelAttribute("sizes")
    public Map<String, String> sizes() {
        Map<String, String> sizes = new LinkedHashMap<>();
        sizes.put("s","s");
        sizes.put("m","m");
        sizes.put("l","l");
        return sizes;
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
    public String addFrom(@ModelAttribute("item") ItemSaveForm form,Model model){
        log.info("item.add");

        return "/admin/item/add";
    }

    //등록 로직
    @PostMapping("/save")
    public String saveItem(@ModelAttribute("item") ItemSaveForm form, RedirectAttributes
            redirectAttributes, Model model) throws IOException {


        List<UploadFile> storeImageFiles = fileutils.storeFiles(form.getImageFiles());
        //데이터베이스에 저장
        Item item = new Item();

        item.setItemName(form.getItemName());
        item.setDescription(form.getDescription());
        item.setCreateUser(form.getCreateUser());
        item.setCreateDate(form.getCreateDate());
        item.setPrice(form.getPrice());
        item.setImageFiles(storeImageFiles);
        item.setColorList(form.getColorList());
        item.setSizeList(form.getSizeList());
       Item savedItem =  itemRepository.save(item);
        log.info("saved item :={}",savedItem.toString());
        return "redirect:/admin/item/detail/"+savedItem.getId();
    }

    //상세 폼
    @GetMapping("/detail/{id}")
    public String detailForm(Model model, @PathVariable Long id){
        log.info("item.detail");
        Item item = itemRepository.findById(id);
        log.info("colorList ="+item.getColorList());

        model.addAttribute("item",item);
        return "/admin/item/detail";
    }


    //수정 폼
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id,Model model){
        Item item = itemRepository.findById(id);
        model.addAttribute("item",item);
        return "/admin/item/edit";
    }

    //상세 로직
    @PostMapping("/edit")
    public String edit(@ModelAttribute Item item){
        itemRepository.update(item.getId(),item);
        return "redirect:/admin/item/list";
    }

    //삭제 로직
    @PostMapping("/delete")
    public String delete(@ModelAttribute("item") Item item){
        itemRepository.delete(item.getId());
        return "redirect:/admin/item/list";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws  MalformedURLException {
        log.info("filename ="+filename);
        return  new UrlResource("file:" + fileutils.getFullPath(filename));
    }





}
