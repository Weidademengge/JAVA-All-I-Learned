package com.wddmg.competition.week339;

/**
 * @author duym
 * @version $ Id: Test6359, v 0.1 2023/04/02 10:29 duym Exp $
 */
public class Test6362 {
    public static void main(String[] args) {
        // 实例1
//        String s = "01000111";

        // 示例2
//        String s = "00111";

        // 示例3
        String s = "111";

        int res = findTheLongestBalancedSubstring(s);
        System.out.println(res);
    }

    public static int findTheLongestBalancedSubstring(String s) {
        char[] arr = s.toCharArray();
        int res = 0;
        for(int i = 0;i<arr.length;i++){
            int zeroPoint = 0,zeroCount =0;
            int onePoint = 0,oneCount = 0;
            if(arr[i] == '0'){
                zeroPoint = i;
                while(zeroPoint < arr.length && arr[zeroPoint] == '0'){
                    zeroCount++;
                    zeroPoint++;
                }
                onePoint = zeroPoint;
                while(onePoint < arr.length && arr[onePoint] == '1'){
                    oneCount++;
                    onePoint++;
                }
            }else{
                continue;
            }
            res = 2* Math.min(zeroCount,oneCount) > res ? 2* Math.min(zeroCount,oneCount):res;
            if(onePoint < arr.length){
                i = onePoint - 1;
            }
        }
        return res;
    }
}
