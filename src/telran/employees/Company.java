package telran.employees;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import telran.util.Arrays;

public class Company implements Iterable<Employee> {
    private Employee[] employees;

    public Company(Employee[] employees) {
        this.employees = java.util.Arrays.copyOf(employees, employees.length);
        java.util.Arrays.sort(this.employees, Comparator.comparingLong(Employee::getId));
    }

    public void addEmployee(Employee empl) {
        if (java.util.Arrays.binarySearch(employees, empl, Comparator.comparingLong(Employee::getId)) >= 0) {
            throw new IllegalStateException("Employee already exists");
        }
        employees = Arrays.insertSorted(employees, empl, Comparator.comparingLong(Employee::getId));
    }

    public Employee getEmployee(long id) {
        int index = java.util.Arrays.binarySearch(employees, new Employee(id, 0, null), Comparator.comparingLong(Employee::getId));
        return index >= 0 ? employees[index] : null;
    }

    public Employee removeEmployee(long id) {
        for (int i = 0; i < employees.length; i++) {
            if (employees[i].getId() == id) {
                Employee toRemove = employees[i];
                Employee[] newEmployees = new Employee[employees.length - 1];
                System.arraycopy(employees, 0, newEmployees, 0, i);
                System.arraycopy(employees, i + 1, newEmployees, i, employees.length - i - 1);
                employees = newEmployees;
                return toRemove;
            }
        }
        throw new NoSuchElementException("No employee with ID " + id);
    }

    public int getDepartmentBudget(String department) {
        int budget = 0;
        for (Employee employee : employees) {
            if (employee.getDepartment().equals(department)) {
                budget += employee.getBasicSalary();
            }
        }
        return budget;
    }

    @Override
    public Iterator<Employee> iterator() {
        return new Iterator<Employee>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < employees.length;
            }

            @Override
            public Employee next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return employees[currentIndex++];
            }
        };
    }
}
