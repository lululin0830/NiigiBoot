package tw.idv.tibame.products.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.idv.tibame.products.entity.ProductSpec;

public interface ProductSpecRepository extends JpaRepository<ProductSpec, String> {

	Optional<ProductSpec> findById(String productSpecId); 
}
