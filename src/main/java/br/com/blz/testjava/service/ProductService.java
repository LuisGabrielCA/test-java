package br.com.blz.testjava.service;

import br.com.blz.testjava.exception.ProductAlreadyCreatedException;
import br.com.blz.testjava.exception.ProductNotFoundException;

import br.com.blz.testjava.model.Inventory;
import br.com.blz.testjava.model.Product;
import br.com.blz.testjava.model.Warehouse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {

    private Map<Long, Product> productMap = new HashMap<>();

    public Product createProduct(Product product) {
        long sku = product.getSku();
        if (productMap.containsKey(sku)) {
            throw new ProductAlreadyCreatedException();
        }
        productMap.put(sku, product);
        return product;
    }

    public Product getProductBySkuId(long sku) {
        Product product = productMap.get(sku);
        if (!productMap.containsKey(sku)) {
                throw new ProductNotFoundException();
        }
        int totalQuantity = calculateTotalQuantity(product.getInventory());
        product.getInventory().setQuantity(totalQuantity);

        boolean isMarketable = isProductMarketable(product);
        product.setMarketable(isMarketable);

        return product;
    }

    public Product updateProduct(long sku, Product product) {
        if (!productMap.containsKey(sku)) {
            throw new ProductNotFoundException();
        }
        product.setSku(sku);
        productMap.put(sku, product);
        return product;
    }

    public void deleteProduct(long sku) {
        if (!productMap.containsKey(sku)) {
            throw new ProductNotFoundException();
        }
        productMap.remove(sku);
    }

    private boolean isProductMarketable(Product product) {
        return product.getInventory().getQuantity() > 0;
    }

    private int calculateTotalQuantity(Inventory inventory) {
        return inventory.getWarehouses().stream()
                .mapToInt(Warehouse::getQuantity)
                .sum();
    }
}
