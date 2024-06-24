package br.com.blz.testjava.exception;

public class SkuFieldIsRequiredException extends RuntimeException {
    public SkuFieldIsRequiredException() {
        super("sku field is required");
    }
}
