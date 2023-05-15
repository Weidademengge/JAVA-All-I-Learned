package com.wddmg.competition.week343;

/**
 * @author duym
 * @version $ Id: Test, v 0.1 2023/05/02 12:37 duym Exp $
 */
public class Test1 {
    public static void main(String[] args) {
        int[] player1 = {10,2,2,3};
        int[] player2 = {3,8,4,5};
        int res = isWinner(player1,player2);
        System.out.println(res);
    }

    public static int isWinner(int[] player1, int[] player2) {
        int r1 = 0;
        int r2 = 0;
        int len = player1.length;
        for (int i = 0; i < len; i++) {
            if ((i - 2 >= 0 && player1[i - 2] == 10) ||  (i -1 >= 0 && player1[i - 1] == 10)) {
                r1 += 2 * player1[i];
            }else{
                r1 += player1[i];
            }
            if ((i - 2 >= 0 && player2[i - 2] == 10) ||  (i -1 >= 0 && player2[i - 1] == 10)) {
                r2 += 2 * player2[i];
            }else{
                r2 += player2[i];
            }
        }
        if(r1 > r2){
            return 1;
        }else if(r1 <r2){
            return 2;
        }else{
            return 0;
        }
    }
}
