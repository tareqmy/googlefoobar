package level3;

import java.math.BigInteger;

public class BombBabyProblem {

    public static void main(String[] args) {
//        String solution = solution("2", "1"); //1
//        String solution = solution("446", "7236"); //impossible
//        String solution = solution("377", "233"); //12
        String solution = solution("3", "13"); //12
//        String solution = solution("4", "7"); //4
        System.out.println("solution = " + solution);
    }

    public static String solution(String M, String F) {
        if (M.equals("1") && F.equals("1")) {
            return "0";
        }
        BigInteger generations = new BigInteger("0");
        BigInteger[] bombs = {new BigInteger(M), new BigInteger(F)};
        //if both are greater than 1
        while (bombs[0].compareTo(BigInteger.ONE) == 1
                && bombs[1].compareTo(BigInteger.ONE) == 1) {
            sort(bombs);
            BigInteger bigModulusSmall = bombs[1].mod(bombs[0]);
            if (bigModulusSmall.equals(BigInteger.ZERO))
                return "impossible";
            BigInteger bigDividedSmall = bombs[1].divide(bombs[0]);
            generations = generations.add(bigDividedSmall);
            bombs[1] = bigModulusSmall;
        }
        generations = generations.add(bombs[0].max(bombs[1])).subtract(BigInteger.ONE);
        return generations.toString();
    }

    public static void sort(BigInteger[] bigIntegers) {
        if (bigIntegers[1].compareTo(bigIntegers[0]) == -1) {
            BigInteger swap = bigIntegers[0];
            bigIntegers[0] = bigIntegers[1];
            bigIntegers[1] = swap;
        }
    }
}
