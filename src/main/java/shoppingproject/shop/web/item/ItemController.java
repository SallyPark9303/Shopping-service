package shoppingproject.shop.web.item;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shoppingproject.shop.domain.item.Item;
import shoppingproject.shop.domain.item.ItemRepository;

import java.util.List;
@Slf4j
@Controller
@RequestMapping("/admin/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    //목록 폼
    @GetMapping("/list")
    public String list(Model model ){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "/admin/item/list";
    }

    //등록 폼
    @GetMapping("/add")
    public String addFrom(){
        log.info("item.add");

        return "/admin/item/add";
    }

    //등록 로직
    @PostMapping("/add")
    public String add(@ModelAttribute("item") Item item){
        itemRepository.save(item);
        return "redirect:/admin/item/list";
    }

    //상세 폼
    @GetMapping("/detail/{id}")
    public String detailForm(Model model, @PathVariable Long id){
        log.info("item.detail");
        Item item = itemRepository.findById(id);
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
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute Item item){
        itemRepository.update(id,item);
        return "redirect:/admin/item/list";
    }

    //삭제 로직
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        itemRepository.delete(id);
        return "redirect:/admin/item/list";
    }

}
