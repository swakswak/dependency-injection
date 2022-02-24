package toy.swak.service.memory;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
public interface EmployeeRepository <T, ID>{
    void save(T t);
    T findById(ID id);
}
