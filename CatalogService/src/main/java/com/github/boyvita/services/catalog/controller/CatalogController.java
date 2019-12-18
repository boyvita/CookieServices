package com.github.boyvita.services.catalog.controller;

import com.github.boyvita.services.catalog.exception.NoEntityException;
import com.github.boyvita.services.catalog.model.Item;
import com.github.boyvita.services.catalog.model.Product;
import com.github.boyvita.services.catalog.repo.ItemRepository;
import com.github.boyvita.services.catalog.repo.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping//("catalog")
@ComponentScan(basePackages = "com.github.boyvita.services.catalog")
@EnableJpaRepositories(basePackages = "com.github.boyvita.services.catalog.repo")
@EntityScan(basePackages = "com.github.boyvita.services.catalog.model")
public class CatalogController {

    private ProductRepository productRepository;
    private ItemRepository itemRepository;

    @Autowired
    private RabbitMQReceiver rabbitMQReceiver;

    @Autowired
    public CatalogController(ProductRepository productRepository, ItemRepository itemRepository) {
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
    }

    /**
     This is a test method, only for demonstration purposes
     */
//    @GetMapping("test")
//    public void testmq(){
//        Product product2 = new Product("p1", 2.2, "perfect p1");
//        Item item1 = new Item(product2);
//        rabbitMQSender.send(item1);
//        System.out.println("Item send to mq =" + item1);
//    }

    @GetMapping("/start")
    public void start() {
        Product product1 = new Product("p1", 1.1, "perfect p1");
        Product product2 = new Product("p1", 2.2, "perfect p1");
        productRepository.save(product1);
        productRepository.save(product2);


        Item item1 = new Item(product1);
        Item item2 = new Item(product1);
        Item item3 = new Item(product2);
        item1.setOrderId(new Long(1));
        item2.setOrderId(new Long(1));
        item3.setOrderId(new Long(1));
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
    }



    @GetMapping("/product")
    public List<Product> listProduct() {
        return productRepository.findAll();
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") Product product) {
        return product;
    }

    @PostMapping("/product")
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/product")
    public Product updateProduct(@RequestBody Product product) throws NoEntityException {
        Long id = product.getId();
        Product productFromDb = productRepository.findById(id).orElseThrow(() -> new NoEntityException(id));
        BeanUtils.copyProperties(product, productFromDb, "id");
        return productRepository.save(productFromDb);

    }

    @DeleteMapping("/product/{id}")
    public Product deleteProduct(@PathVariable("id") Product product) {
        productRepository.delete(product);
        return product;
    }

    @GetMapping("/item")
    public List<Item> listItem() {
        return itemRepository.findAll();
    }

    @GetMapping("/item/{id}")
    public Item getItem(@PathVariable("id") Item item) {
        return item;
    }

    @PostMapping("/item")
    public Item addItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    @PutMapping("/item")
    public Item updateItem(@RequestBody Item item) throws NoEntityException {
        Long id = item.getId();
        Item itemFromDb = itemRepository.findById(id).orElseThrow(() -> new NoEntityException(id));
        BeanUtils.copyProperties(item, itemFromDb, "id");
        return itemRepository.save(itemFromDb);

    }

    @DeleteMapping("/item/{id}")
    public Item deleteItem(@PathVariable("id") Item item) {
        itemRepository.delete(item);
        return item;
    }

}
