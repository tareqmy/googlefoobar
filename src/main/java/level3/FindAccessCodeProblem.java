package level3;

public class FindAccessCodeProblem {

    public static void main(String[] args) {
        int[] codes = {1, 2, 3, 4, 5, 6};
        int solution = solution(codes);
        System.out.println("solution = " + solution);
    }

    public static int solution(int[] codes) {
        int noOfCombinations = 0;
        int[] noOfDoubles = new int[codes.length];

        for (int i = 0; i < codes.length; i++) {
            for (int j = 0; j < i; j++) {
                if (codes[i] % codes[j] == 0) {
                    ++noOfDoubles[i];
                    noOfCombinations = noOfCombinations + noOfDoubles[j];
                }
            }
        }
        return noOfCombinations;
    }
}
