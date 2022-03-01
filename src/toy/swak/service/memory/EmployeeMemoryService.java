package toy.swak.service.memory;

import toy.swak.component.Component;
import toy.swak.service.EmployeeService;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
@Component
public class EmployeeMemoryService implements EmployeeService {
    private final EmployeeMemoryRepository employeeMemoryRepository;

    public EmployeeMemoryService(EmployeeMemoryRepository employeeMemoryRepository) {
        this.employeeMemoryRepository = employeeMemoryRepository;
    }
}
