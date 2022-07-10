package level2;

import java.util.Arrays;

public class GearingUpProblem {

    public static void main(String[] args) {
        int[] pegs = {4, 30, 50};
        int[] solution = solution(pegs);
        System.out.println("solution = " + Arrays.toString(solution));
    }

    //even -- r1 = 2 * ( 2 (d2 + d4 + ... dn) - 2(d1 + d3 + ... + d(n-1)) + d1 - dn)
    //even -- r1 = 1/3 * 2 * ( 2 (d2 + d4 + ... d(n-1)) - 2(d1 + d3 + ... + dn) + d1 + dn)
    public static int[] solution(int[] pegs) {
        int propSol = 2 * (sumArray(1, 2, pegs) - sumArray(0, 2, pegs)) + pegs[0];
        int[] retVal = new int[2];
        int[] impossible = new int[]{-1, -1};
        int length = pegs.length;
        if (length % 2 == 0) {
            propSol = 2 * (propSol - pegs[length - 1]);
            if (propSol / 3 < 1) {
                return impossible;
            }
            if (propSol % 3 == 0) {
                retVal[0] = propSol / 3;
                retVal[1] = 1;
            } else {
                retVal[0] = propSol;
                retVal[1] = 3;
            }
        } else {
            propSol = 2 * (propSol + pegs[length - 1]);
            if (propSol < 1)
                return impossible;
            else {
                retVal[0] = propSol;
                retVal[1] = 1;
            }
        }

        // calculate r2, r3, ... rn and see if value less than 1
        int latestGear = retVal[0] / retVal[1]; //r1
        for (int i = 0; i < length - 1; i++) {
            latestGear = pegs[i + 1] - pegs[i] - latestGear; //r2 = d2 - d1 - r1
            if (latestGear < 1) {
                return impossible;
            }
        }
        return retVal;
    }

    public static int sumArray(int start, int gap, int[] pegs) {
        int sum = 0;
        for (int i = start; i < pegs.length; i = i + gap) {
            sum = sum + pegs[i];
        }
        return sum;
    }
}
