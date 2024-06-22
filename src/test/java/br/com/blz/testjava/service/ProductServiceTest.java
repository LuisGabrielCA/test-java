package br.com.blz.testjava.service;

import br.com.blz.testjava.exception.ProductNotFoundException;
import br.com.blz.testjava.model.Inventory;
import br.com.blz.testjava.model.Product;
import br.com.blz.testjava.model.Warehouse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testCreateProduct() {
        Product product = createSampleProduct(123, "product 123", "SP", 12);

        Product createdProduct = productService.createProduct(product);

        assertNotNull(createdProduct);
        assertEquals(product.getSku(), createdProduct.getSku());
        assertEquals(12, createdProduct.getInventory().getQuantity());
        assertEquals(1, createdProduct.getInventory().getWarehouses().size());
        assertEquals("SP", createdProduct.getInventory().getWarehouses().get(0).getLocality());
    }

    @Test
    public void testGetProductBySkuId() {

        Product product = createSampleProduct(456, "product 456", "RJ", 15);
        productService.createProduct(product);

        Product retrievedProduct = productService.getProductBySkuId(456);

        assertNotNull(retrievedProduct);
        assertEquals(product.getSku(), retrievedProduct.getSku());
        assertEquals("product 456", retrievedProduct.getName());
        assertEquals(15, retrievedProduct.getInventory().getQuantity());
        assertEquals(1, retrievedProduct.getInventory().getWarehouses().size());
        assertEquals("RJ", retrievedProduct.getInventory().getWarehouses().get(0).getLocality());
    }

    @Test
    public void testUpdateProduct() {
  
        Product product = createSampleProduct(789, "product 789", "MG", 20);
        productService.createProduct(product);

        product.setName("Updated product 789 name");
        productService.updateProduct(789, product);
        Product updatedProduct = productService.getProductBySkuId(789);

        assertEquals("Updated product 789 name", updatedProduct.getName());
        assertEquals(20, updatedProduct.getInventory().getQuantity());
        assertEquals(1, updatedProduct.getInventory().getWarehouses().size());
        assertEquals("MG", updatedProduct.getInventory().getWarehouses().get(0).getLocality());
    }

    @Test
    public void testDeleteProduct() {

        Product product = createSampleProduct(987, "product 987", "RS", 10);
        productService.createProduct(product);

        productService.deleteProduct(987);

        assertThrows(ProductNotFoundException.class, () -> productService.getProductBySkuId(987L));
    }

    private Product createSampleProduct(long sku, String name, String locality, int warehouseQuantity) {
        Product product = new Product();
        product.setSku(sku);
        product.setName(name);

        Inventory inventory = new Inventory();
        inventory.setQuantity(warehouseQuantity);

        Warehouse warehouse = new Warehouse();
        warehouse.setLocality(locality);
        warehouse.setQuantity(warehouseQuantity);
        warehouse.setType("ECOMMERCE");

        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse);

        inventory.setWarehouses(warehouses);

        product.setInventory(inventory);

        return product;
    }
}
