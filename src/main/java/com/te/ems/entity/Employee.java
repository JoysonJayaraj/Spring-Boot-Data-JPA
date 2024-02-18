package com.te.ems.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Employee {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer employeeId;
	
	private String  employeeName;
	
	@Column(unique = true)
	private String employeeEmailId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ba_fk")
	private BankAccount bankAccount;
	
	@OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
	private List<Address> addresses;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "emp_tech_map", 
		joinColumns = @JoinColumn(name = "emp_fk"),
		inverseJoinColumns = @JoinColumn(name = "tech_fk"))
	private List<Technology> technologies;

}
