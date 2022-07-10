package level3;

import java.math.BigInteger;

public class FuelInjectionProblem {

    public static void main(String[] args) {
//        int solution = solution("1023");
        int solution = solution("42357");
        System.out.println("solution = " + solution);

    }

    public static int solution(String n) {
        BigInteger num = new BigInteger(n);
        int count = 0;
        while (!num.equals(BigInteger.ONE)) {
            if (num.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
                num = num.divide(BigInteger.valueOf(2));
            } else {
                // Looking at it from a binary perspective, it makes sense that if the last 2 digits are 01,
                // we would subtract instead of add (we get 2 division operations instead of 1), and if the last 2 bits are 11,
                // we would add (we, again, get more division operations).
                // There is an edge case where if n=3, we subtract instead of add.
                if (num.and(BigInteger.valueOf(3)).equals(BigInteger.ONE)
                        || num.equals(BigInteger.valueOf(3))) {
                    num = num.subtract(BigInteger.ONE);
                } else {
                    num = num.add(BigInteger.ONE);
                }
            }
            count++;
        }
        return count;
    }
}
