package com.te.ems.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.te.ems.entity.Address;
import com.te.ems.entity.BankAccount;
import com.te.ems.repository.AddressRepository;
import org.springframework.stereotype.Service;

import static com.te.ems.constant.EmployeeConstant.EMPLOYEE_ID_NOT_FOUND;

import com.te.ems.dto.EmployeeBasicDTO;
import com.te.ems.dto.EmployeeRegDTO;
import com.te.ems.entity.Employee;
import com.te.ems.entity.Technology;
import com.te.ems.exception.EmployeeNotFoundException;
import com.te.ems.repository.EmployeeRepository;
import com.te.ems.repository.TechnologyRepository;
import com.te.ems.util.EmployeeUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TechnologyRepository technologyRepository;
    private final AddressRepository addressRepository;

    @Override
    public EmployeeBasicDTO getEmployee(Integer employeeID) {
//		Optional<Employee> optional = employeeRepository.findById(employeeID);
//		if(optional.isPresent()) {
//			return EmployeeUtils.convertEntityToDTO(optional.get());
//		}
//		return null;

//		-----------------------------------------------------------------------------------

        Employee employee = employeeRepository.findById(employeeID)
                .orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_ID_NOT_FOUND));
        // takes supplier

        EmployeeBasicDTO convertEntityToDTO_ = EmployeeUtils.convertEntityToDTO_(employee);
        return convertEntityToDTO_;
    }

    @Override
    public List<EmployeeBasicDTO> getEmployees() {

        List<Employee> all = employeeRepository.findAll();
//		return all.stream().map(e -> {
//			return EmployeeUtils.convertEntityToDTO_(e);
//		}).toList();

        return all.stream().map(e -> EmployeeUtils.convertEntityToDTO_(e)).toList();
    }

    @Override
    public Integer saveEmployee(EmployeeRegDTO employeeRegDTO) {
        Employee employee = EmployeeUtils.convertDTOTOEntity(employeeRegDTO);
        employeeRegDTO.technologies().stream().forEach(technologyDTO -> {
            Optional<Technology> optionalTechnology = technologyRepository.findById(technologyDTO.getTechnologyName());
            if (optionalTechnology.isPresent()) {
                Technology t = optionalTechnology.get();
                employee.getTechnologies().add(t);
                t.getEmployees().add(employee);
            } else {
                Technology t = technologyRepository.save(
                        Technology.builder().technologyName(technologyDTO.getTechnologyName()).build()
                );
                employee.getTechnologies().add(t);
                t.getEmployees().add(employee);

                // other way
//				technologyRepository.save(Technology.builder().technologyName(technologyDTO.getTechnologyName()).build());
//				Optional<Technology> optionalTechnology_ = technologyRepository.findById(technologyDTO.getTechnologyName());
//				if (optionalTechnology_.isPresent()) {
//					employee.getTechnologies().add(optionalTechnology.get());
//				}
            }
            employeeRepository.save(employee);
        });
        return employee.getEmployeeId();
    }

    @Override
    public boolean updateEmployee(Integer employeeId, EmployeeRegDTO employeeRegDTO) {
        Employee employee = employeeRepository.findById(employeeId).
                orElseThrow(() -> new EmployeeNotFoundException("Employee not found to update"));

        // by me
        employee.getAddresses().stream().forEach(a -> addressRepository.delete(a));


        employee.setAddresses(null);
        employee.setTechnologies(null);
        employeeRepository.save(employee);
        //
        EmployeeUtils.updateFields(employee, employeeRegDTO);
        employeeRegDTO.technologies().stream().forEach(technologyDTO -> {
            Optional<Technology> optionalTechnology = technologyRepository.findById(technologyDTO.getTechnologyName());
            if (optionalTechnology.isPresent()) {
                Technology t = optionalTechnology.get();

                employee.getTechnologies().add(t);
                t.getEmployees().add(employee);
            } else {
                Technology t = technologyRepository.save(
                        Technology.builder().technologyName(technologyDTO.getTechnologyName()).build()
                );
                employee.getTechnologies().add(t);
                t.getEmployees().add(employee);
            }
        });
        employeeRepository.save(employee);
        return true;
    }

    @Override
    public Integer deleteEmployee(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee does not exist"));
        employee.setTechnologies(null);
        employeeRepository.delete(employee);
        return employee.getEmployeeId();
    }

    @Override
    public List<EmployeeBasicDTO> getEmployeeCity(String city) {
//		List<Employee> byAddressAddressCity = employeeRepository.findByAddress_AddressCity(city);
//		List<EmployeeBasicDTO> collect = byAddressAddressCity.stream().map(employee -> EmployeeUtils.convertEntityToDTO_(employee)).collect(Collectors.toList());
//		return collect;

        return employeeRepository.findByAddress_AddressCity(city)
                .stream().map(employee -> EmployeeUtils.convertEntityToDTO_(employee)).collect(Collectors.toList());

    }

    @Override
    public List<EmployeeBasicDTO> getEmployeeByTechnologyName(String technologyName) {
        return employeeRepository.findByTechnology_TechnologyName(technologyName)
                .stream().map(employee -> EmployeeUtils.convertEntityToDTO_(employee)).collect(Collectors.toList());

    }

    @Override
    public List<EmployeeBasicDTO> getEmployeeByBankIFSC(String bankIFSC) {
        return employeeRepository.findByBankAccount_BankIFSC(bankIFSC)
                .stream().map(employee -> EmployeeUtils.convertEntityToDTO_(employee)).collect(Collectors.toList());
    }
}