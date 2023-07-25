package shoppingproject.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingproject.shop.domain.Cart;
import shoppingproject.shop.domain.item.Item;
import shoppingproject.shop.repository.CartRepository;
import shoppingproject.shop.repository.ItemRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {
    private  final CartRepository cartRepository;
    @Transactional
    public void saveCart(Cart cart){
        cartRepository.save(cart);
    }
}
