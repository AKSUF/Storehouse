package com.storehouse.com.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storehouse.com.dto.CartDto;
import com.storehouse.com.dto.CartItemDto;
import com.storehouse.com.entity.Account;
import com.storehouse.com.entity.Cart;
import com.storehouse.com.entity.CartItem;
import com.storehouse.com.entity.Product;
import com.storehouse.com.entity.User;
import com.storehouse.com.exceptions.ResourceNotFoundException;
import com.storehouse.com.repository.AccountRepository;
import com.storehouse.com.repository.CartItemRepository;
import com.storehouse.com.repository.CartRepository;
import com.storehouse.com.repository.ProductRepository;
import com.storehouse.com.repository.UserRepository;
import com.storehouse.com.security.oath.JwtUtils;
import com.storehouse.com.service.CartService;
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private ModelMapper modelmapper;
	@Autowired
	private UserRepository userRepository;
	@Autowired
private CartItemRepository cartItemRepository;
	@Override
	public CartDto addCart(Long productId, String token, CartItemDto cartItemDto) {
		
	
		String email=jwtUtils.getUserNameFromToken(token);
		Account account=accountRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user","credential",email));
		User user=account.getUser();
		System.out.println(user+"//////////////////////////////////User");
		System.out.println(user+"//////////////////////////////////User");
		System.out.println(user+"//////////////////////////////////User");
	Product product=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product","productId",productId.toString()));
	//get the cart or create new one
	 
    Cart cart = user.getCart();
    cart.setUser(user);
	System.out.println(cart+"//////////////////////////////////cartdsetuser");
  
    if (userRepository != null && user != null) {
        userRepository.save(user);
    }
    CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);
	System.out.println(cartItem+"//////////////////////////////////cartitemsetuser");
    if (cartItem == null) {
        // Create a new cart item
        cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cart.getCartItems().add(cartItem);
    } else {
        System.out.println("Your cart");
    }

  
    cartRepository.save(cart);
   return modelmapper.map(cart, CartDto.class);

}
	@Override
	public CartDto getCart(Long cartId, String token) {
		String email=jwtUtils.getUserNameFromToken(token);
		Account account=accountRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user","credential",email));
		User user=account.getUser();
	    Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartId.toString()));
        
        return modelmapper.map(cart, CartDto.class);
		
	}
	@Override
	public CartDto updateCartItem(Long cartItemId, String token) {
		String email=jwtUtils.getUserNameFromToken(token);
		Account account=accountRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user","credential",email));
		User user=account.getUser();
        // Find the cart item
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart Item", "id", cartItemId.toString()));
        // Save the changes
        cartItemRepository.save(cartItem);
		return modelmapper.map(cartItem, CartDto.class);
	}
	@Override
	public void removeCartItem(Long cartItemId, String token) {
		String email=jwtUtils.getUserNameFromToken(token);
		Account account=accountRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user","credential",email));
		User user=account.getUser();
		  CartItem cartItem = cartItemRepository.findById(cartItemId)
	                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "id", cartItemId.toString()));
	        cartItemRepository.delete(cartItem);
	    }
	@Override
	public CartDto getUserCart(String token) {
		String email=jwtUtils.getUserNameFromToken(token);
		Account account=accountRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user","credential",email));
		User user=account.getUser();
		
		return null;
	}

	}
	
	
	
	
		
	
	