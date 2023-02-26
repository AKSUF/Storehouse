package com.storehouse.com.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.storehouse.com.dto.DeliveryDto;
import com.storehouse.com.dto.ProductDto;
import com.storehouse.com.entity.Account;
import com.storehouse.com.entity.Product;
import com.storehouse.com.entity.User;
import com.storehouse.com.repository.ProductRepository;
import com.storehouse.com.security.oath.JwtUtils;
import com.storehouse.com.service.AccountService;
import com.storehouse.com.service.OrderService;
import com.storehouse.com.service.ProducerService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@RestController
@RequestMapping("/api/v1/customer")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ProducerService producerservice;
	
	@Value("${stripe.api.key}")
	private String stripeApiKey;
	
	 @Autowired
	    private Environment environment;
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ModelMapper modelMapper;
//	@PostMapping("/orders/{productId}")
//	
//	public ResponseEntity<DeliveryDto>orderproduct(@Valid @RequestBody DeliveryDto deliveryDto,@PathVariable Long productId,HttpServletRequest request ){
//		DeliveryDto orderProduct=this.orderService.orderProduct(deliveryDto,productId,jwtUtils.getJWTFromRequest(request));
//		
//		return new ResponseEntity<DeliveryDto>(orderProduct,HttpStatus.CREATED);
//	}
//	
	@PostMapping("/orders/{productId}")
	public ResponseEntity<DeliveryDto> orderProduct(@Valid @RequestBody DeliveryDto deliveryDto, @PathVariable Long productId, HttpServletRequest request) throws StripeException {
		String stripeApiKey = environment.getProperty("stripe.api.key");
		Stripe.apiKey = stripeApiKey;
	  //  Product product = productRepository.getProductById(productId);
	    ProductDto product=producerservice.getProductById(productId);
	    System.out.println("Product id"+product);
	    System.out.println("Product id"+productId);
	    // Create a PaymentIntent
	    // get product by id
	   
	    // set product price as total price
	    deliveryDto.setTotalPrice(product.getPrice());
	    System.out.println("Product id"+product.getPrice());
	    
	    Map<String, Object> params = new HashMap<>();
	    System.out.println("Total Price: " + deliveryDto.getTotalPrice());
	    params.put("amount", Math.round(deliveryDto.getTotalPrice() * 100));// amount is in taka
	    System.out.println("Total Price: " + deliveryDto.getTotalPrice());
	    
	    System.out.println("Total Price: " + deliveryDto.getTotalPrice());
	    
	    params.put("currency", "BDT");
	    params.put("payment_method_types", Arrays.asList("card"));

	    PaymentIntent intent = PaymentIntent.create(params);

	    // Save the PaymentIntent ID in the order
	    deliveryDto.setPaymentIntentId(intent.getId());

	    // Place the order
	    DeliveryDto orderProduct = orderService.orderProduct(deliveryDto, productId, jwtUtils.getJWTFromRequest(request));

	    return new ResponseEntity<>(orderProduct, HttpStatus.CREATED);
	}

	
	
	
	
	
	
@PutMapping("/orders/{deliveryId}")
	public ResponseEntity<DeliveryDto>orderProduct(HttpServletRequest request,@PathVariable Long deliveryId,@RequestParam String status ){
	String token = jwtUtils.getJWTFromRequest(request);
	Account account = accountService.getAccount(token);
	User user = account.getUser();
	DeliveryDto orderProduct=this.orderService.orderStatus(user,deliveryId,status);
		return new ResponseEntity<DeliveryDto>(orderProduct,HttpStatus.OK);
	
}

@GetMapping("/orders")
public ResponseEntity<List<DeliveryDto>>getOrderPProduct(HttpServletRequest request){
	System.out.println("this code is for get all deliverdetails");
	List<DeliveryDto>orderProduct=this.orderService.userOrders(jwtUtils.getJWTFromRequest(request));
	if(orderProduct.isEmpty()) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
		
	}
	System.out.println("this code is for get all deliverdetails");
	return ResponseEntity.status(HttpStatus.OK).body(orderProduct);
	
}
@PostMapping("orderconfirm/{deliveryId}")
public ResponseEntity<DeliveryDto>riderConfirm(@PathVariable Long deliveryId,HttpServletRequest request){
	DeliveryDto orderProduct=this.orderService.confirmOrderdelivery(deliveryId,jwtUtils.getJWTFromRequest(request));
	return new ResponseEntity<DeliveryDto>(orderProduct,HttpStatus.CREATED);
	
}



//Drliverymanagement------The store will be manage the delivery thing nobody can not do that other withoiutt his permission
@GetMapping("/orders/alluserorder")
public ResponseEntity<List<DeliveryDto>>getAllOrderProduct(){
	List<DeliveryDto>orderProduct=this.orderService.allOrders();
	if(orderProduct.isEmpty()) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	return ResponseEntity.status(HttpStatus.OK).body(orderProduct);
	
}

@GetMapping("/ordersuser/{deliveryId}")
public ResponseEntity<DeliveryDto>getSingleorder(@PathVariable Long deliveryId){
	DeliveryDto orderProduct=this.orderService.singleorder(deliveryId);
	
	return ResponseEntity.status(HttpStatus.OK).body(orderProduct);
	
}
@GetMapping("/orders/pendingorder")
public ResponseEntity<List<DeliveryDto>>getPemdingOrders(){
	return ResponseEntity.ok(this.orderService.pendingOrders());
	
}


//@GetMapping("/confirm-orders")
//public ResponseEntity<List<DeliveryDto>>getRiderconfirmOrders(HttpServletRequest request,String searchquery){
//	List<DeliveryDto>confirmdelivery=this.orderService.delivermanConfirmorders(jwtUtils.getJWTFromRequest(request));
//	if(confirmdelivery.isEmpty()) {
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//	}
//	return ResponseEntity.status(HttpStatus.OK).body(confirmdelivery);
//}












}
