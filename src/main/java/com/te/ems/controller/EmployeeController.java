package com.te.ems.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.te.ems.dto.AddressDTO;
import com.te.ems.dto.BankAccountDTO;
import com.te.ems.dto.EmployeeBasicDTO;
import com.te.ems.dto.EmployeeRegDTO;
import com.te.ems.dto.TechnologyDTO;
import com.te.ems.entity.AddressType;
import com.te.ems.entity.BankAccount;
import com.te.ems.entity.Employee;
import com.te.ems.entity.Technology;
import com.te.ems.response.SuccessResponse;
import com.te.ems.service.EmployeeService;

@RequestMapping(path = "api/employees")
@RestController
public record EmployeeController(EmployeeService employeeService) {
	
	@GetMapping(path = "dummy")
	public EmployeeRegDTO dummy() {
		return EmployeeRegDTO.builder()
				.employeeEmailId("abc@gmail.com")
				.employeeName("A")
				.bankAccount(BankAccountDTO.builder().bankAccountNumber("12345678").bankIFSC("IFSC01").build())
				.addresses(List.of(AddressDTO.builder().addressCity("city01").addressState("state01").addressType(AddressType.PERMANENT).build()))
				.technologies(List.of(TechnologyDTO.builder().technologyName("Java").build()))
				.build();
	}

	@GetMapping(path = "employee")
	public ResponseEntity<SuccessResponse> getEmployee(@RequestParam(name = "eid") Integer employeeID) {

//		 EmployeeBasicDTO employee = employeeService.getEmployee(employeeID);
		
		return ResponseEntity.<SuccessResponse>ofNullable(
				SuccessResponse.builder()
				.messsage("Employee Data Provided")
				.status(HttpStatus.OK)
//				.data(employee)
				.data(employeeService.getEmployee(employeeID))
				.timestamp(LocalDateTime.now())
				.build());
	}
	
	@GetMapping(path = "")
	public ResponseEntity<SuccessResponse> getEmployees() {
		
			List<EmployeeBasicDTO> employeesBasicDTOs =  employeeService.getEmployees();
		
		return ResponseEntity.<SuccessResponse>ofNullable(
				SuccessResponse.builder()
				.messsage("Employees Data Provided")
				.status(HttpStatus.OK)
				.data(employeesBasicDTOs)
				.timestamp(LocalDateTime.now())
				.build());
	}
	
	@PostMapping(path = "")
	public ResponseEntity<SuccessResponse> saveEmployee(@RequestBody EmployeeRegDTO employeeRegDTO) {

		Integer employeeId = employeeService.saveEmployee(employeeRegDTO);

		return ResponseEntity.<SuccessResponse>ofNullable(
				SuccessResponse.builder()
						.messsage("Employee Data Saved")
						.status(HttpStatus.OK)
						.data(employeeRegDTO)
						.timestamp(LocalDateTime.now())
						.build());
	}
	
	@PutMapping(path = "employee")
	public ResponseEntity<SuccessResponse> updateEmployee(
			@RequestParam(name = "eid") Integer employeeId,
			@RequestBody EmployeeRegDTO employeeRegDTO) {

		return ResponseEntity.<SuccessResponse>ofNullable(
				SuccessResponse.builder()
				.messsage("Employees Updated")
				.status(HttpStatus.OK)
				.data(employeeService.updateEmployee(employeeId,employeeRegDTO))
				.timestamp(LocalDateTime.now())
				.build());
	}

	@DeleteMapping(path = "")
	public  ResponseEntity<SuccessResponse> deleteEmployee(@RequestParam(name = "eid") Integer employeeId) {
		Integer empId = employeeService.deleteEmployee(employeeId);
		return ResponseEntity.<SuccessResponse>ofNullable(
				SuccessResponse.builder()
						.messsage("Employees Deleted")
						.status(HttpStatus.OK)
						.data(employeeId)
						.timestamp(LocalDateTime.now())
						.build());
	}

	@GetMapping(path = "address")
	public ResponseEntity<SuccessResponse> getEmployeeByCity(@RequestParam(name = "city") String city) {
		List<EmployeeBasicDTO>  employee = employeeService.getEmployeeCity(city);

		return ResponseEntity.<SuccessResponse>ofNullable(
				SuccessResponse.builder()
						.messsage("Employee details with address: + " + city)
						.status(HttpStatus.OK)
						.data(employee)
						.timestamp(LocalDateTime.now())
						.build());
	}

	@GetMapping(path = "technology")
	public ResponseEntity<SuccessResponse> getEmployeeByTechnologyName(@RequestParam(name = "technologyName") String technologyName) {

		List<EmployeeBasicDTO>  employee = employeeService.getEmployeeByTechnologyName(technologyName);

		return ResponseEntity.<SuccessResponse>ofNullable(
				SuccessResponse.builder()
						.messsage("Employee Details with technology : + " + technologyName)
						.status(HttpStatus.OK)
						.data(employee)
						.timestamp(LocalDateTime.now())
						.build());
	}

	@GetMapping(path = "bankifsc")
	public ResponseEntity<SuccessResponse> getEmployeeByBankIFSC(@RequestParam(name = "bankIFSC") String bankIFSC) {

		List<EmployeeBasicDTO>  employee = employeeService.getEmployeeByBankIFSC(bankIFSC);

		return ResponseEntity.<SuccessResponse>ofNullable(
				SuccessResponse.builder()
						.messsage("Employee Details with BankIfsc : " + bankIFSC)
						.status(HttpStatus.OK)
						.data(employee)
						.timestamp(LocalDateTime.now())
						.build());
	}

}