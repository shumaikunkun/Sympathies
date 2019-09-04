package com.example.springmvc.boot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Product testProduct = new Product();
        testProduct.setName("shira");
        testProduct.setDescription("this is shira");
        testProduct.setType("water");
        testProduct.setCategory("whale");
        testProduct.setPrice(3.0);
        productRepository.save(testProduct);

        Product testProduct2 = new Product();
        testProduct2.setName("shira");
        testProduct2.setDescription("this is shira");
        testProduct2.setType("water");
        testProduct2.setCategory("whale");
        testProduct2.setPrice(3.0);
        productRepository.save(testProduct2);
    }
}