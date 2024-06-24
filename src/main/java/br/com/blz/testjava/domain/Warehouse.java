package br.com.blz.testjava.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Warehouse {

    @NotEmpty(message = "warehouses.locality field is required")
    private String locality;

    @NotNull(message = "warehouses.quantity field is required")
    private int quantity;

    @NotEmpty(message = "warehouses.type field is required")
    private String type;

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
