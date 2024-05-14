package experiments;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder @ToString
public class Employee {

    @Getter @Setter
    private String name;
    @Getter @Setter
    private int age;
}
