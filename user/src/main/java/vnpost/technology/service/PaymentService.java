package vnpost.technology.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnpost.technology.dto.PaymentDTO;
import vnpost.technology.dto.ResponseDTO;
import vnpost.technology.dto.SearchDTO;
import vnpost.technology.entity.Payment;
import vnpost.technology.repo.PaymentRepo;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface PaymentService {

    void create(PaymentDTO paymentDTO);

    void delete(Integer id);

    ResponseDTO<List<PaymentDTO>> find(SearchDTO searchDTO);
}
@Service
class PaymentServiceImpl implements PaymentService{
    @Autowired
    PaymentRepo paymentRepo;

    @Override
    @Transactional
    public void create(PaymentDTO paymentDTO) {
        ModelMapper mapper = new ModelMapper();
        Payment payment = mapper.map(paymentDTO, Payment.class);
        paymentRepo.save(payment);
        paymentDTO.setId(payment.getId());
    }

    @Override
    @Transactional
    public void delete(Integer id) {

        paymentRepo.deleteById(id);
    }

    @Override
    public ResponseDTO<List<PaymentDTO>> find(SearchDTO searchDTO) {
        List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
                .map(order -> {
                    if (order.getOrder().equals(SearchDTO.ASC))
                        return Sort.Order.asc(order.getProperty());

                    return Sort.Order.desc(order.getProperty());
                }).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

        Page<Payment> page = paymentRepo.searchByStatus(searchDTO.getValue(), pageable);

        @SuppressWarnings("unchecked")
        ResponseDTO<List<PaymentDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
        responseDTO.setData(page.get().map(payment -> convert(payment)).collect(Collectors.toList()));
        return responseDTO;
    }
    private PaymentDTO convert(Payment payment) {
        return new ModelMapper().map(payment, PaymentDTO.class);
    }
}
