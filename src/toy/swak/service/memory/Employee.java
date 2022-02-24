package toy.swak.service.memory;

import java.time.LocalDate;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
public class Employee {

    private final Long id;

    private final String name;

    private final LocalDate startDate;

    private final Integer salary;

    public Employee(Long id, String name, LocalDate startDate, Integer salary) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Integer getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", salary=" + salary +
                '}';
    }
}
