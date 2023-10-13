package monitoring_ozone.util.atoi;

public class StringToInteger {

    private StringToInteger() {}

    public static int parseInt(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                sb.append(str.charAt(i));
            }
        }
        return Integer.parseInt(sb.toString());
    }
}
