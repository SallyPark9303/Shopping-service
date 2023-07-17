package shoppingproject.shop.web.item;

import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/item")
public class ItemController {

    @GetMapping("/detailPage")
    public String detailPage(){
        return "/item/detail";

    }
    @GetMapping("/listPage")
    public String listPage(){
        return "/item/list";

    }
}
