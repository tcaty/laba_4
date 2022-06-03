import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Main {
  private static double[] array = new double[100000000];

  private static long syncTime = 0l;
  private static long asyncTime = 0l;

  private static double temp = 0;
  private static double min = array[0];
  private static int min_pos = 0;

  public static void main(String[] args) {
    FillArrayRandomNumbers();
    SyncTask();
    ResetVariables();
    AsyncTask();
    System.out.printf("Coef = %.3f\n", (float)syncTime / asyncTime);
  }

  public static void FillArrayRandomNumbers() {
    for (int i = 0; i < array.length; i++) {
      array[i] = Math.random() * 1000 - 200;
    }
  }

  public static void ResetVariables() {
    temp = 0;
    min = array[0];
    min_pos = 0;
  }

  private static void SyncTask() {
    syncTime = System.currentTimeMillis();

    for (int i = 0; i < array.length; i++)
    { 
        temp += Math.pow(Math.exp(Math.cos(Math.cos(Math.sin(2.0 * i / array.length)))),3.5);

        if (array[i] <= min) {
             min = array[i];
             min_pos = i; 
        }
    }

    syncTime = System.currentTimeMillis() - syncTime;
    PrintResult("Synchronous", min, min_pos, syncTime, temp);
  }

  private static void AsyncTask() {
    asyncTime = System.currentTimeMillis();

    int[] numbers = new int[array.length];
    Arrays.fill(numbers, 0);

    temp = Arrays.stream(numbers).reduce(0, (a, b) -> (int) (a + Math.pow(Math.exp(Math.cos(Math.cos(Math.sin(2.0 * 2.0 / array.length)))),3.5)));

    List<Integer> threadNums = Arrays.asList(0,1);
    threadNums.parallelStream().forEach(n_thread -> {
      for (int i = 0; i < array.length; i++) { 
        if (array[i] <= min) {
             min = array[i];
             min_pos = i; 
        }
      }
    });

    asyncTime = System.currentTimeMillis() - asyncTime;
    PrintResult("Asynchronous", min, min_pos, syncTime, temp);
  }

  private static void PrintResult(String taskType, double min, int min_pos, long time, double temp) {
    System.out.printf("===========%s==========\n", taskType);
    System.out.printf("min = %.3f\n", min);
    System.out.printf("position = %d\n", min_pos);
    System.out.printf("time = %dms\n", time);
    System.out.printf("temp = %.3f\n", temp);
    System.out.println("================================\n");
  }
}