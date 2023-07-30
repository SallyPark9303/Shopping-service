package shoppingproject.shop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shoppingproject.shop.domain.Cart;
import shoppingproject.shop.domain.Member;
import shoppingproject.shop.domain.common.PagesUtils;
import shoppingproject.shop.domain.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
@RequiredArgsConstructor
public class CartRepository {
    @PersistenceContext
    private final EntityManager em;

    public void save(Cart cart){
        em.persist(cart);
    }
    public List<Cart> findByUserId(Member member){

        return em.createQuery("select i.item from Cart i where i.member.id=:id", Cart.class)
                .setParameter("di", member.getId())
                .getResultList();
    }


    public List<Cart> findAll() {
        return em.createQuery("select i from Cart i", Cart.class)
                .getResultList();
    }
    public Cart findOne(long id) {
        return em.createQuery("select i from Cart i where i.id =:id", Cart.class)
                .setParameter("id",id)
                .getSingleResult();
    }
    public List<Cart> findAllPaging(PagesUtils pageUtils, long userId){
        return em.createQuery("select c from Cart c"+
                        " join fetch c.member m"+
                        " join fetch c.item i " +
                        " join fetch i.uploadFiles u" +
                        " where m.id =:userId", Cart.class)
                .setFirstResult(pageUtils.getStartItemNum())
                .setMaxResults(pageUtils.getDisplayPageNum()) //offset
                .setParameter("userId",userId)
                .getResultList();
    }

    public List<Cart> findAllWithUserId(long userId){
        return em.createQuery("select distinct c from Cart c"+
                                        " join fetch c.member m"+
                                        " join fetch c.item i where m.id =:userId"
                                       ,Cart.class)
                .setParameter("userId",userId)
                .getResultList();

    }


    public void update(Long cartId, Cart updateParam){

    }
    @Transactional
    public void delete(Cart id){
       em.remove(id);
    }

    public void clearStore(){

    }

    
}
