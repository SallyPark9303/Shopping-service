package shoppingproject.shop.web.graph;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shoppingproject.shop.domain.common.Graph;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/graph")
public class GraphController {

    @GetMapping("/order")
    public String orderGraph(Model model){

        List<Graph> result = new ArrayList<>();
        for(int i=1; i<13; i++){
            Graph g = new Graph();
            g.setCategory(i +"월");
            g.setValue(230);
        }

        model.addAttribute("result", result);

        return "/graph/order";
    }
}
