package com.jmaster.io.shopservice.service;

import com.jmaster.io.shopservice.entity.Cart;
import com.jmaster.io.shopservice.entity.Category;
import com.jmaster.io.shopservice.model.CartDTO;
import com.jmaster.io.shopservice.model.CategoryDTO;
import com.jmaster.io.shopservice.model.ResponseDTO;
import com.jmaster.io.shopservice.model.SearchDTO;
import com.jmaster.io.shopservice.repository.CartRepository;
import com.jmaster.io.shopservice.repository.CategoryRepository;
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

public interface CartService {
    void create(CartDTO cartDTO);

    void update(CartDTO cartDTO);

    void delete(long id);

    void deleteAll(List<Long> ids);

    CartDTO get(long id);


    ResponseDTO<List<CartDTO>> find(SearchDTO searchDTO);

}

@Service
class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;

    @Override
    @Transactional
    public void create(CartDTO cartDTO) {
        ModelMapper mapper = new ModelMapper();
        Cart cart = mapper.map(cartDTO, Cart.class);
        cartRepository.save(cart);
        cartDTO.setId(cart.getId());
    }

    @Override
    @Transactional
    public void update(CartDTO cartDTO) {
        ModelMapper mapper = new ModelMapper();
        mapper.createTypeMap(CartDTO.class, Cart.class)
                .setProvider(p -> cartRepository.findById(cartDTO.getId()).orElseThrow(NoResultException::new));

        Cart cart = mapper.map(cartDTO, Cart.class);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void delete(long id) {
        cartRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll(List<Long> ids) {
        cartRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public CartDTO get(long id) {
        return cartRepository.findById(id).map(cart -> convert(cart)).orElseThrow(NoResultException::new);
    }



    @Override
    public ResponseDTO<List<CartDTO>> find(SearchDTO searchDTO) {
        List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
                .map(order -> {
                    if (order.getOrder().equals(SearchDTO.ASC))
                        return Sort.Order.asc(order.getProperty());

                    return Sort.Order.desc(order.getProperty());
                }).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

        Page<Cart> page = cartRepository.find(Long.parseLong(searchDTO.getValue()), pageable);

        ResponseDTO<List<CartDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
        responseDTO.setData(page.get().map(category -> convert(category)).collect(Collectors.toList()));
        return responseDTO;
    }

    private CartDTO convert(Cart cart) {
        return new ModelMapper().map(cart, CartDTO.class);
    }
}