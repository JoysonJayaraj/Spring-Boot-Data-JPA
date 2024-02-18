package com.te.ems.dto;

import java.util.List;

import com.te.ems.entity.Address;
import com.te.ems.entity.BankAccount;
import com.te.ems.entity.Technology;

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
public class EmployeeBasicDTO {

	private Integer employeeId;

	private String employeeName;
	
	private String employeeEmailId;
	
	private List<AddressDTO> addresses;
	
	private List<TechnologyDTO> technologies;
	

}
