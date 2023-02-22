package com.storehouse.com.serviceImpl;


import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storehouse.com.dto.StoreDto;
import com.storehouse.com.entity.Account;
import com.storehouse.com.entity.Store;
import com.storehouse.com.entity.User;
import com.storehouse.com.exceptions.ResourceNotFoundException;
import com.storehouse.com.repository.AccountRepository;
import com.storehouse.com.repository.StoreRepository;
import com.storehouse.com.security.oath.JwtUtils;
import com.storehouse.com.service.StoresService;
@Service
public class StoresServiceImpl implements StoresService {

@Autowired
private AccountRepository accountRepository;
@Autowired
private JwtUtils jwtUtils;
@Autowired
private ModelMapper modelMapper;
@Autowired
private StoreRepository storeRepository;
@Override
public StoreDto createStore(@Valid StoreDto storeDto, String token) {
	String email=jwtUtils.getUserNameFromToken(token);
	Account account=accountRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user","credential",email));
	Store store=this.modelMapper.map(storeDto,Store.class);
store.setUser(account.getUser());
	User user=account.getUser();
	store.setStoreManager(user.getStoreManager());

	Store saveStore=this.storeRepository.save(store);
	return this.modelMapper.map(saveStore, StoreDto.class);
}
@Override
public StoreDto updateStore(@Valid StoreDto storeDto, Long storeId, HttpServletRequest request) {
Store store=this.storeRepository.findById(storeId).orElseThrow(()->new ResourceNotFoundException("Store","Store Id",storeId.toString()));
store.setCatagory(storeDto.getCatagory());
store.setOpenday(storeDto.getOpenday());
store.setStoreLocation(storeDto.getStoreLocation());
Store updateStore=this.storeRepository.save(store);
return this.modelMapper.map(updateStore, StoreDto.class);
}
@Override
public void deleteStore(Long storeId, String token) {
	String email=jwtUtils.getUserNameFromToken(token);
	Account account=accountRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user","credential",email));
	Store store=this.storeRepository.findById(storeId).orElseThrow(()->new ResourceNotFoundException("Store","Store Id",storeId.toString()));
	System.out.println(store);
	this.storeRepository.delete(store);
}
@Override
public List<StoreDto> getAllCatagory(String token) {
	List<Store>stores=this.storeRepository.findAll();
	List<StoreDto>storeDtos=stores.stream().map((store)->this.modelMapper.map(store, StoreDto.class)).collect(Collectors.toList());
	System.out.println(storeDtos);
	return storeDtos;
}
@Override
public StoreDto getStore(Long storeId, String token) {
Store store=this.storeRepository.findById(storeId).orElseThrow(()->new ResourceNotFoundException("Store","Store Id",storeId.toString()));
System.out.println(store);
	return this.modelMapper.map(store, StoreDto.class);
}
@Override
public StoreDto getStoreById(Long storeId) {
	Store store=this.storeRepository.findById(storeId).orElseThrow(()->new ResourceNotFoundException("Store","Store Id",storeId.toString()));
	return this.modelMapper.map(store, StoreDto.class);
}
@Override
public StoreDto updateImage(StoreDto storeDto, String jwtFromRequest, Long storeId) {
Store store=this.storeRepository.findById(storeId).orElseThrow(()->new ResourceNotFoundException("Store","Storeid",storeId.toString()));
String email=jwtUtils.getUserNameFromToken(jwtFromRequest);
Account account=accountRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user","credntial",email));
store.setStoreImage(storeDto.getStoreImage());
Store updateStore=this.storeRepository.save(store);
	return this.modelMapper.map(updateStore, StoreDto.class);
}


}
