package telran.employees.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.employees.*;

class CompanyTest {
    private static final long ID1 = 123;
    private static final int SALARY1 = 1000;
    private static final String DEPARTMENT1 = "QA";
    private static final long ID2 = 120;
    private static final int SALARY2 = 2000;
    private static final long ID3 = 125;
    private static final int SALARY3 = 3000;
    private static final String DEPARTMENT2 = "Development";
    private static final long ID4 = 200;
    private static final String DEPARTMENT4 = "Audit";
    private static final int WAGE1 = 100;
    private static final int HOURS1 = 10;
    private static final float FACTOR1 = 2.0f;
    private static final float PERCENT1 = 0.01f;
    private static final long SALES1 = 10000;

    private Company company;
    private Employee empl1, empl2, empl3;

    @BeforeEach
    void setCompany() {
        empl1 = new WageEmployee(ID1, SALARY1, DEPARTMENT1, WAGE1, HOURS1);
        empl2 = new Manager(ID2, SALARY2, DEPARTMENT1, FACTOR1);
        empl3 = new SalesPerson(ID3, SALARY3, DEPARTMENT2, WAGE1, HOURS1, PERCENT1, SALES1);
        company = new Company(new Employee[] {empl1, empl2, empl3});
    }

    @Test
    void testAddEmployee() {
        Employee newEmployee = new Employee(ID4, SALARY1, DEPARTMENT1);
        company.addEmployee(newEmployee);
        assertThrowsExactly(IllegalStateException.class, () -> company.addEmployee(newEmployee));
        assertThrowsExactly(IllegalStateException.class, () -> company.addEmployee(empl1));
    }

    @Test
    void testGetEmployee() {
        assertEquals(empl1, company.getEmployee(ID1));
        assertNull(company.getEmployee(ID4));
    }

    @Test
    void testRemoveEmployee() {
        assertEquals(empl1, company.removeEmployee(ID1));
        assertThrowsExactly(NoSuchElementException.class, () -> company.removeEmployee(ID1));
    }

    @Test
    void testGetDepartmentBudget() {
        int expectedBudgetDept1 = empl1.computeSalary() + empl2.computeSalary();
        int expectedBudgetDept2 = empl3.computeSalary();
        assertEquals(expectedBudgetDept1, company.getDepartmentBudget(DEPARTMENT1));
        assertEquals(expectedBudgetDept2, company.getDepartmentBudget(DEPARTMENT2));
        assertEquals(0, company.getDepartmentBudget(DEPARTMENT4));
    }

    @Test
    void testIterator() {
        Employee[] expected = {empl1, empl2, empl3};
        Arrays.sort(expected, Comparator.comparingLong(Employee::getId));
        Iterator<Employee> it = company.iterator();
        int index = 0;
        while(it.hasNext()) {
            assertEquals(expected[index++], it.next());
        }
        assertEquals(expected.length, index);
        assertThrowsExactly(NoSuchElementException.class, it::next);
    }

    @Test
    void testGetDepartments() {
        String[] expected = {DEPARTMENT1, DEPARTMENT2};
        Arrays.sort(expected);
        assertArrayEquals(expected, company.getDepartments());
    }

    @Test
    void testGetManagersWithMostFactor() {
        Manager[] managers = company.getManagersWithMostFactor();
        assertTrue(Arrays.asList(managers).contains(empl2));
        assertEquals(1, managers.length);
        assertEquals(FACTOR1, ((Manager) managers[0]).factor);
    }
}
