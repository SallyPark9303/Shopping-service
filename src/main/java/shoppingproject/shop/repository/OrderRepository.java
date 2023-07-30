package shoppingproject.shop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shoppingproject.shop.domain.Member;
import shoppingproject.shop.domain.Order;
import shoppingproject.shop.domain.OrderItem;
import shoppingproject.shop.domain.common.PagesUtils;
import shoppingproject.shop.domain.item.Category;
import shoppingproject.shop.domain.item.Item;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    @PersistenceContext
    private final EntityManager em;

    public void save(Order order){

        em.persist(order);

    }
    public Order findOne(Long id){
        return em.find(Order.class,id);
    }
    public List<Order> findByUserId(Long id){
        return em.createQuery("select i from Order i where member.id = :id", Order.class)
                .setParameter("id",id)
                .getResultList();
    }
    public List<Order> findByUserIdPaging(long id, PagesUtils pageUtils){
        return em.createQuery("select i from Order i where member.id = :id", Order.class)
                .setParameter("id",id)
                .setFirstResult(pageUtils.getStartItemNum())
                .setMaxResults(pageUtils.getDisplayPageNum()) //offset
                .getResultList();
    }

    public List<Order> findAll(){
        return em.createQuery("select i from Order i", Order.class)
                .getResultList();
    }
    public List<Order> findAllPaging(PagesUtils pageUtils){
        return em.createQuery("select i from Order i", Order.class)
                .setFirstResult(pageUtils.getStartItemNum())
                .setMaxResults(pageUtils.getDisplayPageNum()) //offset
                .getResultList();
    }


}
