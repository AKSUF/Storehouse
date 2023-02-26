package com.storehouse.com.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.storehouse.com.dto.DeliveryDto;
import com.storehouse.com.entity.Account;
import com.storehouse.com.entity.Delivery;
import com.storehouse.com.entity.DeliveryMan;
import com.storehouse.com.entity.Product;
import com.storehouse.com.entity.User;
import com.storehouse.com.exceptions.ResourceNotFoundException;
import com.storehouse.com.repository.AccountRepository;
import com.storehouse.com.repository.DeliveryManRepository;
import com.storehouse.com.repository.DeliveryRepository;
import com.storehouse.com.repository.ProductRepository;
import com.storehouse.com.security.oath.JwtUtils;
import com.storehouse.com.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private DeliveryRepository deliverrepository;
	@Autowired
	private DeliveryManRepository deliveryManRepository;

	@Override
	public DeliveryDto orderProduct(@Valid DeliveryDto deliveryDto, Long productId, String token) {
		Product product = this.productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException("product", "product is not found", productId.toString()));
		String email = jwtUtils.getUserNameFromToken(token);
		Account account = accountRepo.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("user", "credentials", email));
		Delivery delivery = this.modelMapper.map(deliveryDto, Delivery.class);
		delivery.setDelivery_number((int) (Math.floor(Math.random() * (99999999 - 00000000)) + 00000000));
		delivery.setQuantity((int) (Math.floor(Math.random() * (99999999 - 00000000)) + 00000000));
		delivery.setStatus("Pending");

		delivery.setUser(account.getUser());
		delivery.setProduct(product);
		Delivery newOrder = this.deliverrepository.save(delivery);

		return this.modelMapper.map(newOrder, DeliveryDto.class);
	}

	@Override
	public DeliveryDto orderStatus(User user, Long deliveryId, String status) {
		Delivery delivery = this.deliverrepository.findById(deliveryId)
				.orElseThrow(() -> new ResourceNotFoundException("delivery", "delivery id", deliveryId.toString()));
		delivery.setStatus(status);
		System.out.println(status);
		System.out.println("///////////////////////");
		if (status.equalsIgnoreCase("Ordered")) {
			DeliveryMan deliverman = new DeliveryMan();
			deliverman.setUserdelivryman(user);
			deliverman.setDelivery(delivery);
			this.deliveryManRepository.save(deliverman);
		}
		Delivery changeStatus = this.deliverrepository.save(delivery);

		return this.modelMapper.map(changeStatus, DeliveryDto.class);
	}

	// get all delivery deatils
	@Override
	public List<DeliveryDto> userOrders(String token) {
		String email = jwtUtils.getUserNameFromToken(token);
		Account account = accountRepo.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("user", "credentials", email));
		List<Delivery> userOrders = this.deliverrepository.findByUser(account.getUser());
		List<DeliveryDto> orderDtos = userOrders.stream().map(order -> this.modelMapper.map(order, DeliveryDto.class))
				.collect(Collectors.toList());

		return orderDtos;
	}

	@Override
	public DeliveryDto confirmOrderdelivery(Long deliveryId, String token) {
		Delivery orderderProduct = this.deliverrepository.findById(deliveryId)
				.orElseThrow(() -> new ResourceNotFoundException("deliverry", "delivery Id", deliveryId.toString()));
		String email = jwtUtils.getUserNameFromToken(token);
		Account account = accountRepo.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("user", "credentials", email));
		DeliveryMan deliveryMan = new DeliveryMan();
		deliveryMan.setUserdelivryman(account.getUser());
		deliveryMan.setDelivery(orderderProduct);
		deliveryMan = deliveryManRepository.save(deliveryMan);
		return this.modelMapper.map(orderderProduct, DeliveryDto.class);

	}

	// get all product order

	@Override
	public List<DeliveryDto> allOrders() {
		List<Delivery> userOrders = this.deliverrepository.findAll();
		List<DeliveryDto> orderDtos = userOrders.stream().map(order -> this.modelMapper.map(order, DeliveryDto.class))
				.collect(Collectors.toList());

		return orderDtos;
	}

	@Override
	public DeliveryDto singleorder(Long deliveryId) {
		Delivery deliver = this.deliverrepository.findById(deliveryId)
				.orElseThrow(() -> new ResourceNotFoundException("delivery", "delivery id", deliveryId.toString()));
		return this.modelMapper.map(deliver, DeliveryDto.class);
	}

	@Override
	public List<DeliveryDto> pendingOrders() {
		List<DeliveryDto> deliveryDtos = this.deliverrepository.findByStatus("PENDING").stream()
				.map((delivery) -> this.modelMapper.map(delivery, DeliveryDto.class)).collect(Collectors.toList());
		return deliveryDtos;
	}

//	// this method has problem
//	@Override
//	public List<DeliveryDto> delivermanConfirmorders(String token) {
//
//	String email = jwtUtils.getUserNameFromToken(token);
//	Account account = accountRepo.findByEmail(email)
//			.orElseThrow(() -> new ResourceNotFoundException("user", "credentials", email));
//	User deliveryman = account.getUser();
//		List<DeliveryDto> deliveryDto = new ArrayList<DeliveryDto>();
//		deliveryman.getDeliveryMan().stream().forEach((delivery)->{
//			deliveryDto.add(this.modelMapper.map(delivery. , destinationType))
//	})
//	
//		deliveryman.getDeliveryMan().stream().forEach((delivery) -> {
//		deliveryDto.add(this.modelMapper.map(delivery.getDelivery(), DeliveryDto.class));
//	});
//
//	
//		return deliveryDto;
//	}

}
