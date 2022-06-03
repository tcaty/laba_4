#include <iostream>
#include <algorithm>
#include <ctime>
#include <math.h>
#include <omp.h>
#define N 8


using namespace std;

const int n = 100000000;
double array[n];


double get_min_and_pos_find_time_s() {
    double time_start, time_end, time_result;
    double min = array[0];
    double temp = 0;
    int min_position;

    time_start = omp_get_wtime();
   
    for (int i = 0; i < n; i++)
    { 
        temp += pow(exp(cos(cos(sin(2.0 * i / n)))),3.5);
        if (array[i] <= min) {
             min = array[i];
             min_position = i; 
        }
    }

    time_end = omp_get_wtime();
    time_result = time_end - time_start;

    printf("\n===========Synchronous==========\n");
    printf("min = %.16f\n", min);
    printf("position = %d\n", min_position);
    printf("time = %.16f\n", time_result);
    printf("temp = %.16f\n", temp);
    printf("================================\n\n");

    return time_result;
}


double get_min_and_pos_find_time_p() {
    double time_start, time_end, time_result;
    double min = array[0];
    double temp = 0;
    int min_position;

    time_start = omp_get_wtime();
   
    #pragma omp parallel for reduction(+ : temp)
    for (int i = 0; i < n; i++)
    { 
        temp += pow(exp(cos(cos(sin(2.0 * i / n)))),3.5);
        if (array[i] <= min) {
             min = array[i];
             min_position = i; 
        }
    }

    time_end = omp_get_wtime();
    time_result = time_end - time_start;

    printf("============Parallel============\n");
    printf("min = %.16f\n", min);
    printf("position = %d\n", min_position);
    printf("time = %.16f\n", time_result);
    printf("temp = %.16f\n", temp);
    printf("================================\n\n");

    return time_result;
}


int main()
{
    setlocale(LC_ALL, "RUSSIAN");
    srand(257);
    omp_set_num_threads(N);

    for (int i = 0;i < n;i++)
    {
        array[i] = (double)rand() / RAND_MAX + (double)rand() /
RAND_MAX * 100000;
    }

    printf("Coef = %.16f\n", get_min_and_pos_find_time_s(), get_min_and_pos_find_time_p());
    return 0;
}
