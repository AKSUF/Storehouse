package com.storehouse.com.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.storehouse.com.dto.ApiResponse;
import com.storehouse.com.dto.StoreDto;
import com.storehouse.com.security.oath.JwtUtils;
import com.storehouse.com.service.FileService;
import com.storehouse.com.service.StoresService;

@RestController
@RequestMapping("/api/v1/manage")
public class StoreController {
@Autowired
	private StoresService storesService;
@Autowired
private JwtUtils jwtUtils;
@Autowired
private FileService fileService;

@Value("${project.image}")
private String path;
//This method is for adding  store in the project
@PostMapping("/store")
public ResponseEntity<StoreDto>createstore(@Valid @RequestBody StoreDto storeDto,HttpServletRequest request){
	System.out.println("///This is for add Store controller///////////////////");
	
	System.out.println("///Store stre///////////////////");
	StoreDto addStore=this.storesService.createStore(storeDto ,jwtUtils.getJWTFromRequest(request));
	return new ResponseEntity<StoreDto>(addStore,HttpStatus.CREATED);
	
}

@PutMapping("/store/{storeId}")
public ResponseEntity<StoreDto>updateStore(@Valid  @RequestBody StoreDto storeDto,@PathVariable Long storeId,HttpServletRequest request){
	StoreDto updateStore=this.storesService.updateStore(storeDto,storeId,request);
	return new ResponseEntity<StoreDto>(updateStore,HttpStatus.OK);
	
}
@DeleteMapping("/store/{storeId}")
public ResponseEntity<ApiResponse>deleteStore(@PathVariable Long storeId,HttpServletRequest request){
	this.storesService.deleteStore(storeId,jwtUtils.getJWTFromRequest(request));
	return new ResponseEntity<ApiResponse>(new ApiResponse("Store is deleted successfully",true),HttpStatus.OK);
	
}
@GetMapping("/store/{storeId}")
public ResponseEntity<StoreDto>getStore(@PathVariable Long storeId,HttpServletRequest request){
	StoreDto storeDto=this.storesService.getStore(storeId,jwtUtils.getJWTFromRequest(request));
	return new ResponseEntity<StoreDto>(storeDto,HttpStatus.OK);
	
}
@GetMapping("/store")
public ResponseEntity<List<StoreDto>>getAllStore(HttpServletRequest request){
	List<StoreDto>storeDtos=this.storesService.getAllCatagory(jwtUtils.getJWTFromRequest(request));
	
	System.out.println("This is store /////////////////////////////////");
	return new ResponseEntity<List<StoreDto>>(storeDtos,HttpStatus.OK);
	
}

@PostMapping("/store/image/{storeId}")
public ResponseEntity<StoreDto>uploadImage(@RequestParam ("file") MultipartFile image,@PathVariable Long storeId,HttpServletRequest request) throws IOException{
	System.out.println("This is image /////////////////////////////////");
	System.out.println(image);
	System.out.println("This is image /////////////////////////////////");
	System.out.println("This is image /////////////////////////////////");
	StoreDto storeDto=this.storesService.getStoreById(storeId);
	System.out.println("Uploaded image is best thing");
	String fileName=this.fileService.uploadaStoreImage(path,image);
	System.out.println("Uploaded image is best thing");
	storeDto.setStoreImage(fileName);
	System.out.println("Uploaded image is best thing");
	StoreDto updateStore=this.storesService.updateImage(storeDto,jwtUtils.getJWTFromRequest(request),storeId);
	System.out.println("Uploaded image is best thing");
	return new ResponseEntity<StoreDto>(updateStore,HttpStatus.OK);
}


}
