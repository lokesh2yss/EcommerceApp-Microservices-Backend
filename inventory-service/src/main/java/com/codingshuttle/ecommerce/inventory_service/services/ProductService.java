package com.codingshuttle.ecommerce.inventory_service.services;

import com.codingshuttle.ecommerce.inventory_service.dtos.ProductDTO;
import com.codingshuttle.ecommerce.inventory_service.entities.Product;
import com.codingshuttle.ecommerce.inventory_service.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

}
