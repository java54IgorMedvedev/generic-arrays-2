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
    private static final String DEPARTMENT1 = "Development";
    private static final long ID2 = 120;
    private static final int SALARY2 = 2000;
    private static final long ID3 = 125;
    private static final int SALARY3 = 3000;
    private static final String DEPARTMENT2 = "QA";
    private static final long ID4 = 200;
    private static final int SALARY4 = 2000;
    private static final String DEPARTMENT4 = "Development";

    private Company company;
    private Employee empl1;
    private Employee empl2;
    private Employee empl3;
    private Manager manager;

    @BeforeEach
    void setUp() {
        empl1 = new Employee(ID1, SALARY1, DEPARTMENT1);
        empl2 = new Employee(ID2, SALARY2, DEPARTMENT1);
        empl3 = new Employee(ID3, SALARY3, DEPARTMENT2);
        manager = new Manager(ID4, SALARY4, DEPARTMENT4, 1.5);
        company = new Company(new Employee[]{empl1, empl2, empl3, manager});
    }

    @Test
    void testAddEmployee() {
        Employee newEmployee = new Employee(130, 5000, "HR");
        company.addEmployee(newEmployee);
        assertThrows(IllegalStateException.class, () -> company.addEmployee(newEmployee));
        assertThrows(IllegalStateException.class, () -> company.addEmployee(empl1));
    }

    @Test
    void testGetEmployee() {
        assertEquals(empl1, company.getEmployee(ID1));
        assertNull(company.getEmployee(999)); // Проверяем на несуществующем ID
    }

    @Test
    void testRemoveEmployee() {
        assertEquals(empl1, company.removeEmployee(ID1));
        assertThrows(NoSuchElementException.class, () -> company.removeEmployee(ID1));
    }

    @Test
    void testGetDepartmentBudget() {
        int expectedBudget = SALARY1 + SALARY2 + (int)(SALARY4 * 1.5);  // Manager's salary with factor
        assertEquals(expectedBudget, company.getDepartmentBudget(DEPARTMENT1));
        assertEquals(SALARY3, company.getDepartmentBudget(DEPARTMENT2));
    }

    @Test
    void testIterator() {
        Employee[] expected = {empl1, empl2, empl3, manager};
        Arrays.sort(expected, Comparator.comparingLong(Employee::getId));
        Iterator<Employee> it = company.iterator();
        int index = 0;
        while (it.hasNext()) {
            assertEquals(expected[index++], it.next());
        }
        assertEquals(expected.length, index);
        assertThrows(NoSuchElementException.class, it::next);
    }


    @Test
    void testGetDepartments() {
        String[] expectedDepartments = {DEPARTMENT1, DEPARTMENT2};
        Arrays.sort(expectedDepartments);
        String[] actualDepartments = company.getDepartments();
        Arrays.sort(actualDepartments);
        assertArrayEquals(expectedDepartments, actualDepartments);
    }
}
