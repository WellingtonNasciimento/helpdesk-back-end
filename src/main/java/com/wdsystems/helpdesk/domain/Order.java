package com.wdsystems.helpdesk.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wdsystems.helpdesk.domain.enums.Priority;
import com.wdsystems.helpdesk.domain.enums.Status;

@Entity
public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private LocalDate orderDate = LocalDate.now();
	@JsonFormat(pattern = "yyyy/MM/dd")
	private LocalDate closeDate;
	
	private Priority priority;
	private Status status;
	private String title;
	private String comments;
	
	@ManyToOne
	@JoinColumn(name = "technical_id")
	private Technical technical;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	public Order() {
		super();
	}

	public Order(Integer id, Priority priority, Status status, String title, String comments, Technical technical,
			Customer customer) {
		super();
		this.id = id;
		this.priority = priority;
		this.status = status;
		this.title = title;
		this.comments = comments;
		this.technical = technical;
		this.customer = customer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDate getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(LocalDate closeDate) {
		this.closeDate = closeDate;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Technical getTechnical() {
		return technical;
	}

	public void setTechnical(Technical technical) {
		this.technical = technical;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(id, other.id);
	}
}
