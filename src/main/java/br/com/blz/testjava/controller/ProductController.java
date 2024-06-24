package br.com.blz.testjava.controller;

import br.com.blz.testjava.domain.Product;
import br.com.blz.testjava.service.ProductService;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        logger.info("Created product with sku {}", createdProduct.getSku());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("/{sku}")
    public ResponseEntity<Product> getProduct(@PathVariable long sku) {
        Product product = productService.getProductBySkuId(sku);
        logger.info("Fetched product with sku {}", product.getSku());
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping("/{sku}")
    public ResponseEntity<Product> updateProduct(@PathVariable long sku, @Valid @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(sku, product);
        logger.info("Updated product with sku {}", updatedProduct.getSku());
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<Object> deleteProduct(@PathVariable long sku) {
        productService.deleteProduct(sku);
        logger.info("Deleted product with sku {}", sku);
        return ResponseEntity.noContent().build();
    }
}
