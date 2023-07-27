package shoppingproject.shop.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shoppingproject.shop.domain.Order;
import shoppingproject.shop.domain.common.PagesUtils;
import shoppingproject.shop.domain.item.Category;
import shoppingproject.shop.domain.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){
            em.persist(item);
        }else{
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() { //여러개 찾는건 spl 사용해야함
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
    public List<Item> findByCategory(long no) {
        return em.createQuery("select i from Item i where i.category.id =:no", Item.class)
                .setParameter("no", no)
                .getResultList();
    }
    public Category findCategoryOne(Long id) {
        return em.find(Category.class, id);
    }
    public List<Category> findAllCategories(){
        return em.createQuery("select i from Category i", Category.class)
                .getResultList();
    }

    public List<Item> findAllPaging(PagesUtils pageUtils){
        return em.createQuery("select i from Item i", Item.class)
                .setFirstResult(pageUtils.getStartItemNum())
                .setMaxResults(pageUtils.getDisplayPageNum()) //offset
                .getResultList();
    }




}
