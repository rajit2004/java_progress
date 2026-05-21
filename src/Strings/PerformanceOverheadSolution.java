package Strings;

public class PerformanceOverheadSolution {
    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder("Series : ");
        for (int i = 0; i < 26; i++) {
            builder.append((char)('a'+i));
            builder.append(" ");
        }
        System.out.println(builder);
    }
}
