package tw.idv.tibame.products.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.idv.tibame.products.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	Optional<Product> findById(Integer productId); 

}
