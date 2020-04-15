import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args){
        Solution0331 s1 = new Solution0331();
        List<String> rtn = s1.letterCombinations("23");
        for(String str:rtn){
            System.out.println(str);
        }
    }
}