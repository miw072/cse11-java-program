import java.io.*;
import java.util.*;
public class Solution {
    public static void main(String args[] ) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        Scanner scnr = new Scanner(System.in);
        int n = scnr.nextInt();
        int m = 0;
        for(int i = 1; ; i++){
            m = n * i;
            if (isValid(m)){
                System.out.println(m);
                return;
            }
        }
    }
    private static boolean isValid(int m){
        for (int i = 10; m != 0; m /= 10){
            if (m % i == 0 || m % i == 1){
                continue;
            }else{
                return false;
            }
        }
        return true;
    }
}


if (z == 0 || z == 1){
            return 0;
        }
        double num = (double) z;
        for (double i = 2.0; i < num; i++){
            double q = Math.log(num) / Math.log(i);
            if (q - (int)q == 0){
                return 1;
            }
        }
        return 0;