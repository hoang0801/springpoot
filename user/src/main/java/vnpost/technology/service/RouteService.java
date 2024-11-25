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
import vnpost.technology.dto.RouteDTO;
import vnpost.technology.dto.SearchDTO;
import vnpost.technology.dto.ShipperDTO;
import vnpost.technology.entity.Route;
import vnpost.technology.entity.Shipper;
import vnpost.technology.repo.RouteRepo;
import vnpost.technology.repo.ShipperRepo;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface RouteService {

        void create(RouteDTO routeDTO);

        void update(RouteDTO routeDTO);

        void delete(Integer id);

        ResponseDTO<List<RouteDTO>> find(SearchDTO searchDTO);
    }

    @Service
    class RouteServiceImpl implements RouteService {
        @Autowired
        RouteRepo routeRepo;

        @Override
        @Transactional
        public void create(RouteDTO routeDTO) {
            ModelMapper mapper = new ModelMapper();
            Route route = mapper.map(routeDTO, Route.class);
            routeRepo.save(route);
            routeDTO.setId(route.getId());
        }

        @Override
        @Transactional
        public void update(RouteDTO routeDTO) {
            Route route = routeRepo.findById(routeDTO.getId()).orElseThrow(NoResultException::new);
            route.setRouteDetails(routeDTO.getRouteDetails());
            routeRepo.save(route);
        }

        @Override
        @Transactional
        public void delete(Integer id) {

            routeRepo.deleteById(id);
        }

        @Override
        public ResponseDTO<List<RouteDTO>> find(SearchDTO searchDTO) {
            List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
                    .map(order -> {
                        if (order.getOrder().equals(SearchDTO.ASC))
                            return Sort.Order.asc(order.getProperty());

                        return Sort.Order.desc(order.getProperty());
                    }).collect(Collectors.toList());

            Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

            Page<Route> page = routeRepo.searchByRoute(searchDTO.getValue(), pageable);

            @SuppressWarnings("unchecked")
            ResponseDTO<List<RouteDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
            responseDTO.setData(page.get().map(route -> convert(route)).collect(Collectors.toList()));
            return responseDTO;
        }
        private RouteDTO convert(Route route) {
            return new ModelMapper().map(route, RouteDTO.class);
        }
    }

