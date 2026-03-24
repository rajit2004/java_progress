package methods;

import java.util.Scanner;
public class area {
    static void main() {
        Scanner in = new Scanner(System.in);
        System.out.println("AREA PLATFORM : \n" +
                "1. Circle\n" +
                "2. Triangle\n" +
                "3. Rectangle\n" +
                "4. Isosceles Triangle\n" +
                "5. Parallelogram\n" +
                "6. Rhombus\n" +
                "7. Equilateral Triangle\n");
        System.out.print("Enter your choice : ");
        int choice = in.nextInt();
        switch(choice){
            case 1:
                circle();
                break;
            case 2:
                triangle();
                break;
            case 3:
                rectangle();
                break;
            case 4:
                isoTriangle();
                break;
            case 5:
                parallelogram();
                break;
            case 6:
                rhombus();
                break;
            case 7:
                equitri();
                break;
            default:
                System.out.println("Enter a valid choice.");
        }
    }
    static void circle(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the radius of the circle : ");
        int r = in.nextInt();
        System.out.println("Area of circle : " + (3.14*r*r));
    }
    static void triangle(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the base : ");
        int b = in.nextInt();
        System.out.print("Enter the height : ");
        int h = in.nextInt();
        System.out.println("Area of triangle : " + (0.5*b*h));
    }
    static void rectangle(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the length : ");
        int l = in.nextInt();
        System.out.print("Enter the breadth : ");
        int b = in.nextInt();
        System.out.println("Area of rectangle : " + (l*b));
    }
    static void isoTriangle(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the base : ");
        int b = in.nextInt();
        System.out.print("Enter the height : ");
        int h = in.nextInt();
        System.out.println("Area of triangle : " + (0.5*b*h));
    }
    static void parallelogram(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the base : ");
        int b = in.nextInt();
        System.out.print("Enter the height : ");
        int h = in.nextInt();
        System.out.println("Area of parallelogram : " + (b*h));
    }
    static void rhombus(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the diagonal1 : ");
        int d1 = in.nextInt();
        System.out.print("Enter the diagonal2 : ");
        int d2 = in.nextInt();
        System.out.println("Area of rhombus : " + (0.5*d1*d2));
    }
    static void equitri(){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the side : ");
        int s = in.nextInt();
        System.out.println("Area of triangle : " + ((Math.sqrt(3))/4)*(s*s));
    }

}
