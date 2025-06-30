package com.smart.hotel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class OtherServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerEmail;

    @ManyToOne
    private OtherService service;

    private LocalDateTime addedTime;

    public OtherServiceOrder() {}

    public OtherServiceOrder(String customerEmail, OtherService service, LocalDateTime addedTime) {
        this.setCustomerEmail(customerEmail);
        this.service = service;
        this.setAddedTime(addedTime);
    }

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public LocalDateTime getAddedTime() {
		return addedTime;
	}

	public void setAddedTime(LocalDateTime addedTime) {
		this.addedTime = addedTime;
	}

    public OtherService getService() {
        return service;
    }

    public void setService(OtherService service) {
        this.service = service;
    }
}
