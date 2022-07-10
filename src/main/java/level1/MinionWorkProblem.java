package level1;

import java.util.*;

public class MinionWorkProblem {

    public static void main(String[] args) {
        int[] data = {1, 2, 2, 3, 3, 3, 4, 5, 5};
        int n = 1;
        int[] solution = solution(data, n);
        System.out.println("solution = " + Arrays.toString(solution));
    }

    public static int[] solution(int[] data, int n) {
        //solution
        Map<Integer, Integer> countMap = new HashMap<>();

        for (int datum : data) {
            int count = 1;
            if (countMap.containsKey(datum)) {
                count = countMap.get(datum) + 1;
                countMap.put(datum, count);
            } else {
                countMap.put(datum, count);
            }
        }

        List<Integer> solution = new ArrayList<>();
        for (int datum : data) {
            if (countMap.get(datum) <= n) {
                solution.add(datum);
            }
        }

        return solution.stream().mapToInt(i -> i).toArray();
    }

}
