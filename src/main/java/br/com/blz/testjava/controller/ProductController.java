package br.com.blz.testjava.controller;

import br.com.blz.testjava.model.Product;
import br.com.blz.testjava.service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("/{sku}")
    public ResponseEntity<Product> getProduct(@PathVariable long sku) {
        Product product = productService.getProductBySkuId(sku);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping("/{sku}")
    public ResponseEntity<Void> updateProduct(@PathVariable long sku, @RequestBody Product product) {
        productService.updateProduct(sku, product);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<Object> deleteProduct(@PathVariable long sku) {
        productService.deleteProduct(sku);
        return ResponseEntity.noContent().build();
    }
}
