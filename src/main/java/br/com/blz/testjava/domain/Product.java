package br.com.blz.testjava.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Product {
    private long sku;

    @NotEmpty(message = "name field is required")
    private String name;

    @Valid
    @NotNull(message = "inventory is required")
    private Inventory inventory;

    @JsonProperty("isMarketable")
    private boolean isMarketable;

    @JsonIgnore
    private boolean marketable;

    public long getSku() {
        return sku;
    }

    public void setSku(long sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public boolean isMarketable() {
        return isMarketable;
    }

    public void setMarketable(boolean marketable) {
        isMarketable = marketable;
    }

}
