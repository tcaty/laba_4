import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Example {
    private static final int M = 2022;
    private static final int K = 3033;
    private static final int N = 1122;

    private static long syncTime = 0l;
    private static long asyncTime = 0l;

    private static int[][] result = new int[M][N];

   
    private static int matrix1(int i, int j) {
        return 2;
    }
   
    private static int matrix2(int i, int j) {
        return 3;
    }


    public static void main(String[] args) {
        clearResult();
        SyncTask();
       // printResult(); 

        clearResult();
	AsyncTask();
       // printResult(); 

        System.out.printf("Ratio: [%f]\n", (float)syncTime / asyncTime);
    }

    private static void clearResult() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                result[i][j] = 0;
            }
        }
    }

    private static void printResult() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.print("\n");
        }
    }


    
    private static void SyncTask() {
        syncTime = System.currentTimeMillis();

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                int element = 0;
                for (int k = 0; k < K; k++) {
                    element += matrix1(i, k) * matrix2(k, j);
                }
                result[i][j] = element;
            }
        }

        syncTime = System.currentTimeMillis() - syncTime;
        System.out.printf("Sync time: [%d]ms\n", syncTime);
    }

    

    private static void AsyncTask() {
        asyncTime = System.currentTimeMillis();

        int split = (int)Math.ceil((float)M / 2); //Thread num (2)
        
        List<Integer> threadNums = Arrays.asList(0,1); // 0, 1 - index of thread
        threadNums.parallelStream().forEach(n_thread -> {
          for (int i = split*n_thread; (i < split*(n_thread+1)) && (i < M); i++) {
            for (int j = 0; j < N; j++) {
              int element = 0;
              for (int k = 0; k < K; k++) {
                element += matrix1(i, k) * matrix2(k, j);
              }
              result[i][j] = element;
            }
          }
        });

        asyncTime = System.currentTimeMillis() - asyncTime;
        System.out.printf("Async time: [%d]ms\n", asyncTime);
    }
   
}