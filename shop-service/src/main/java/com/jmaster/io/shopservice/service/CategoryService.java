package com.jmaster.io.shopservice.service;

import com.jmaster.io.shopservice.entity.Category;
import com.jmaster.io.shopservice.model.CategoryDTO;
import com.jmaster.io.shopservice.model.ResponseDTO;
import com.jmaster.io.shopservice.model.SearchDTO;
import com.jmaster.io.shopservice.repository.CategoryRepository;
import com.jmaster.io.shopservice.utils.CacheNames;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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



public interface CategoryService {
    void create(CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);

    void delete(long id);

    void deleteAll(List<Long> ids);

    CategoryDTO get(long id);


    ResponseDTO<List<CategoryDTO>> find(SearchDTO searchDTO);

}

@Service
class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    @Transactional
    @CacheEvict(value = CacheNames.CACHE_CATEGORY_FIND,allEntries = true)
    public void create(CategoryDTO categoryDTO) {
        ModelMapper mapper = new ModelMapper();
        Category category = mapper.map(categoryDTO, Category.class);
        categoryRepository.save(category);
        categoryDTO.setId(category.getId());
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheNames.CACHE_CATEGORY_FIND,allEntries = true)
    public void update(CategoryDTO categoryDTO) {
        ModelMapper mapper = new ModelMapper();
        mapper.createTypeMap(CategoryDTO.class, Category.class)
                .setProvider(p -> categoryRepository.findById(categoryDTO.getId()).orElseThrow(NoResultException::new));
        System.out.println(categoryDTO.toString());
        Category category = mapper.map(categoryDTO, Category.class);
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheNames.CACHE_CATEGORY_FIND,allEntries = true)
    public void delete(long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheNames.CACHE_CATEGORY_FIND,allEntries = true)
    public void deleteAll(List<Long> ids) {
        categoryRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    @Cacheable(CacheNames.CACHE_CATEGORY)
    public CategoryDTO get(long id) {
        return categoryRepository.findById(id).map(category -> convert(category)).orElseThrow(NoResultException::new);
    }



    @Override
    @Cacheable(cacheNames = CacheNames.CACHE_CATEGORY_FIND, unless = "#result.totalElements == 0", key = "#searchDTO.toString()")
    public ResponseDTO<List<CategoryDTO>> find(SearchDTO searchDTO) {
        List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
                .map(order -> {
                    if (order.getOrder().equals(SearchDTO.ASC))
                        return Sort.Order.asc(order.getProperty());

                    return Sort.Order.desc(order.getProperty());
                }).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

        Page<Category> page = categoryRepository.find(searchDTO.getValue(), pageable);

        ResponseDTO<List<CategoryDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
        responseDTO.setData(page.get().map(category -> convert(category)).collect(Collectors.toList()));
        return responseDTO;
    }

    private CategoryDTO convert(Category category) {
        return new ModelMapper().map(category, CategoryDTO.class);
    }
}
