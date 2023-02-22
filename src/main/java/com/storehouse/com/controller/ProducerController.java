package com.storehouse.com.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;

import com.storehouse.com.dto.ApiResponse;
import com.storehouse.com.dto.ProductDto;
import com.storehouse.com.entity.Product;
import com.storehouse.com.security.oath.JwtUtils;
import com.storehouse.com.service.FileService;
import com.storehouse.com.service.ProducerService;

@RestController
@RequestMapping("/api/v1/producer")
public class ProducerController {
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private ProducerService producerService;
	@Autowired
	private FileService fileservice;
	@Autowired
	private ModelMapper modelMapper;
	
	@Value("${project.image}")
	private String path;
	
	//add new product
	@PostMapping("/product")
	public ResponseEntity<ProductDto>addProduct(@Valid @RequestBody ProductDto productDto,HttpServletRequest request){
		System.out.println("///This is for add product controller///////////////////");
		
		System.out.println("///Foof food///////////////////");
		Product product=this.producerService.addProduct(productDto,jwtUtils.getJWTFromRequest(request));
		ProductDto newProduct=this.modelMapper.map(product,ProductDto.class);
		return new ResponseEntity<ProductDto>(newProduct,HttpStatus.CREATED);
		
	}
	//this controller for update added product
	@PutMapping("/product")
	public ResponseEntity<ApiResponse>updateproduct(@Valid @RequestBody ProductDto productDto,HttpServletRequest request,Long productId){
		System.out.println("////Update Product////");
		this.producerService.updateaddedProduct(productDto,jwtUtils.getJWTFromRequest(request),productId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Product updated successfully",true),HttpStatus.CREATED);
		
	}
	//as uploading as user and product id
	@PostMapping("/product/{userId}/{storeId}")
	public ResponseEntity<ProductDto>addproduct(@RequestBody ProductDto productDto,@PathVariable Long userId,@PathVariable Long storeId,HttpServletRequest request){
		ProductDto addproduct=this.producerService.addnewProduct(productDto,userId,storeId,jwtUtils.getJWTFromRequest(request));
		return new ResponseEntity<ProductDto>(addproduct,HttpStatus.CREATED);
		
	}
	
	//For uploading image
	@PostMapping("/product/{productId}")
	public ResponseEntity<ProductDto>uploadproductimage(@RequestParam("image")MultipartFile image,@PathVariable Long productId,HttpServletRequest request) throws  IOException{
		ProductDto productDto=this.producerService.getProductById(productId);
		String fileName=this.fileservice.uploadproductImage(path,image);
		productDto.setImage(fileName);
		ProductDto updateProduct=this.producerService.updateaddedProduct(productDto,jwtUtils.getJWTFromRequest(request),productId);
		return new ResponseEntity<ProductDto>(updateProduct,HttpStatus.OK);
		
	}
	//get image from 
	@GetMapping(value="/product/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName")String imageName,HttpServletResponse response,HttpServletRequest request) throws IOException{
		InputStream resource = this.fileservice.getResource(path,imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
	
	
	@GetMapping("/product/{userId}")
	public ResponseEntity<List<ProductDto>>getProductByUser(@PathVariable Long userId,HttpServletRequest request){
		List<ProductDto>products=this.producerService.getProductByUser(userId,jwtUtils.getJWTFromRequest(request));
		return new ResponseEntity<List<ProductDto>>(products,HttpStatus.OK);
		
	}
	@GetMapping("/product/store/{storeId}")
	public ResponseEntity<List<ProductDto>>getProductasStore(@PathVariable Long storeId,HttpServletRequest request){
		List<ProductDto>product=this.producerService.getProductAsStore(storeId,jwtUtils.getJWTFromRequest(request));
		return new ResponseEntity<List<ProductDto>>(product,HttpStatus.OK);
		
	}
	
	
	
	
	
}
