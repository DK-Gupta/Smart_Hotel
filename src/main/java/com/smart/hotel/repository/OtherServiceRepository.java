package com.smart.hotel.repository;

import com.smart.hotel.model.OtherService;
import com.smart.hotel.model.OtherServiceOrder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherServiceRepository extends JpaRepository<OtherService, Long> {
	public interface OtherServiceOrderRepository extends JpaRepository<OtherServiceOrder, Long> {
	    List<OtherServiceOrder> findByCustomerEmail(String email);
	}

}
