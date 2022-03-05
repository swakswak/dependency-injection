package toy.swak.service.memory;

import toy.swak.component.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
@Component
public class EmployeeMemoryRepository implements EmployeeRepository{
    private final Map<Long, Employee> memoryDatabase;

    EmployeeMemoryRepository() {
        this.memoryDatabase = new ConcurrentHashMap<>();
    }

    @Override
    public void save(Employee employee) {

    }

    @Override
    public Employee findById(Long aLong) {
        return null;
    }
}
