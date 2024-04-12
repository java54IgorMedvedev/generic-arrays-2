package telran.employees;

public class SalesPerson extends WageEmployee {
    private double commissionRate;

    public SalesPerson(long id, int basicSalary, String department, int hours, int wage, double commissionRate) {
        super(id, basicSalary, department, hours, wage);
        this.commissionRate = commissionRate;
    }

    @Override
    public int computeSalary() {
        return super.computeSalary() + (int) (getHours() * getWage() * commissionRate);
    }
}
