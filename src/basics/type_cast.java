package basics;

public class type_cast {
    public static void main (String[] args){
        byte b = 34;
        int i = 78;
        float f = 27.321f;
        char c = 'W';
        double d = 32.8123987;
        short s = 10243;
        long l = 182932587;
        double result = (l/s)-(d*c)-(f+i)+b;
        System.out.println(result);
    }
}

