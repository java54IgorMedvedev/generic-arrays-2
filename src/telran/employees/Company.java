package telran.employees;

import java.util.Iterator;
import java.util.NoSuchElementException;

import telran.util.Arrays;
//SO far we don't consider optimization
public class Company implements Iterable{
	private Employee[] employees;
	public void addEmployee(Employee empl) {
	    for (Employee employee : employees) {
	        if (employee.getId() == empl.getId()) {
	            throw new IllegalStateException("Employee with ID " + empl.getId() + " already exists.");
	        }
	    }
	    employees = Arrays.add(employees, empl);
	}

	public Employee getEmployee(long id) {
	    Employee foundEmployee = null;
	    for (Employee employee : employees) {
	        if (employee.getId() == id) {
	            foundEmployee = employee;
	            break; 
	        }
	    }
	    return foundEmployee; 
	}

	public Employee removeEmployee(long id) {
	    for (int i = 0; i < employees.length; i++) {
	        if (employees[i].getId() == id) {
	            Employee toRemove = employees[i];
	            employees = Arrays.removeIf(employees, e -> e.getId() == id);
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

	public Company(Employee[] employees) {
		this.employees = Arrays.copy(employees);
	}
	@Override
	public Iterator<Employee> iterator() {
		
		return new CompanyIterator();
	}
	private class CompanyIterator implements Iterator<Employee> {
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
	}

}