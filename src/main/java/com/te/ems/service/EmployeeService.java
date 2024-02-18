package com.te.ems.service;

import java.util.List;

import com.te.ems.dto.EmployeeBasicDTO;
import com.te.ems.dto.EmployeeRegDTO;

public interface EmployeeService {

	EmployeeBasicDTO getEmployee(Integer employeeID);

	List<EmployeeBasicDTO> getEmployees();

	Integer saveEmployee(EmployeeRegDTO employeeRegDTO);

	boolean updateEmployee(Integer employeeId, EmployeeRegDTO employeeRegDTO);

	Integer deleteEmployee(Integer employeeId);

	List<EmployeeBasicDTO> getEmployeeCity(String city);

	List<EmployeeBasicDTO> getEmployeeByTechnologyName(String technologyName);

	List<EmployeeBasicDTO> getEmployeeByBankIFSC(String bankIFSC);
}
