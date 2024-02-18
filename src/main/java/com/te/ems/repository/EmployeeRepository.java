package com.te.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.te.ems.entity.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "from Employee e inner join Address a on e.employeeId = a.addressId where a.addressCity = :city")
    List<Employee> findByAddress_AddressCity(@Param(value = "city")String city);
                   // findByAddressesAddressCity

    @Query(value = "from Employee e inner join e.technologies t where t.technologyName = :technologyName")
    List<Employee> findByTechnology_TechnologyName(@Param(value = "technologyName")String technologyName);
                // findBytechnologiesTechnologyName

    @Query(value = "from Employee e inner join e.bankAccount b where b.bankIFSC = :bankIfsc")
    List<Employee> findByBankAccount_BankIFSC(@Param(value = "bankIfsc")String bankIFSC);
                // findByBankAccountBankIFSC
}
