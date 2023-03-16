package com.wdsystems.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.wdsystems.helpdesk.domain.enums.Profile;

@Entity
public class Customer extends Person {

	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "customer")
	private List<Order> orders = new ArrayList<>();

	public Customer() {
		super();
		addProfile(Profile.CUSTOMER);
	}

	public Customer(Integer id, String name, String cpf, String email, String password) {
		super(id, name, cpf, email, password);
		addProfile(Profile.CUSTOMER);
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
