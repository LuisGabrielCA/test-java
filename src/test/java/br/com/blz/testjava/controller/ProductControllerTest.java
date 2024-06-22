package br.com.blz.testjava.controller;

import br.com.blz.testjava.model.Inventory;
import br.com.blz.testjava.model.Product;
import br.com.blz.testjava.model.Warehouse;
import br.com.blz.testjava.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductService productService;

    @Test
    public void testCreateProduct() throws Exception {
        long sku = System.currentTimeMillis();
        Product product = createSampleProduct(sku, "L'Oréal Professionnel Expert Absolut Repair Cortex Lipidium - Máscara de Reconstrução 500g",
                Arrays.asList(
                        createWarehouse("SP", 12, "ECOMMERCE"),
                        createWarehouse("MOEMA", 3, "PHYSICAL_STORE")
                ));

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }

    @Test
    public void testGetProduct() throws Exception {
        long sku = System.currentTimeMillis();
        Product product = createSampleProduct(sku, "L'Oréal Professionnel Expert Absolut Repair Cortex Lipidium - Máscara de Reconstrução 500g",
                Arrays.asList(
                        createWarehouse("SP", 12, "ECOMMERCE"),
                        createWarehouse("MOEMA", 3, "PHYSICAL_STORE")
                ));
        productService.createProduct(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/{sku}", sku))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.sku").value(sku))
                .andExpect(MockMvcResultMatchers.jsonPath("$.inventory.quantity").value(15))
                .andDo(print());
    }

    @Test
    public void testUpdateProduct() throws Exception {
        long sku = System.currentTimeMillis();
        Product product = createSampleProduct(sku, "L'Oréal Professionnel Expert Absolut Repair Cortex Lipidium - Máscara de Reconstrução 500g",
                Arrays.asList(
                        createWarehouse("SP", 12, "ECOMMERCE"),
                        createWarehouse("MOEMA", 3, "PHYSICAL_STORE")
                ));
        productService.createProduct(product);

        product.setName("Updated Product Name");

        mockMvc.perform(MockMvcRequestBuilders.put("/products/{sku}", sku)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/products/{sku}", sku))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Product Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.inventory.quantity").value(15))
                .andDo(print());
    }

    @Test
    public void testDeleteProduct() throws Exception {
        long sku = System.currentTimeMillis();
        Product product = createSampleProduct(sku, "L'Oréal Professionnel Expert Absolut Repair Cortex Lipidium - Máscara de Reconstrução 500g",
                Arrays.asList(
                        createWarehouse("SP", 12, "ECOMMERCE"),
                        createWarehouse("MOEMA", 3, "PHYSICAL_STORE")
                ));
        productService.createProduct(product);

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/{sku}", sku))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/products/{sku}", sku))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }

    private Product createSampleProduct(long sku, String name, List<Warehouse> warehouses) {
        Product product = new Product();
        product.setSku(sku);
        product.setName(name);

        Inventory inventory = new Inventory();
        int totalQuantity = warehouses.stream().mapToInt(Warehouse::getQuantity).sum();
        inventory.setQuantity(totalQuantity);
        inventory.setWarehouses(new ArrayList<>(warehouses));

        product.setInventory(inventory);

        return product;
    }

    private Warehouse createWarehouse(String locality, int quantity, String type) {
        Warehouse warehouse = new Warehouse();
        warehouse.setLocality(locality);
        warehouse.setQuantity(quantity);
        warehouse.setType(type);
        return warehouse;
    }
}
