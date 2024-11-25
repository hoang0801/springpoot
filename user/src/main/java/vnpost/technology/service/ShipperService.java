package vnpost.technology.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vnpost.technology.dto.ResponseDTO;
import vnpost.technology.dto.SearchDTO;
import vnpost.technology.dto.ShipperDTO;
import vnpost.technology.entity.Shipper;
import vnpost.technology.repo.ShipperRepo;
import vnpost.technology.utils.ShipperStatus;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ShipperService {
 void create(ShipperDTO shipperDTO);

 void update(ShipperDTO shipperDTO);

 void delete(Integer id);

 ResponseDTO<List<ShipperDTO>> find(SearchDTO searchDTO);
}
@Service
 class ShipperServiceImpl implements ShipperService {
  @Autowired
  ShipperRepo shipperRepo;


  @Override
  @Transactional
  public void create(ShipperDTO shipperDTO) {
   ModelMapper mapper = new ModelMapper();
   Shipper shipper = mapper.map(shipperDTO, Shipper.class);
   if (shipper.getStatus() == null) {
    shipper.setStatus(ShipperStatus.AVAILABLE); // Đảm bảo giá trị mặc định nếu không được gán
   }
   shipperRepo.save(shipper);
   shipperDTO.setId(shipper.getId());
  }

  @Override
  @Transactional
  public void update(ShipperDTO shipperDTO) {
   Shipper shipper = shipperRepo.findById(shipperDTO.getId()).orElseThrow(NoResultException::new);
   shipper.setName(shipperDTO.getName());
   shipper.setPhone(shipperDTO.getPhone());
   shipper.setEmail(shipperDTO.getEmail());
   shipperRepo.save(shipper);
  }

  @Override
  @Transactional
  public void delete(Integer id) {

   shipperRepo.deleteById(id);
  }

  @Override
  public ResponseDTO<List<ShipperDTO>> find(SearchDTO searchDTO) {
   List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
           .map(order -> {
            if (order.getOrder().equals(SearchDTO.ASC))
             return Sort.Order.asc(order.getProperty());

            return Sort.Order.desc(order.getProperty());
           }).collect(Collectors.toList());

   Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

   Page<Shipper> page = shipperRepo.searchByName(searchDTO.getValue(), pageable);

   @SuppressWarnings("unchecked")
   ResponseDTO<List<ShipperDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
   responseDTO.setData(page.get().map(shipper -> convert(shipper)).collect(Collectors.toList()));
   return responseDTO;
  }
  private ShipperDTO convert(Shipper shipper) {
   return new ModelMapper().map(shipper, ShipperDTO.class);
  }
 }