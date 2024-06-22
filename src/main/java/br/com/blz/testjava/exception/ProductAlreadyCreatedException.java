package br.com.blz.testjava.exception;

public class ProductAlreadyCreatedException extends RuntimeException {
    public ProductAlreadyCreatedException() {
        super("Product already created");
    }
}
