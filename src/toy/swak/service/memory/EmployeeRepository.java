package toy.swak.service.memory;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
public interface EmployeeRepository{
    void save(Employee employee);

    Employee findById(Long aLong);
}
