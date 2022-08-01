import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String [] args) {
        Scanner input = new Scanner(System.in);
        System.out.println(calc(input.nextLine()));
    }

    public static String calc(String input) {
        String [] rom = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        String [] arab = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String [] strings = input.split(" ");
        if (strings.length != 3) {
            throw new IllegalArgumentException("wrong input!");
        }
        boolean arabian;
        int flag1 = 0;
        int flag2 = 0;
        for (String str : rom) {
            if (str.equals(strings[0])) {
                flag1 = 1;
            }
            if (str.equals(strings[2])) {
                flag2 = 1;
            }
        }
        for (String str : arab) {
            if (str.equals(strings[0])) {
                flag1 = 2;
            }
            if (str.equals(strings[2])) {
                flag2 = 2;
            }
        }
        if (flag1 == 0 || flag2 == 0) {
            throw new IllegalArgumentException("incorrect number system!");
        } else if (flag1 == flag2) {
            if (flag1 == 1) {
                arabian = false;
            } else {
                arabian = true;
            }
        } else {
            throw new IllegalArgumentException("different number systems!");
        }
        switch (strings[1]) {
            case "+" :
                if (arabian) {
                    return Integer.toString(Integer.parseInt(strings[0]) + Integer.parseInt(strings[2]));
                } else {
                    return RomanArabicConverter.arabicToRoman(RomanArabicConverter.romanToArabic(strings[0]) + RomanArabicConverter.romanToArabic(strings[2]));
                }
            case "-" :
                if (arabian) {
                    return Integer.toString(Integer.parseInt(strings[0]) - Integer.parseInt(strings[2]));
                } else {
                    return RomanArabicConverter.arabicToRoman(RomanArabicConverter.romanToArabic(strings[0]) - RomanArabicConverter.romanToArabic(strings[2]));
                }
            case "/" :
                if (arabian) {
                    return Integer.toString(Integer.parseInt(strings[0]) / Integer.parseInt(strings[2]));
                } else {
                    return RomanArabicConverter.arabicToRoman(RomanArabicConverter.romanToArabic(strings[0]) / RomanArabicConverter.romanToArabic(strings[2]));
                }
            case "*" :
                if (arabian) {
                    return Integer.toString(Integer.parseInt(strings[0]) * Integer.parseInt(strings[2]));
                } else {
                    return RomanArabicConverter.arabicToRoman(RomanArabicConverter.romanToArabic(strings[0]) * RomanArabicConverter.romanToArabic(strings[2]));
                }
            default:
                throw new IllegalArgumentException(strings[1] + " wrong arithmetic symbol!");
        }
    }
}

class RomanArabicConverter {
    public static int romanToArabic(String input) {
        String romanNumeral = input;
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " cannot be converted to a Arabic Numeral!");
        }

        return result;
    }

    public static String arabicToRoman(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException(number + " is not in range (0,+inf)!");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }
}
enum RomanNumeral {
    I(1), IV(4), V(5), IX(9), X(10),
    XL(40), L(50), XC(90), C(100);

    private int value;

    RomanNumeral(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static List<RomanNumeral> getReverseSortedValues() {
        return Arrays.stream(values())
                .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                .collect(Collectors.toList());
    }
}


