package conditionals;

import java.util.Scanner;
public class nested_switch {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the department : ");
        String dept = input.next();
        System.out.print("Enter the employee id : ");
        int empID = input.nextInt();
        switch(dept){
            case "Engineering":
                switch(empID){
                    case 101 -> System.out.println("ADMIN");
                    case 102 -> System.out.println("STAFF");
                    case 103 -> System.out.println("MANAGER");
                    case 104 -> System.out.println("DEVELOPER");
                    case 105 -> System.out.println("DEBUGGER");
                    case 106 -> System.out.println("TESTER");
                    default -> System.out.println("INVALID EMPID");
                }
                break;
            case "Medical":
                switch(empID){
                    case 101 -> System.out.println("HEAD DOC");
                    case 102 -> System.out.println("DEAN");
                    case 103 -> System.out.println("SURGEON");
                    case 104 -> System.out.println("NURSE");
                    case 105 -> System.out.println("COMPOUNDER");
                    case 106 -> System.out.println("STAFF");
                    default -> System.out.println("INVALID EMPID");
                }
                break;
            default:
                System.out.println("INVALID DEPARTMENT");
        }
    }
}
