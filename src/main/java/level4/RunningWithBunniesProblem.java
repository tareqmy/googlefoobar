package level4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class RunningWithBunniesProblem {

    public static void main(String[] args) {
        int[][] times = {
                {0, 2, 2, 2, -1},
                {9, 0, 2, 2, -1},
                {9, 3, 0, 2, -1},
                {9, 3, 2, 0, -1},
                {9, 3, 2, 2, 0}
        };
        int times_limit = 1;
//        int[][] times = {
//                {0, 1, 1, 1, 1},
//                {1, 0, 1, 1, 1},
//                {1, 1, 0, 1, 1},
//                {1, 1, 1, 0, 1},
//                {1, 1, 1, 1, 0}
//        };
//        int times_limit = 3;
        int[] solution = solution(times, times_limit);
        System.out.println("solution = " + Arrays.toString(solution));
    }

    public static boolean hasNegativeCycle(int[][] times, int n) {
        for (int i = 0; i < n; i++) {
            if (times[i][i] < 0) {
                return true;
            }
        }
        return false;
    }

    public static void relaxation(int[][] times, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (times[i][j] > times[i][k] + times[k][j]) {
                        times[i][j] = times[i][k] + times[k][j];
                    }
                }
            }
        }
    }

    public static int[] solution(int[][] times, int times_limit) {
        int length = times.length;
        //if no bunnies return empty array
        if (length <= 2) {
            return IntStream.of().toArray();
        }

        //apply relaxation with bellman-ford algorithm
        relaxation(times, length);

        //if negative cycle exists all bunnies can be saved
        if (hasNegativeCycle(times, length)) {
            return IntStream.range(0, length - 2).toArray();
        }

        ans = new ArrayList<>();
        findSolution(times, times_limit, length);
        //return a sorted array from the list
        return ans.stream().sorted().mapToInt(Integer::intValue).toArray();
    }

    private static List<Integer> ans;

    public static void findSolution(int[][] times, int times_limit, int length) {
        boolean[] visited = new boolean[length];
        visited[0] = true;
        //for all the bunnies index 1 to n-2
        for (int startingBunny = 1; startingBunny < length - 1; startingBunny++) {
            dfs(startingBunny, times_limit - times[0][startingBunny],
                    times, new ArrayList<>(), visited);
        }
    }

    public static void dfs(int currentNode, int time, int[][] times, List<Integer> list, boolean[] visited) {
        int length = times.length;

        //reached destination
        if (currentNode == length - 1) {
            //within time. we have a solution!
            //is this solution better than previous solution?
            if (time >= 0 && list.size() > ans.size()) {
                ans = new ArrayList<>(list);
            }
            return;
        }

        //mark visited and explore graph from this point
        visited[currentNode] = true;
        list.add(currentNode - 1); //add bunny number to end. which is 1 less than currentNode(index in the array)
        for (int nextNode = 1; nextNode < length; nextNode++) {
            if (nextNode == currentNode || visited[nextNode]) {
                continue;
            }
            dfs(nextNode, time - times[currentNode][nextNode], times, list, visited);
        }
        list.remove(list.size() - 1); //remove the last element
        visited[currentNode] = false;
    }
}
