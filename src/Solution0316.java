import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution0316 {
    // 7. 整数反转
    public int reverse(int x) {
        long rtn = 0;
        Math.abs(x);
        while(x != 0){
            rtn = rtn * 10 + x % 10;
            if((int)rtn != rtn)
                return 0;
            x /= 10;
        }
        if(x >= 0)
            return (int)rtn;
        else
            return (int)-rtn;
    }

    // 836. 矩形重叠
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        // 把问题看做两个矩形分别在x、y维度上是否重叠
        // 先看不相交的情况
        // return !((rec2[0]>=rec1[2])||(rec2[2]<=rec1[0])||(rec2[1]>=rec1[3])||(rec2[3]<=rec1[1]));
        // 看两个维度上是否重叠
        return (Math.min(rec1[2],rec2[2])>Math.max(rec1[0],rec2[0]))
                &&(Math.min(rec1[3],rec2[3])>Math.max(rec1[1],rec2[1]));
    }

    // 8. 字符串转换整数 (atoi)
    public int myAtoi(String str) {
        int  neg = 0;
        for(int i = 0; i<str.length(); i++){
            if(str.charAt(i) == ' ')
                continue;
            else if(str.charAt(i) == '+' || str.charAt(i) == '-'){
                if(neg == 0){
                    neg = (str.charAt(i)=='+')?1:-1;
                    return myNum(str, i+1, neg);
                }
                else
                    return 0;
            }
            else if(str.charAt(i) >='0'&&str.charAt(i)<='9')
                return myNum(str, i, 1);
            else
                return 0;
        }
        return 0;
    }
    public int myNum(String str, int x, int neg){
        long num = 0;
        for(int i = x; i<str.length(); i++){
            if(str.charAt(i) >='0'&&str.charAt(i)<='9'){
                num = num*10+(str.charAt(i) - '0');
                if(neg == 1 && num > Integer.MAX_VALUE)
                    return Integer.MAX_VALUE;
                if(neg == -1 && -num < Integer.MIN_VALUE)
                    return Integer.MIN_VALUE;
            }
            else
                break;
        }
        return (int)(neg*num);
    }
    // 正则表达式
    public int myAtoi2(String str) {
        int value = 0;
        str = str.trim();
        Pattern reg = Pattern.compile("^[\\+\\-]?\\d+");
        Matcher m = reg.matcher(str);
        if(m.find()){
            // 字符串转整数
            try {
//                System.out.println(m.group(0));
                value = Integer.parseInt(str.substring(m.start(),m.end()));
            }catch (Exception e){
                // 溢出异常
                value = str.charAt(m.start())=='-' ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
        }
        return value;
    }

    // 9. 回文数 现在想想计算量有点大，太傻了
    public boolean isPalindrome(int x) {
        if(x < 0)
            return false;
        int ch = 1, n_x = x, w = 1;
        while(n_x >= 10){
            n_x /= 10;
            ch *= 10;
            w++;
        }
        w = w/2;
        int sub = 0;
        while(w > 0){
            if(x % 10 != x / ch)
                return false;
            sub = ch*(x/ch);
            x = (x - sub)/10;
            ch /= 100;
            w--;
        }
        return true;
    }
    // 直接将数字后半部分反转，再和前半部分比较是否相等就好了！
    public boolean isPalindrome2(int x) {
        if(x < 0 || (x%10 == 0 && x != 0))
            return false;
        int reverseInt = 0;
        while(x > reverseInt){
            reverseInt = reverseInt*10 + x%10;
            x /= 10;
        }
        return reverseInt == x || x == reverseInt/10;
    }

    // 10. 正则表达式匹配
    public boolean isMatch(String s, String p) {
        boolean dp[][] = new boolean[s.length()+1][p.length()+1];
        dp[0][0] = true;
        for(int i = 0; i < p.length(); i++){
            if(p.charAt(i) == '*' && dp[0][i-1])
                dp[0][i+1] = true;
        }
        for(int i = 0; i < s.length(); i++){
            for(int j = 0; j < p.length(); j++){
                if(s.charAt(i) == p.charAt(j) || p.charAt(j) == '.')
                    dp[i+1][j+1] = dp[i][j];
                else if(p.charAt(j) == '*'){
                    if(s.charAt(i) != p.charAt(j-1))
                        dp[i+1][j+1] = dp[i+1][j-1];
                    if(s.charAt(i) == p.charAt(j-1) || p.charAt(j-1) == '.'){
                        dp[i+1][j+1] = dp[i][j+1] || dp[i+1][j] || dp[i+1][j-1];
                    }
                }
            }
        }
        return dp[s.length()][p.length()];
    }
    // 有问题，c*a*b* 字符串aab匹配不了
    public boolean isMatch2(String s, String p) {
        int posS = 0, posP = 0;
        while(posS < s.length() && posP < p.length()){
            if(s.charAt(posS) == p.charAt(posP)){
                if(posP+1<p.length() && p.charAt(posP+1) == '*'){
                    if(posS+1 < s.length() && s.charAt(posS+1)==p.charAt(posP)){
                        posS++;
                    }
                    else{
                        posS++;
                        posP += 2;
                    }
                }
                else{
                    posS++;
                    posP++;
                }
            }
            else if(p.charAt(posP) == '.'){
                if(posP+1 < p.length() && p.charAt(posP+1) == '*'){
                    if(posP+2>=p.length())
                        return true;
                    else if(posS+1 < s.length() && s.charAt(posS+1) != p.charAt(posP+2)){
                        posS++;
                    }
                    else{
                        posS++;
                        posP +=2;
                    }
                }
                else{
                    posP++;
                    posS++;
                }
            }
            else if(posP+1<p.length()&&p.charAt(posP+1) == '*'){
                if(posS+1 < s.length() && s.charAt(posS+1)==p.charAt(posP)){
                    posS++;
                }
            }
            else{
                return false;
            }
        }
        return posP == p.length() && posS == s.length();
    }

    // 11. 盛最多水的容器,暴力法，时间复杂度为O(N^2)
    public int maxArea(int[] height) {
        int maxWater = 0;
        for(int i = 0; i < height.length; i++){
            for(int j = i + 1; j < height.length; j++){
                maxWater = Math.max(maxWater, (j-i)*Math.min(height[i], height[j]));
            }
        }
        return maxWater;
    }
    // 11.双指针法，时间复杂度为O(n)
    public int maxArea2(int[] height) {
        int maxWater = 0;
        int p = 0, q = height.length-1;
        while(q > p){
            maxWater = Math.max(maxWater, (q-p)*Math.min(height[p], height[q]));
            if(height[q] > height[p])
                p++;
            else
                q--;
        }
        return maxWater;
    }

    // 12. 整数转罗马数字
    public String intToRomanNew(int num) {
        int[] index = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] ssr = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder rtn = new StringBuilder();
        for(int i = 0; i < index.length && num > 0; i++){
            while(num >= index[i]){
                rtn.append(ssr[i]);
                num -= index[i];
            }
        }
        return rtn.toString();
    }
    public String intToRoman(int num) {
        HashMap<Integer, Character> myMap = new HashMap<>();
        myMap.put(1,'I');
        myMap.put(5,'V');
        myMap.put(10,'X');
        myMap.put(50,'L');
        myMap.put(100,'C');
        myMap.put(500,'D');
        myMap.put(1000,'M');
        StringBuilder rtn = new StringBuilder();
        int aa = 1, bb = 0;
        while(num != 0){
            bb = num % 10;
            num /= 10;
            if(bb > 0 && bb < 4)
                rtn.insert(0, String.join("", Collections.nCopies(bb, myMap.get(aa).toString())));
            else if(bb == 4)
                rtn.insert(0, "" + myMap.get(aa) + myMap.get(aa*5));
            else if(bb > 4 && bb < 9)
                rtn.insert(0, "" + myMap.get(aa*5) + String.join("", Collections.nCopies(bb-5, myMap.get(aa).toString())));
            else if(bb == 9)
                rtn.insert(0, "" + myMap.get(aa) + myMap.get(aa*10));
            aa *= 10;
        }
        return rtn.toString();
    }

    // 13. 罗马数字转整数
    //使用case,左减右加
    public int romanToInt(String s){
        int myNum = 0, num = 0;
        int preNum = getNum(s.charAt(0));
        if(s.length() < 2)
            return preNum;
        for(int i = 1; i < s.length(); i++){
            num = getNum(s.charAt(i));
            if(preNum < num)
                myNum -= preNum;
            else
                myNum += preNum;
            preNum = num;
        }
        return myNum + preNum;
    }
    public int getNum(char a){
        switch (a){
            case 'M': return 1000;
            case 'D': return 500;
            case 'C': return 100;
            case 'L': return 50;
            case 'X': return 10;
            case 'V': return 5;
            case 'I': return 1;
            default:return 0;
        }
    }
    public int romanToInt2(String s) {
        int myNum = 0, i = 1;
        HashMap<String,Integer> fi = new HashMap<String,Integer>(){
            {
                put("M",1000);put("CM",900);put("D",500);put("CD",400);
                put("C",100);put("XC",90);put("L",50);put("XL",40);
                put("X",10);put("IX",9);put("V",5);put("IV",4);put("I",1);
            }
        };
        if(s.length() < 1)
            return 0;
        for(; i < s.length(); i++){
            if(fi.get(s.substring(i-1, i+1)) == null)
                myNum += fi.get(Character.toString(s.charAt(i-1)));
            else{
                myNum += fi.get(s.substring(i-1, i+1));
                i++;
            }
        }
        if(i > s.length())
            return myNum;
        myNum += fi.get(Character.toString(s.charAt(i-1)));
        return myNum;
    }

    // 14. 最长公共前缀
    public String longestCommonPrefix(String[] strs) {
        StringBuilder sb = new StringBuilder();
        char nChar;
        int i = 0, j = 0;
        for(i = 0; i < strs[0].length(); i++){
            nChar = strs[0].charAt(i);
            for(j = 1; j < strs.length; j++){
                if(i >= strs[j].length() || strs[j].charAt(i) != nChar)
                    return sb.toString();
            }
            if(j == strs.length)
                sb.append(nChar);
        }
        return sb.toString();
    }

    // 15. 三数之和
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> rtn = new ArrayList<>();
        if(nums.length < 3)
            return rtn;
        Arrays.sort(nums);
        int m, n;
        for(int i = 0; i < nums.length; i++){
            if(nums[i] > 0)
                return rtn;
            if(i>0 && nums[i]==nums[i-1])
                continue;
            m = i + 1;
            n = nums.length - 1;
            while(m < n){
                if(nums[i]+nums[m]+nums[n] == 0){
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[m]);
                    list.add(nums[n]);
                    rtn.add(list);
                    while(m < n && nums[m+1] == nums[m]){
                        m++;
                    }
                    while(m < n && nums[n-1] == nums[n]){
                        n--;
                    }
                    m++;n--;
                }
                else if(nums[i]+nums[m]+nums[n] > 0)
                    n--;
                else
                    m++;
            }
        }
        return rtn;
    }

    // 16. 最接近的三数之和
    // 双指针法
    public int threeSumClosest2(int[] nums, int target) {
        int cloSum = 0;
        int nSum;
        Arrays.sort(nums);
        int m, n;
        for(int i = 0; i < nums.length; i++){
            if(i > 0 && nums[i]==nums[i-1])
                continue;
            m = i + 1;
            n = nums.length - 1;
            while(m < n) {
                nSum = nums[i] + nums[m] + nums[n];
                if (nSum == target) {
                    return nSum;
                }
                else{
                    cloSum = Math.abs(nSum - target) < Math.abs(cloSum - target) ? nSum : cloSum;
                    if (nSum > target)
                        n--;
                    else
                        m++;
                }
            }
        }
        return cloSum;
    }
    // 暴力法
    public int threeSumClosest(int[] nums, int target) {
        int cloSum = 0;
        int nSum = 0;
        if(nums.length < 3)
            return cloSum;
        Arrays.sort(nums);
        cloSum = nums[0] + nums[1] + nums[2];
        for(int i = 0; i < nums.length; i++){
            for(int j = i+1; j < nums.length; j++){
                for(int k = j+1; k < nums.length; k++) {
                    nSum = nums[i] + nums[j] + nums[k];
                    cloSum = Math.abs(nSum - target) < Math.abs(cloSum - target) ? nSum : cloSum;
                }
            }
        }
        return cloSum;
    }

    // 20.有效括号
    public boolean isValid(String s) {
        Stack<Character> st = new Stack<>();
        for (int i = 0; i < s.length(); i++){
            if(!st.empty() && ((s.charAt(i) == ')' && st.peek() == '(') || (s.charAt(i) == ']' && st.peek() == '[') || (s.charAt(i) == '}' && st.peek() == '{')))
                st.pop();
            else if(s.charAt(i) == ')' || s.charAt(i) == ']' || s.charAt(i) == '}')
                return false;
            else
                st.push(s.charAt(i));
        }
        return st.empty();
    }

    // 21. 合并两个有序链表
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode headPro = head;
        while(l1 != null || l2 !=null){
            if(l1 == null)
                head.next = l2;
            else if(l2 == null)
                head.next = l1;
            else if(l1.val <= l2.val){
                head.next = l1;
                l1 = l1.next;
            }
            else {
                head.next = l2;
                l2 = l2.next;
            }
            head = head.next;
        }
        return headPro.next;
    }

    // 23. 合并K个排序链表
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode head = new ListNode(0);
        ListNode headPro = head;
        ArrayList<Integer> mid = new ArrayList<>();
        for(ListNode list : lists){
            while(list != null) {
                mid.add(list.val);
                list = list.next;
            }
        }
        Collections.sort(mid);
        for(int i :mid){
            head.next = new ListNode(i);
            head = head.next;
        }
        return headPro.next;
    }

    // 26. 删除排序数组中的重复项
    public int removeDuplicates(int[] nums) {
        if(nums.length < 1)
            return 0;
        int preNum = nums[0],count = 1;
        for(int i = 0; i < nums.length;i++){
            if(nums[i] != preNum){
                preNum = nums[i];
                nums[count++] = preNum;
            }
        }
        return count;
    }

    // 33. 搜索旋转排序数组
    public int search(int[] nums, int target) {
        if(nums.length < 1)
            return -1;
        int left = 0, right = nums.length-1;
        int mid = (left+right)/2;
        do{
            if(nums[mid] == target)
                return mid;
            if(nums[left] <= nums[mid]) { // 左边是升序
                if(target < nums[mid] && target >= nums[left])
                    right = mid - 1;
                else
                    left = mid + 1;
            }
            else{ // 右边升序
                if(target > nums[mid] && target <= nums[right])
                    left = mid + 1;
                else
                    right = mid - 1;
            }
            mid = (left+right)/2;
        }while(left <= right);
        return -1;
    }
}
