package com.example.GreenNest;

import com.example.GreenNest.model.Category;
import com.example.GreenNest.model.Product;
import com.example.GreenNest.model.SupplierDetails;
import com.example.GreenNest.repository.ProductRepository;
import com.example.GreenNest.repository.SupplierRepository;
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
import java.util.List;

//@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
class GreenNestApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private SupplierRepository supplierRepository;

	@Test
	@Rollback(value = false)
	void contextLoads() throws IOException {
//		File file = new File("D:\\Git\\image.jpg");
//		Product product = new Product();
//
//		byte[] bytes = Files.readAllBytes(file.toPath());
//		product.setContent(bytes);
//		product.setProduct_name("mango");
//		product.setDescription("mango description");
//		product.setPrice(100);
//		product.setQuantity(10);
//		product.setProduct_status(0);
//		product.setFeatured(true);
//		product.setReorder_level(5);
//
//		Product product1 = productRepository.save(product);
//
//		Product product2 = testEntityManager.find(Product.class, product1.getProduct_id());

		SupplierDetails supplierDetails = new SupplierDetails();
		List<Category> name = new ArrayList<Category>();
		name.add(new Category("lfowers"));
		name.add(new Category("fruits"));
		supplierDetails.setFirst_name("nimal");
		supplierDetails.setLast_name("sunil");
		supplierDetails.setAddress("kurunegala");
		supplierDetails.setEmail("nimal@gmail.com");
		supplierDetails.setMobile(0766655432);
		supplierDetails.setCategories(name);

		SupplierDetails supplierDetails1 = supplierRepository.save(supplierDetails);




	}

}
