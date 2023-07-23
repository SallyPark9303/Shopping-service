package shoppingproject.shop.domain.cart;

import org.springframework.stereotype.Repository;
import shoppingproject.shop.domain.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class CartRepository {
    private  static final Map<Long, Cart> cartrepo = new HashMap<>(); //static
    private static long sequence = 0L;

    public Cart  save(Cart item){

        item.setCartId(sequence++);
        cartrepo.put(item.getCartId(),item);
        return item;
    }
    public Cart findById(Long id){
        return cartrepo.get(id);
    }


    public List<Cart> findAll(){
        return  new ArrayList<>(cartrepo.values());
    }

    public void update(Long cartId, Cart updateParam){
        Cart findItem = findById(cartId);
        findItem.setItemId(updateParam.getItemId());
        findItem.setItemId(updateParam.getItemId());
        findItem.setUserId(updateParam.getUserId());
       /// findItem.setCreateDate(updateParam.getCreateDate());
       // findItem.setCreateUser(updateParam.getCreateUser());
        cartrepo.replace(cartId, findItem);
    }

    public void delete(Long id){
        cartrepo.remove(id);
    }

    public void clearStore(){
        cartrepo.clear();
    }

    
}
