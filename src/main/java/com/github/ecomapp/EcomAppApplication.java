package com.github.ecomapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private double price;
}

interface ProductRepository extends JpaRepository<Product, Long> {

}


@Controller
class ProductController {
	@Autowired
	private ProductRepository productRepository;
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/products")
	public String index(Model model) {
		model.addAttribute("products", productRepository.findAll());
		return "products";
	}
}


@SpringBootApplication
public class EcomAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcomAppApplication.class, args);
	}


	@Bean
	CommandLineRunner start(ProductRepository productRepository) {
		return args -> {
			productRepository.save(new Product(null, "Ord HP 564", 8000));
			productRepository.save(new Product(null, "Imprimente Lx 11", 100));
			productRepository.save(new Product(null, "Smart phone", 9900));
			productRepository.findAll().forEach(p -> {
				System.out.println(p.getName());
			});
		};
	}

}
