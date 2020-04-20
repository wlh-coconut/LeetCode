import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args){
        Solution0331 s1 = new Solution0331();
        int[] a = {0,0,0,0};
        List<String> rtn = s1.generateParenthesis(3);
        for(String string : rtn){
            System.out.println(string);
        }
    }
}