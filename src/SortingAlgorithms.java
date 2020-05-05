/*
- Running with array size 1.000
Selection sort processing time in milliseconds: 3
Arrays.sort processing time in milliseconds: 1

- Running with array size 10.000
Selection sort processing time in milliseconds: 60
Arrays.sort processing time in milliseconds: 3

- Running with array size 100.000
Selection sort processing time in milliseconds: 4857
Arrays.sort processing time in milliseconds: 12
*/

import java.util.Arrays;

public class SortingAlgorithms {
    public static void main(String[] args) {
        int arraySize = 100000;
        int[] firstArray = new int[arraySize];
        int[] secondArray = new int[arraySize];

        for (int i = 0; i < arraySize; i++) {
            int randomNumber = (int) (Integer.MAX_VALUE * Math.random());
            firstArray[i] =  randomNumber;
            secondArray[i] =  randomNumber;

        }
        timeSelectionSort(firstArray);
        timeArraysSort(secondArray);
    }

    static void timeSelectionSort(int[] array) {
        long startTime = System.currentTimeMillis();
        selectionSort(array);
        long runTime = System.currentTimeMillis() - startTime;
        System.out.println("Selection sort processing time in milliseconds: " + runTime);
    }

    static void timeArraysSort(int[] array) {
        long startTime = System.currentTimeMillis();
        Arrays.sort(array);
        long runTime = System.currentTimeMillis() - startTime;
        System.out.println("Arrays.sort processing time in milliseconds: " + runTime);
    }

    static void selectionSort(int[] A) {
        // Sort A into increasing order, using selection sort
        for (int lastPlace = A.length-1; lastPlace > 0; lastPlace--) {
            int maxLoc = 0;  // Location of largest item seen so far.

            for (int j = 1; j <= lastPlace; j++) {
                if (A[j] > A[maxLoc]) {
                    maxLoc = j;
                }
            }

            int temp = A[maxLoc];  // Swap largest item with A[lastPlace].
            A[maxLoc] = A[lastPlace];
            A[lastPlace] = temp;
        }
    }
}