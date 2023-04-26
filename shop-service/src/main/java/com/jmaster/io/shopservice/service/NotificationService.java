package com.jmaster.io.shopservice.service;

import com.jmaster.io.shopservice.entity.Notification;
import com.jmaster.io.shopservice.entity.User;
import com.jmaster.io.shopservice.model.NotificationDTO;
import com.jmaster.io.shopservice.model.ResponseDTO;
import com.jmaster.io.shopservice.model.SearchDTO;
import com.jmaster.io.shopservice.repository.NotificationRepository;
import com.jmaster.io.shopservice.repository.UserRepository;
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

public interface NotificationService {
    void create(NotificationDTO notificationDTO);

    void update(NotificationDTO notificationDTO);

    void delete(long id);

    void deleteAll(List<Long> ids);

    NotificationDTO get(long id);


    ResponseDTO<List<NotificationDTO>> find(SearchDTO searchDTO);

}

@Service
class NotificationServiceImpl implements NotificationService {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public void create(NotificationDTO notificationDTO) {
        ModelMapper mapper = new ModelMapper();
        Notification notification = mapper.map(notificationDTO, Notification.class);
        User user= userRepository.findById(notificationDTO.getUser().getId()).orElseThrow(NoResultException::new);
        notification.setUser(user);
        notificationRepository.save(notification);
        notificationDTO.setId(notification.getId());
    }

    @Override
    @Transactional
    public void update(NotificationDTO notificationDTO) {
        ModelMapper mapper = new ModelMapper();
        mapper.createTypeMap(NotificationDTO.class, Notification.class)
                .setProvider(p -> notificationRepository.findById(notificationDTO.getId()).orElseThrow(NoResultException::new));
        System.out.println(notificationDTO.toString());
        Notification notification = mapper.map(notificationDTO, Notification.class);
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void delete(long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll(List<Long> ids) {
        notificationRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public NotificationDTO get(long id) {
        return notificationRepository.findById(id).map(notification -> convert(notification)).orElseThrow(NoResultException::new);
    }



    @Override
    public ResponseDTO<List<NotificationDTO>> find(SearchDTO searchDTO) {
        List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
                .map(order -> {
                    if (order.getOrder().equals(SearchDTO.ASC))
                        return Sort.Order.asc(order.getProperty());

                    return Sort.Order.desc(order.getProperty());
                }).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

        Page<Notification> page = notificationRepository.find(searchDTO.getValue(), pageable);

        ResponseDTO<List<NotificationDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
        responseDTO.setData(page.get().map(category -> convert(category)).collect(Collectors.toList()));
        return responseDTO;
    }

    private NotificationDTO convert(Notification notification) {
        return new ModelMapper().map(notification, NotificationDTO.class);
    }
}
