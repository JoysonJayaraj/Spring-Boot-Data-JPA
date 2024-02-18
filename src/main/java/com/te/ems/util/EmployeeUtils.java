package com.te.ems.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.te.ems.dto.AddressDTO;
import com.te.ems.dto.EmployeeBasicDTO;
import com.te.ems.dto.EmployeeRegDTO;
import com.te.ems.dto.TechnologyDTO;
import com.te.ems.entity.Address;
import com.te.ems.entity.AddressType;
import com.te.ems.entity.BankAccount;
import com.te.ems.entity.Employee;
import com.te.ems.entity.Technology;
import com.te.ems.exception.AddressTypeNotProvidedException;

public class EmployeeUtils {

	public static EmployeeBasicDTO convertEntityToDTO(Employee employee) {

		EmployeeBasicDTO employeeBasicDTO = EmployeeBasicDTO.builder()
				.employeeId(employee.getEmployeeId())
				.employeeName(employee.getEmployeeName())
				.employeeEmailId(employee.getEmployeeEmailId())
				.build();
		
		List<AddressDTO> addressDTOs = new ArrayList<>();
		for(Address address : employee.getAddresses()) {
			addressDTOs.add(AddressDTO.builder()
					.addressCity(address.getAddressCity())
					.addressState(address.getAddressState())
					.addressType(address.getAddressType())
					.build());
			
		}
		employeeBasicDTO.setAddresses(addressDTOs);

		List<TechnologyDTO> technologyDTOs = new ArrayList<>();
		for(Technology technology : employee.getTechnologies()) {
			technologyDTOs.add(TechnologyDTO.builder()
					.technologyName(technology.getTechnologyName())
					.build());
		}
		employeeBasicDTO.setTechnologies(technologyDTOs);

		return employeeBasicDTO;
	}

	public static EmployeeBasicDTO convertEntityToDTO_(Employee employee) {

		return EmployeeBasicDTO.builder()
				.employeeId(employee.getEmployeeId())
				.employeeName(employee.getEmployeeName())
				.employeeEmailId(employee.getEmployeeEmailId())
				.addresses(employee.getAddresses().stream()
						.map(a -> AddressDTO.builder()
								.addressCity(a.getAddressCity())
								.addressState(a.getAddressState())
								.addressType(a.getAddressType())
								.build()).collect(Collectors.toList()))
				.technologies(employee.getTechnologies().stream()
						.map(t -> TechnologyDTO.builder()
								.technologyName(t.getTechnologyName())
								.build()).toList())

		.build();
	}
	
	public static Employee convertDTOTOEntity(EmployeeRegDTO employeeRegDTO) {

		Employee employee = Employee.builder()
				.employeeEmailId(employeeRegDTO.employeeEmailId())
				.employeeName(employeeRegDTO.employeeName())
//				.bankAccount(bankAccount)
//				.addresses(addresses)
				.technologies(new ArrayList<>())
//		.technologies(technologies)
				.build();

		BankAccount bankAccount = BankAccount.builder()
				.bankAccountNumber(employeeRegDTO.bankAccount().bankAccountNumber())
				.bankIFSC(employeeRegDTO.bankAccount().bankIFSC())
				.employee(employee)
				.build();

//		List<Address> addresses = employeeRegDTO.addresses().stream()                                         1. before
//		.map(a -> Address.builder()
//				.addressCity(a.getAddressCity())
//				.addressState(a.getAddressState())
//				.addressType(a.getAddressType())
//				.build()).collect(Collectors.toList());
		
		List<Address> addresses = employeeRegDTO.addresses().stream().map(a -> {
			if (Objects.isNull(a.getAddressType())) {
				throw new AddressTypeNotProvidedException("Address type for the address/addresses are not provided");
			}
			return Address.builder()
					.addressCity(a.getAddressCity())
					.addressState(a.getAddressState())
					.addressType(a.getAddressType())
					.employee(employee)
					.build();
		}).collect(Collectors.toList());
				
		
//		List<Technology> technologies = employeeRegDTO.technologies().stream()
//		.map(t -> Technology.builder()
//				.technologyName(t.getTechnologyName())
//				.employees(new ArrayList<>()) // very important
//				.build()).toList();


//		bankAccount.setEmployee(employee); not required already set in line BankAccount.builder.employee(employee).build();
		employee.setBankAccount(bankAccount);
		employee.setAddresses(addresses);
		addresses.forEach(address -> address.setEmployee(employee));
//		technologies.forEach(technology -> technology.getEmployees().add(employee));
		
		return employee;
	}

	public static void updateFields(Employee employee, EmployeeRegDTO employeeRegDTO) {

		employee.setEmployeeEmailId(employeeRegDTO.employeeEmailId());
		employee.setEmployeeName(employeeRegDTO.employeeName());
		employee.setBankAccount(BankAccount.builder()
				.bankAccountNumber(employeeRegDTO.bankAccount().bankAccountNumber())
				.bankIFSC(employeeRegDTO.bankAccount().bankIFSC())
				.build());
		employee.setAddresses(employeeRegDTO.addresses().stream().map(a -> Address.builder()
				.addressCity(a.getAddressCity())
				.addressState(a.getAddressState())
				.addressType(a.getAddressType())
				.build()).collect(Collectors.toList()));

		// changes done by me
		 employee.setTechnologies(new ArrayList<>());
	}
}
