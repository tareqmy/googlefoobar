package level2;

import java.util.Arrays;

public class IonFluxRelabelingProblem {

    public static void main(String[] args) {
        int h = 5;
        int[] q = {19, 14, 28};
//        int h = 3;
//        int[] q = {7, 3, 5, 1};
        int[] solution = solution(h, q);
        System.out.println("solution = " + Arrays.toString(solution));
    }

    public static int[] solution(int h, int[] q) {
        int[] p = new int[q.length];

        for (int i = 0; i < q.length; i++) {
            p[i] = getParentIndex(h, q[i]);
        }

        return p;
    }

    private static int getParentIndex(int h, int index) {
        int result = -1;
        int subtreeSize = (int) (Math.pow(2, h) - 1);
        if (subtreeSize < index)
            return result;

        int parentNode = subtreeSize;

        while (true) {
            if (subtreeSize == 0)
                break;

            //right shift is equivalent to dividing by 2 and discarding the remainder.
            subtreeSize = subtreeSize >> 1;

            //calculate right node
            int rightNode = parentNode - 1;

            //predict the left node
            int leftNode = rightNode - subtreeSize;

            //if either child is a match, return parent node value
            if ((leftNode == index) || (rightNode == index)) {
                result = parentNode;
                break;
            }

            //Make the current left child the offset if the index is greater than the left.
            //This effectively searches down the right subtree.
            if (index > leftNode)
                parentNode = rightNode;
            else
                parentNode = leftNode;
        }

        return result;
    }
}
