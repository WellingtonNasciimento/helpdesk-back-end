package com.wdsystems.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.wdsystems.helpdesk.domain.enums.Profile;

@Entity
public class Technical extends Person {

	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "technical")
	private List<Order> orders = new ArrayList<>();

	public Technical() {
		super();
		addProfile(Profile.TECHNICAL);
	}

	public Technical(Integer id, String name, String cpf, String email, String password) {
		super(id, name, cpf, email, password);
		addProfile(Profile.TECHNICAL);
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
