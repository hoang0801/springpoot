package com.jmaster.io.shopservice.aop;

import com.jmaster.io.shopservice.entity.User;
import com.jmaster.io.shopservice.model.UserDTO;
import com.jmaster.io.shopservice.repository.UserRepository;
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
public class UserServiceAspect {
    @Autowired
    CacheManager cacheManager;

    @Autowired
    UserRepository userRepo;

    @Before("execution(* com.jmaster.io.shopservice.service.UserService.create(*))")
    public void create(JoinPoint joinPoint) {
        cacheManager.getCache(CacheNames.CACHE_USER_FIND).clear();
        cacheManager.getCache(CacheNames.CACHE_USER).clear();
    }

    @Before("execution(* com.jmaster.io.shopservice.service.UserService.update(*))")
    public void update(JoinPoint joinPoint) {
        UserDTO userDTO = (UserDTO) joinPoint.getArgs()[0];
        User currentUser = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);

        cacheManager.getCache(CacheNames.CACHE_USER_FIND).clear();
        cacheManager.getCache(CacheNames.CACHE_USER).evict(currentUser.getId());
    }

    @Before("execution(* com.jmaster.io.shopservice.service.UserService.delete(*))")
    public void delete(JoinPoint joinPoint) {
        long id = (Integer) joinPoint.getArgs()[0];
        User currentUser = userRepo.findById(id).orElseThrow(NoResultException::new);

        cacheManager.getCache(CacheNames.CACHE_USER_FIND).clear();
        cacheManager.getCache(CacheNames.CACHE_USER).evict(currentUser.getId());
    }

    @Before("execution(* com.jmaster.io.shopservice.service.UserService.deleteAll(*))")
    public void deleteAll(JoinPoint joinPoint) {
        @SuppressWarnings("unchecked")
        List<Long> ids = (List<Long>) joinPoint.getArgs()[0];

        List<User> users = userRepo.findAllById(ids);

        // clear cache
        users.forEach(user -> {
            cacheManager.getCache(CacheNames.CACHE_USER).evict(user.getId());
        });
        cacheManager.getCache(CacheNames.CACHE_USER_FIND).clear();
    }
}
