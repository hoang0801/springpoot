package com.jmaster.io.shopservice.aop;


import com.jmaster.io.shopservice.entity.Category;
import com.jmaster.io.shopservice.model.CategoryDTO;
import com.jmaster.io.shopservice.repository.CategoryRepository;
import com.jmaster.io.shopservice.utils.CacheNames;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.List;

@Aspect
@Component
public class CategoryServiceAspect {
    @Autowired
    CacheManager cacheManager;

    @Autowired
    CategoryRepository categoryRepo;

    @Before("execution(* com.jmaster.io.shopservice.service.CategoryService.create(*))")
    public void create(JoinPoint joinPoint) {
        cacheManager.getCache(CacheNames.CACHE_CATEGORY_FIND).clear();
        cacheManager.getCache(CacheNames.CACHE_CATEGORY).clear();
    }

    @Before("execution(* com.jmaster.io.shopservice.service.CategoryService.update(*))")
    public void update(JoinPoint joinPoint) {
        CategoryDTO categoryDTO = (CategoryDTO) joinPoint.getArgs()[0];
        Category currentUser = categoryRepo.findById(categoryDTO.getId()).orElseThrow(NoResultException::new);

        cacheManager.getCache(CacheNames.CACHE_CATEGORY_FIND).clear();
        cacheManager.getCache(CacheNames.CACHE_CATEGORY).evict(currentUser.getId());
    }

    @Before("execution(* com.jmaster.io.shopservice.service.CategoryService.delete(*))")
    public void delete(JoinPoint joinPoint) {
        int id = (Integer) joinPoint.getArgs()[0];
        Category currentUser = categoryRepo.findById((long) id).orElseThrow(NoResultException::new);

        cacheManager.getCache(CacheNames.CACHE_CATEGORY_FIND).clear();
        cacheManager.getCache(CacheNames.CACHE_CATEGORY).evict(currentUser.getId());
    }

    @Before("execution(* com.jmaster.io.shopservice.service.CategoryService.deleteAll(*))")
    public void deleteAll(JoinPoint joinPoint) {
        @SuppressWarnings("unchecked")
        List<Long> ids = (List<Long>) joinPoint.getArgs()[0];

        List<Category> categories = categoryRepo.findAllById(ids);

        // clear cache
        categories.forEach(category -> {
            cacheManager.getCache(CacheNames.CACHE_CATEGORY).evict(category.getId());
        });
        cacheManager.getCache(CacheNames.CACHE_CATEGORY_FIND).clear();
    }
}
