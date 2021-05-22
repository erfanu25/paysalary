package org.paysalary.services;

import org.paysalary.domain.Employee;
import org.paysalary.entity.EmployeeEntity;
import org.paysalary.mapper.EmployeeMapper;
import org.paysalary.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public List<Employee> getEmployeeList() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeEntity -> employeeMapper
                        .entityToDomainMapping()
                        .map(employeeEntity))
                .collect(Collectors.toList());
    }

    public Employee getEmployeeById(long id) {
        return employeeMapper.entityToDomainMapping().map(employeeRepository.findById(id).get());
    }

    public void delete(long id) {
        employeeRepository.deleteById(id);
    }

    public boolean employeeIdExist(String employeeId) {
        EmployeeEntity employeeEntity = employeeRepository.findByEmployeeId(employeeId);
        return employeeEntity != null;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeMapper.entityToDomainMapping().map(
                employeeRepository.save(employeeMapper.domainToEntityMapping().map(employee)));
    }

    public String updateEmployee(Employee employee, long id) {
        Optional<EmployeeEntity> employeeEntityOptional = employeeRepository.findById(id);
        if (!employeeEntityOptional.isPresent()) {
            return "Employee Not Found";
        }
        EmployeeEntity employeeEntity = employeeMapper.domainToEntityMapping().map(employee);
        employeeEntity.setId(id);
        employeeRepository.save(employeeEntity);
        return "Update Success";
    }
}
