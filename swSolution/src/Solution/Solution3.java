package Solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
class Solution3 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for(int t=1;t<=T;t++){
            int n = Integer.parseInt(br.readLine());
            StringTokenizer stk =new StringTokenizer(br.readLine());
            int[] arr = new int[n];
            for(int i=0;i<n;i++){
                arr[i] = Integer.parseInt(stk.nextToken());
            }
            int[] dp = new int[n];
            int max = 0;
            for(int i=n-1;i>=0;i--){
                max = Math.max(max,arr[i]);
                dp[i] = max;
            }
            long sum = 0 ;
            for(int i=0;i<n;i++){
                if(dp[i]>arr[i]){
                    sum += dp[i]-arr[i];
                }
            }
            System.out.println("#"+t+" "+sum);
        }
    }
}