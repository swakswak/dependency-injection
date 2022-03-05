package toy.swak.service.memory;

import toy.swak.component.Component;
import toy.swak.service.EmployeeService;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
@Component
public class EmployeeMemoryService implements EmployeeService {
    private final EmployeeMemoryRepository employeeRepository;

    EmployeeMemoryService(EmployeeMemoryRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
}
