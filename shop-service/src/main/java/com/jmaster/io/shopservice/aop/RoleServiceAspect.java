//package com.jmaster.io.shopservice.aop;
//
//
//import com.jmaster.io.shopservice.entity.Role;
//import com.jmaster.io.shopservice.model.RoleDTO;
//import com.jmaster.io.shopservice.repository.RoleRepository;
//import com.jmaster.io.shopservice.utils.CacheNames;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.CacheManager;
//import org.springframework.stereotype.Component;
//
//import javax.persistence.NoResultException;
//import java.util.List;
//
//@Aspect
//@Component
//public class RoleServiceAspect {
//    @Autowired
//    CacheManager cacheManager;
//    @Autowired
//    RoleRepository roleRepository;
//
//    @Before("execution(* com.jmaster.io.shopservice.service.UserRoleService.create(*))")
//    public void create(JoinPoint joinPoint) {
//        cacheManager.getCache(CacheNames.CACHE_USER_ROLE_FIND).clear();
//        cacheManager.getCache(CacheNames.CACHE_USER_ROLE).clear();
//    }
//
//    @Before("execution(* com.jmaster.io.shopservice.service.UserRoleService.update(*))")
//    public void update(JoinPoint joinPoint) {
//        RoleDTO userRoleDTO = (RoleDTO) joinPoint.getArgs()[0];
//        Role currentUser = roleRepository.findById(userRoleDTO.getId()).orElseThrow(NoResultException::new);
//
//        cacheManager.getCache(CacheNames.CACHE_USER_ROLE_FIND).clear();
//        cacheManager.getCache(CacheNames.CACHE_USER_ROLE).evict(currentUser.getId());
//    }
//
//    @Before("execution(* com.jmaster.io.blogservice.service.UserRoleService.delete(*))")
//    public void delete(JoinPoint joinPoint) {
//        long id = (Integer) joinPoint.getArgs()[0];
//        Role currentUser = roleRepository.findById(id).orElseThrow(NoResultException::new);
//
//        cacheManager.getCache(CacheNames.CACHE_USER_ROLE_FIND).clear();
//        cacheManager.getCache(CacheNames.CACHE_USER_ROLE).evict(currentUser.getId());
//    }
//
//    @Before("execution(* com.jmaster.io.shopservice.service.deleteByUserId(*))")
//    public void deleteByUserId(JoinPoint joinPoint) {
//        long id = (Integer) joinPoint.getArgs()[0];
//        Role currentUser = roleRepository.findById(id).orElseThrow(NoResultException::new);
//
//        cacheManager.getCache(CacheNames.CACHE_USER_ROLE_FIND).clear();
//        cacheManager.getCache(CacheNames.CACHE_USER_ROLE).evict(currentUser.getId());
//    }
//
//    @Before("execution(* com.jmaster.io.shopservice.service.UserRoleService.deleteAll(*))")
//    public void deleteAll(JoinPoint joinPoint) {
//        @SuppressWarnings("unchecked")
//        List<Long> ids = (List<Long>) joinPoint.getArgs()[0];
//
//        List<Role> userRoles = roleRepository.findAllById(ids);
//
//        // clear cache
//        userRoles.forEach(userRole -> {
//            cacheManager.getCache(CacheNames.CACHE_USER_ROLE).evict(userRole.getId());
//        });
//        cacheManager.getCache(CacheNames.CACHE_USER_ROLE_FIND).clear();
//    }
//}
