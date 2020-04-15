package Test.meituan;

import java.util.HashMap;
import java.util.Scanner;

public class meituan5 {
    public static int numOfSub(String a, HashMap<Integer,String> b){
        int count = 0;
        for(String s : b.values()){
            int lenOfSub = s.length();
            for(int i = 0; i < a.length(); ++i){
                if(i+lenOfSub <= a.length() && a.substring(i,i+lenOfSub).equals(s))
                    count++;
            }
        }
        return count;
    }
    public static void main(String[] args) {
        int M = (int) Math.pow(10, 9) + 7;
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            // 输入部分
            int n = sc.nextInt();
            int k = sc.nextInt();
            String input, oprate;
            int count = 0;
            // 原本
            HashMap<Integer,String> map1 = new HashMap<>();
            // 用于增删子串
            HashMap<Integer,String> map2 = new HashMap<>();
            sc.nextLine();
            for(int i = 0; i < k; ++i){
                input = sc.nextLine();
                map1.put(i+1,input);
                map2.put(i+1,input);
            }
            for(int i = 0; i < n; ++i){
                oprate = sc.nextLine();
                if(oprate.charAt(0) == '?'){
                    System.out.println(numOfSub(oprate.substring(1),map2));
                }
                else if(oprate.charAt(0) == '+')
                    map2.put(Integer.parseInt(oprate.substring(1)),map1.get(Integer.parseInt(oprate.substring(1))));
                else if(oprate.charAt(0) == '-')
                    map2.remove(Integer.parseInt(oprate.substring(1)));
            }
        }
    }
}
