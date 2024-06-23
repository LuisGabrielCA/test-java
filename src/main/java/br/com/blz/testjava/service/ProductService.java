package br.com.blz.testjava.service;

import br.com.blz.testjava.domain.Inventory;
import br.com.blz.testjava.domain.Product;
import br.com.blz.testjava.domain.Warehouse;
import br.com.blz.testjava.exception.ProductAlreadyCreatedException;
import br.com.blz.testjava.exception.ProductNotFoundException;

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

        updateProductInventory(product);
        productMap.put(sku, product);

        return product;
    }

    public Product getProductBySkuId(long sku) {
        if (!productMap.containsKey(sku)) {
            throw new ProductNotFoundException();
        }

        Product product = productMap.get(sku);
        updateProductInventory(product);

        return product;
    }

    public Product updateProduct(long sku, Product product) {
        if (!productMap.containsKey(sku)) {
            throw new ProductNotFoundException();
        }

        product.setSku(sku);
        productMap.put(sku, product);

        updateProductInventory(product);

        return product;
    }

    public void deleteProduct(long sku) {
        if (!productMap.containsKey(sku)) {
            throw new ProductNotFoundException();
        }
        productMap.remove(sku);
    }

    private void updateProductInventory(Product product) {
        int totalQuantity = calculateTotalQuantity(product.getInventory());
        product.getInventory().setQuantity(totalQuantity);
        boolean isMarketable = isProductMarketable(product);
        product.setMarketable(isMarketable);
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
