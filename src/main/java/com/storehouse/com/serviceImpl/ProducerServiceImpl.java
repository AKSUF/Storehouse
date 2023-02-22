package com.storehouse.com.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimbusds.openid.connect.sdk.assurance.Status;
import com.storehouse.com.dto.ProductDto;
import com.storehouse.com.entity.Account;
import com.storehouse.com.entity.Product;
import com.storehouse.com.entity.Store;
import com.storehouse.com.entity.User;
import com.storehouse.com.exceptions.ResourceNotFoundException;
import com.storehouse.com.repository.AccountRepository;
import com.storehouse.com.repository.ProductRepository;
import com.storehouse.com.repository.StoreRepository;
import com.storehouse.com.security.oath.JwtUtils;
import com.storehouse.com.service.ProducerService;
@Service
public class ProducerServiceImpl implements ProducerService{
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private  ProductRepository productRepository;
	@Autowired
	private StoreRepository storeRepository;
	
	@Override
	public Product addProduct(@Valid ProductDto productDto, String token) {
		String email=jwtUtils.getUserNameFromToken(token);
		Account account=accountRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user","credential",email));
		Product product=this.modelMapper.map(productDto, Product.class);
		product.setUser(account.getUser());
	User user=account.getUser();
	product.setProducer(user.getProducer());
		product.setStatus("Pending");
		return this.productRepository.save(product);
	}

	@Override
	public ProductDto updateaddedProduct(@Valid ProductDto productDto, String token,Long productId) {
		Product product = this.productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product id", productId.toString()));
		String email=jwtUtils.getUserNameFromToken(token);
		Account account=accountRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user","credntial",email));
		product.setCategory(productDto.getCategory());
		product.setImage(productDto.getImage());
		product.setPrice(productDto.getPrice());
	
		Product updateproduct=this.productRepository.save(product);
	
		return this.modelMapper.map(updateproduct, ProductDto.class);
		
	}

	@Override
	public ProductDto addnewProduct(ProductDto productDto, Long userId, Long storeId, String token) {
		String email=jwtUtils.getUserNameFromToken(token);
		Account account=accountRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user","credential",email));
		User user=account.getUser();
		Store store=this.storeRepository.findById(storeId).orElseThrow(()->new ResourceNotFoundException("Store","store Id",storeId.toString()));
		Product product=this.modelMapper.map(productDto, Product.class);
		
	
		product.setUser(user);
		product.setProducer(user.getProducer());
		product.setStore(store);
		product.setStatus("PENDING");
		Product newProduct=this.productRepository.save(product);
		return  this.modelMapper.map(newProduct, ProductDto.class);
	}

	@Override
	public ProductDto getProductById(Long productId) {
		Product product=this.productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","Product Id",productId.toString()));
		
		return this.modelMapper.map(product, ProductDto.class);
	}

	@Override
	public List<ProductDto> getProductByUser(Long userId, String jwtFromRequest) {
		String email=jwtUtils.getUserNameFromToken(jwtFromRequest);
		Account account=accountRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user","credential",email));
		User user=account.getUser();
		List<Product>products=this.productRepository.findByUser(user);
		List<ProductDto>productDtos=products.stream().map((product)->this.modelMapper.map(product,ProductDto.class)).collect(Collectors.toList());
		return productDtos;
	}

	@Override
	public List<ProductDto> getProductAsStore(Long storeId, String token) {
	Store store=this.storeRepository.findById(storeId).orElseThrow(()->new ResourceNotFoundException("Store","StoreId",storeId.toString()));
	List<Product>products=this.productRepository.findByStore(store);
	List<ProductDto>productDtos=products.stream().map((product)->this.modelMapper.map(product,ProductDto.class)).collect(Collectors.toList());
		return productDtos;
	}


}
