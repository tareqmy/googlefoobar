package level4;


import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Collections;

public class EscapePodsProblem {

    public static void main(String[] args) {
        int[] sources = {0, 1};
        int[] sinks = {4, 5};
        int[][] network = {
                {0, 0, 4, 6, 0, 0},
                {0, 0, 5, 2, 0, 0},
                {0, 0, 0, 0, 4, 4},
                {0, 0, 0, 0, 6, 6},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}};
//        int[] sources = {0};
//        int[] sinks = {3};
//        int[][] network = {
//                {0, 7, 0, 0},
//                {0, 0, 6, 0},
//                {0, 0, 0, 8},
//                {9, 0, 0, 0}};
        int solution = solution(sources, sinks, network);
        System.out.println("solution = " + solution);
    }

    private static final int CORRIDOR_CAPACITY = 2000001;

    private static int[][] transform(int[] sources, int[] sinks, int[][] network) {
        // transform to an equivalent single-source, single-sink flow network
        int length = network.length;
        int newLength = length + 2;
        int[][] newNetwork = new int[newLength][newLength];
        for (int i = 0; i < length; i++) {
            System.arraycopy(network[i], 0, newNetwork[i + 1], 1, length);
        }
        for (int entrance : sources) {
            newNetwork[0][entrance + 1] = CORRIDOR_CAPACITY;
        }
        for (int exit : sinks) {
            newNetwork[exit + 1][newLength - 1] = CORRIDOR_CAPACITY;
        }
        return newNetwork;
    }

    private static List<Integer> bfs(int[][] residualNetwork) {
        // find a path from s to t that every (u, v) in p satisfies c_f(u, v) > 0
        int[] parents = new int[residualNetwork.length];
        Arrays.fill(parents, -1);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        int u;
        while (!queue.isEmpty() && parents[parents.length - 1] == -1) {
            u = queue.remove();
            for (int v = 0; v < parents.length; v++) {
                if (residualNetwork[u][v] > 0 && parents[v] == -1) {
                    queue.add(v);
                    parents[v] = u;
                }
            }
        }
        List<Integer> path = new ArrayList<>();
        u = parents[parents.length - 1];
        while (u != 0) {
            if (u == -1) {
                return null;
            }
            path.add(u);
            u = parents[u];
        }
        Collections.reverse(path);
        return path;
    }

    private static int solveWithFordFulkerson(int[][] residualNetwork) {
        // https://en.wikipedia.org/wiki/Ford%E2%80%93Fulkerson_algorithm
        int maxFlow = 0;
        List<Integer> path;
        while ((path = bfs(residualNetwork)) != null) {
            // calculate residual capacity c_f(p)
            int residualCapacity = CORRIDOR_CAPACITY;
            int u = 0;
            for (int v : path) {
                residualCapacity = Math.min(residualCapacity, residualNetwork[u][v]);
                u = v;
            }
            // increment max flow
            maxFlow += residualCapacity;
            u = 0;
            // update residual network
            for (int v : path) {
                residualNetwork[u][v] -= residualCapacity;
                residualNetwork[v][u] += residualCapacity;
                u = v;
            }
        }
        return maxFlow;
    }

    public static int solution(int[] entrances, int[] exits, int[][] path) {
        return solveWithFordFulkerson(transform(entrances, exits, path));
    }
}
