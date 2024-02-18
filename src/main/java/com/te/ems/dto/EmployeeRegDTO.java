package com.te.ems.dto;

import java.util.List;

import com.te.ems.entity.BankAccount;

import lombok.Builder;

@Builder
public record EmployeeRegDTO(
		String employeeName,
		String employeeEmailId,
		BankAccountDTO bankAccount,
		List<AddressDTO> addresses,
		List<TechnologyDTO> technologies) {

}
