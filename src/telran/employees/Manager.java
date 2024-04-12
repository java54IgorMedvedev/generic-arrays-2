package telran.employees;

public class Manager extends Employee {
	private double factor;

    public Manager(long id, int basicSalary, String department, double factor) {
        super(id, basicSalary, department);
        this.factor = factor;
    }

    @Override
    public int computeSalary() {
        return (int) (getBasicSalary() * factor);
    }
}