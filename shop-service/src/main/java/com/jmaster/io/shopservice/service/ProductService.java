package com.jmaster.io.shopservice.service;

import com.jmaster.io.shopservice.entity.Product;
import com.jmaster.io.shopservice.model.ProductDTO;
import com.jmaster.io.shopservice.model.ResponseDTO;
import com.jmaster.io.shopservice.model.SearchDTO;
import com.jmaster.io.shopservice.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ProductService {
    void create(ProductDTO productDTO);

    void update(ProductDTO productDTO);

    void delete(long id);

    void deleteAll(List<Long> ids);

    ProductDTO get(long id);


    ResponseDTO<List<ProductDTO>> find(SearchDTO searchDTO);

}

@Service
class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    @Transactional
    public void create(ProductDTO productDTO) {
        ModelMapper mapper = new ModelMapper();
        Product product = mapper.map(productDTO, Product.class);
        productRepository.save(product);
        productDTO.setId(product.getId());
    }

    @Override
    @Transactional
    public void update(ProductDTO productDTO) {
        ModelMapper mapper = new ModelMapper();
        mapper.createTypeMap(ProductDTO.class, Product.class)
                .setProvider(p -> productRepository.findById(productDTO.getId()).orElseThrow(NoResultException::new));

        Product product = mapper.map(productDTO, Product.class);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void delete(long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll(List<Long> ids) {
        productRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public ProductDTO get(long id) {
        return productRepository.findById(id).map(product -> convert(product)).orElseThrow(NoResultException::new);
    }



    @Override
    public ResponseDTO<List<ProductDTO>> find(SearchDTO searchDTO) {
        List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
                .map(order -> {
                    if (order.getOrder().equals(SearchDTO.ASC))
                        return Sort.Order.asc(order.getProperty());

                    return Sort.Order.desc(order.getProperty());
                }).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

        Page<Product> page = productRepository.find(searchDTO.getValue(), pageable);

        ResponseDTO<List<ProductDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
        responseDTO.setData(page.get().map(product -> convert(product)).collect(Collectors.toList()));
        return responseDTO;
    }

    private ProductDTO convert(Product product) {
        return new ModelMapper().map(product, ProductDTO.class);
    }
}