package com.codingshuttle.ecommerce.inventory_service.services;

import com.codingshuttle.ecommerce.inventory_service.dtos.OrderRequestDTO;
import com.codingshuttle.ecommerce.inventory_service.dtos.OrderRequestItemDTO;
import com.codingshuttle.ecommerce.inventory_service.dtos.ProductDTO;
import com.codingshuttle.ecommerce.inventory_service.entities.Product;
import com.codingshuttle.ecommerce.inventory_service.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDTO> getAllInventory() {
        log.info("Fetching all items from inventory");
        List<Product> inventories = productRepository.findAll();

        return inventories
                .stream()
                .map((element) -> modelMapper.map(element, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long productId) {
        log.info("fetching product for id: {}", productId);
        Optional<Product> inventory = productRepository.findById(productId);
        return inventory.map((element) -> modelMapper.map(element, ProductDTO.class)).orElseThrow(() ->
                new RuntimeException("Inventory not found with id: "+productId));
    }

    @Transactional
    public BigDecimal reduceStocks(OrderRequestDTO orderRequestDTO) {
        log.info("Reducing the stocks");
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(OrderRequestItemDTO itemDTO: orderRequestDTO.getItems()) {
            Long productId = itemDTO.getProductId();
            int quantity = itemDTO.getQuantity();
            Product product = productRepository.findById(productId).orElseThrow(() ->
                    new RuntimeException("Product not found with id: "+productId));

            if(product.getStock() < quantity) {
                throw new RuntimeException("Insufficient stock for product with id: "+productId);
            }
            product.setStock(product.getStock()-quantity);
            productRepository.save(product);
            BigDecimal curItemPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            totalPrice = totalPrice.add(curItemPrice);

        }
        return totalPrice;
    }

    @Transactional
    public boolean restockForCancelledOrder(OrderRequestDTO orderRequestDTO) {
        log.info("Restocking the order items for the cancelled order");
        for(OrderRequestItemDTO itemDTO: orderRequestDTO.getItems()) {
            Long productId = itemDTO.getProductId();
            int quantity = itemDTO.getQuantity();
            Product product = productRepository.findById(productId).orElseThrow(() ->
                    new RuntimeException("Product not found with id: "+productId));

            if(quantity < 0) {
                throw new RuntimeException("Quantity cannot be a negative number for product with id:  "+productId);
            }
            product.setStock(product.getStock()+quantity);
            productRepository.save(product);

        }
        return true;
    }
}
