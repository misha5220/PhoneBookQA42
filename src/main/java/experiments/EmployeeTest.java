package experiments;

public class EmployeeTest {

    public static void main(String[] args) {
        Employee employee = Employee.builder().name("Anton").age(22).build();
        System.out.println(employee);
    }
}

