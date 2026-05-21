package Strings;

public class StringBuilders {
    public static void main(String[] args) {
// StringBuilder itself is a class
        StringBuilder builder = new StringBuilder("The ");
        builder.append("apple");
        System.out.println(builder);
        builder.insert(9 , " is red.");
        System.out.println(builder);
        System.out.println(builder.reverse());
        builder.delete(6,10);
        System.out.println(builder);

    }
}
