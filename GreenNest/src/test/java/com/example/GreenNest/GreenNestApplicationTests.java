package com.example.GreenNest;

import com.example.GreenNest.model.*;
import com.example.GreenNest.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.swing.text.Document;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
class GreenNestApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderRequestRepository orderRequestRepository;

	@Test
	@Rollback(value = false)
	void contextLoads() throws IOException {

//		OrderRequest orderRequest = new OrderRequest();
//		orderRequest.setDescription("I want this amount of plants");
//		orderRequest.setQuantity(67);
//		orderRequest.setProductName("annasi");
//
//		Customer customer = customerRepository.getById(3);
//
//		orderRequest.setCustomer(customer);
//		orderRequestRepository.save(orderRequest);

		Optional<Product> product = productRepository.findById(40L);
		List<Reviews> reviews = reviewRepository.findByProduct(product.get());
		System.out.println(reviews.get(0).getReview());





//		SupplierDetails supplierDetails = new SupplierDetails();
//		supplierDetails.setFirst_name("dog");
//		supplierDetails.setLast_name("sunil");
//		supplierDetails.setAddress("kurunegala");
//		supplierDetails.setEmail("nimal@gmail.com");
//		supplierDetails.setMobile(0766655432);
		//supplierRepository.save(supplierDetails);

//		Category category = new Category("indoor");
//		Category category1 = new Category("outdoor");

//		List<Category> categories1= new ArrayList<Category>();
//		ArrayList<String> data = new ArrayList<String>();
//		data.add("indoor");
//		data.add("outdoor");

		//Category category2 = categoryRepository.findByCategoryName("indoor");
		//System.out.println(category2);
		//product.getCategories().add(category2);
//		for(int i=0;i<data.size();i++){
//			System.out.println(data.get(i));
//			Category category = categoryRepository.findByCategoryName(data.get(i));
//			categories1.add(category);
//		}
//
//		supplierDetails.getCategories().addAll(categories1);

//		Category category = categoryRepository.findByCategoryName("indoor");
//		Category category1 = categoryRepository.findByCategoryName("outdoor");
//		supplierDetails.getCategories().add(category);
//		supplierDetails.getCategories().add(category1);

//		categoryRepository.saveAll(Arrays.asList(category, category1));
//		supplierDetails.getCategories().addAll(Arrays.asList(category, category1));

//		supplierRepository.save(supplierDetails);








	}

}
