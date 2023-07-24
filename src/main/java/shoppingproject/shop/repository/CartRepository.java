package shoppingproject.shop.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shoppingproject.shop.domain.Cart;
import shoppingproject.shop.domain.Member;
import shoppingproject.shop.domain.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
@RequiredArgsConstructor
public class CartRepository {
    private final EntityManager em;

    public void save(Cart cart){
        em.persist(cart);
    }
    public List<Item> findById(Member member){

        return em.createQuery("select i.item from Cart i where i.member.id=:id", Item.class)
                .setParameter("di", member.getId())
                .getResultList();
    }


    public List<Cart> findAll() {
        return em.createQuery("select i from Cart i", Cart.class)
                .getResultList();
    }

    public void update(Long cartId, Cart updateParam){

    }

    public void delete(Long id){

    }

    public void clearStore(){

    }

    
}
