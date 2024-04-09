package telran.employees.test;

import static org.junit.jupiter.api.Assertions.*;

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
Employee empl1 = new Employee(ID1, SALARY1, DEPARTMENT1);
Employee empl2 = new Employee(ID2, SALARY2, DEPARTMENT1);
Employee empl3 = new Employee(ID3, SALARY3, DEPARTMENT2);
Company company;
@BeforeEach
void setCompany() {
	//before each test there will be created new object company 
	// with array of the given employee objects
	company = new Company(new Employee[] {empl1, empl2, empl3});
}
	@Test
	void testAddEmployee() {
	    assertThrowsExactly(IllegalStateException.class, () -> company.addEmployee(new Employee(ID1, 5000, "HR")));
	}

	@Test
	void testGetEmployee() {
	    assertEquals(empl1, company.getEmployee(ID1));
	    assertNull(company.getEmployee(999)); 
	}


	@Test
	void testRemoveEmployee() {
	    assertNotNull(company.removeEmployee(ID1));
	    assertThrows(NoSuchElementException.class, () -> company.removeEmployee(ID1)); // Повторное удаление
	}


	@Test
	void testGetDepartmentBudget() {
	    assertEquals(SALARY1 + SALARY2, company.getDepartmentBudget(DEPARTMENT1));
	}


	@Test
	void testIterator() {
	    Iterator<Employee> it = company.iterator();
	    assertTrue(it.hasNext());
	    assertEquals(empl1, it.next());
	    assertTrue(it.hasNext());
	    assertEquals(empl2, it.next());
	    assertTrue(it.hasNext());
	    assertEquals(empl3, it.next());
	    assertFalse(it.hasNext());
	}


}