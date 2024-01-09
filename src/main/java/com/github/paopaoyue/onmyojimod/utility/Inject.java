package com.github.paopaoyue.onmyojimod.utility;

public class Inject {

    public static int[] insertAfter(int[] lineNum) {
        for (int i = 0; i < lineNum.length; ++i) {
            lineNum[i] += 1;
        }

        return lineNum;
    }
}
