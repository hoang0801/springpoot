package com.jmaster.io.shopservice.service;

import com.jmaster.io.shopservice.entity.Bill;
import com.jmaster.io.shopservice.entity.Cart;
import com.jmaster.io.shopservice.model.BillDTO;
import com.jmaster.io.shopservice.model.CartDTO;
import com.jmaster.io.shopservice.model.ResponseDTO;
import com.jmaster.io.shopservice.model.SearchDTO;
import com.jmaster.io.shopservice.repository.BillRepository;
import com.jmaster.io.shopservice.repository.CartRepository;
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

public interface BillService {
    void create(BillDTO billDTO);

    void update(BillDTO billDTO);

    void delete(long id);

    void deleteAll(List<Long> ids);

    BillDTO get(long id);


    ResponseDTO<List<BillDTO>> find(SearchDTO searchDTO);

}

@Service
class BillServiceImpl implements BillService {
    @Autowired
    BillRepository billRepository;

    @Override
    @Transactional
    @CacheEvict(value = CacheNames.CACHE_BILL_FIND,allEntries = true)
    public void create(BillDTO billDTO) {
        ModelMapper mapper = new ModelMapper();
        Bill bill = mapper.map(billDTO, Bill.class);
        billRepository.save(bill);
        billDTO.setId(bill.getId());
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheNames.CACHE_BILL_FIND,allEntries = true)
    public void update(BillDTO billDTO) {
        ModelMapper mapper = new ModelMapper();
        mapper.createTypeMap(BillDTO.class, Bill.class)
                .setProvider(p -> billRepository.findById(billDTO.getId()).orElseThrow(NoResultException::new));

        Bill bill = mapper.map(billDTO, Bill.class);
        billRepository.save(bill);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheNames.CACHE_BILL_FIND,allEntries = true)
    public void delete(long id) {
        billRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheNames.CACHE_BILL_FIND,allEntries = true)
    public void deleteAll(List<Long> ids) {
        billRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    @Cacheable(CacheNames.CACHE_BILL)
    public BillDTO get(long id) {
        return billRepository.findById(id).map(bill -> convert(bill)).orElseThrow(NoResultException::new);
    }



    @Override
    @Cacheable(cacheNames = CacheNames.CACHE_BILL_FIND, unless = "#result.totalElements == 0", key = "#searchDTO.toString()")
    public ResponseDTO<List<BillDTO>> find(SearchDTO searchDTO) {
        List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
                .map(order -> {
                    if (order.getOrder().equals(SearchDTO.ASC))
                        return Sort.Order.asc(order.getProperty());

                    return Sort.Order.desc(order.getProperty());
                }).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

        Page<Bill> page = billRepository.find(searchDTO.getValue(), pageable);

        ResponseDTO<List<BillDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
        responseDTO.setData(page.get().map(bill -> convert(bill)).collect(Collectors.toList()));
        return responseDTO;
    }

    private BillDTO convert(Bill bill) {
        return new ModelMapper().map(bill, BillDTO.class);
    }
}
