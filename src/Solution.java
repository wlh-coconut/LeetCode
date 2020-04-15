import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class Solution {
    // 1.两数之和
    public int[] twoSum(int[] nums, int target) {
        int rst[] = new int[2];
        for(int i = 0; i < nums.length; ++i){
            for(int j = i + 1; j < nums.length; ++j){
                if(nums[i] + nums[j] == target){
                    rst[0] = i;
                    rst[1] = j;
                    return rst;
                }
            }
        }
        return rst;
    }
    // 2.两数相加
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        int sum = 0;
        ListNode head = new ListNode(0);
        ListNode cur = head;
        while(l1 != null || l2 != null || carry > 0) {
            sum = carry;
            if(l1 != null){
                sum += l1.val;
                l1 = l1.next;
            }
            if(l2 != null){
                sum += l2.val;
                l2 = l2.next;
            }
            carry = sum / 10;
            cur.next = new ListNode(sum % 10);
            cur = cur.next;
        }
        return head.next;
    }
    // 3. 无重复字符的最长子串
    public int lengthOfLongestSubstring(String s) {
        if(s.length() == 0)
            return 0;
        HashMap<Character,Integer> hm = new HashMap<>();
        int head = 0, length = 1, maxLength = 1;
        hm.put(s.charAt(head), 1);
        for(int cur = 1; cur < s.length();){
            if(hm.get(s.charAt(cur)) == null){
                hm.put(s.charAt(cur), 1);
                length++;
                cur++;
            }
            else{
                hm.remove(s.charAt(head));
//                hm.put(s.charAt(head), 0);
                head++;
                length--;
            }
            maxLength = maxLength>=length? maxLength: length;
        }
        return maxLength;
    }

    // 4.寻找两个有序数组的中位数
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length, len2 = nums2.length;
        int sumLen = len1 + len2;
        int midPos, nextPos, sum = 0;
        if(sumLen % 2 == 1){
            midPos = sumLen / 2;
            nextPos = midPos;
        }
        else{
            nextPos = sumLen / 2;
            midPos = nextPos - 1;
        }
        for(int i=0, j=0, curLen=0; (i<len1 || j<len2) && curLen <= nextPos;){
            if(j >= len2 || (i<len1 && nums1[i] <= nums2[j])){
                if(curLen == midPos || curLen == nextPos)
                    sum += nums1[i];
                i++;
            }
            else if(j<len2){
                if(curLen == midPos || curLen == nextPos)
                    sum += nums2[j];
                j++;
            }
            curLen++;
        }
        if(sumLen % 2 == 1)
            return sum;
        else
            return sum / 2.0;
    }

    // 5.最长回文子串
    public String longestPalindrome(String s) {
        int maxL = -1, maxR = -1;
        for(int i = 0; i<s.length(); ++i){
            int len1 = maxLenOfPalindrome(s, i, i);
            int len2 = maxLenOfPalindrome(s, i, i+1);
            int len = Math.max(len1, len2);
            if(len > maxR-maxL+1){
                maxL = i - (len - 1) / 2;
                maxR = i + len / 2;
            }
        }
        return s.substring(maxL, maxR+1);
    }
    // 返回以某中心的回味最大长度
    public int maxLenOfPalindrome(String s, int L, int R) {
        int i = L, j = R;
        if(i<0 || j>=s.length() || s.charAt(i)!=s.charAt(j))
            return -1;
        while(i>=0 && j<s.length() && s.charAt(i)==s.charAt(j)){
            --i;
            ++j;
        }
        return j - i - 1;
    }
    // 6. Z字型变换
    public String convert(String s, int numRows) {
        if(s == null)
            return "";
        if(numRows <= 1)
            return s;
        int circle = numRows * 2 - 2;
        char[] rtn = new char[s.length()];
        int pos = 0;
        for(int j = 0; j < numRows; ++j){
            for(int i = 0;i < s.length(); ++i){
                if(i % circle == j || i % circle == circle - j)
                    rtn[pos++] = s.charAt(i);
            }
        }
        return String.valueOf(rtn);
    }
    public String convert2(String s, int numRows) {
        if(s == null)
            return "";
        if(numRows <= 1)
            return s;
        int circle = numRows * 2 - 2;
        char[] rtn = new char[s.length()];
        int pos = 0;
        for(int j = 0; j < numRows; ++j){
            
        }
        return String.valueOf(rtn);
    }

    // 322.零钱兑换-自底向上的动态规划
    public int coinChange(int[] coins, int amount) {
        if(amount <= 0)
            return 0;
        int[] f = new int[amount+1];
        Arrays.fill(f, amount + 1);
        f[0] = 0;
        for(int i = 1; i <= amount; i++){
            for(int j = 0; j < coins.length; j++){
                if(i >= coins[j])
                    f[i] = Math.min(f[i], f[i-coins[j]]+1);
            }
        }
        if(f[amount] > amount)
            return -1;
        return f[amount];
    }

    // 322.零钱兑换-贪心+剪枝 因为剪枝了所以更快
    int ans = Integer.MAX_VALUE;
    public void coinChange2(int[] coins, int amount, int n_coin, int count) {
        if(amount == 0){
            ans = Math.min(ans, count);
            return;
        }
        if(n_coin < 0) return;
        for(int c = amount/coins[n_coin]; c>=0 && c+count<ans; --c){
            coinChange2(coins, amount-c*coins[n_coin], n_coin-1, count+c);
        }
    }
    public int coinChange2(int[] coins, int amount) {
        if(amount <= 0)
            return 0;
        ans = Integer.MAX_VALUE;
        Arrays.sort(coins);
        coinChange2(coins,amount,coins.length-1,0);
        return ans == Integer.MAX_VALUE? -1 : ans;
    }

    // 面试题 01.06. 字符串压缩 使用StringBuilder而非String，因对字符串进行大量修改操作
    public String compressString(String S) {
        StringBuilder sb = new StringBuilder();
        char[] cs = S.toCharArray();
        int count = 0;
        for(int i = 0; i < cs.length; ++i){
            ++count;
            if(i == cs.length - 1 || cs[i] != cs[i + 1]){
                sb.append(cs[i]).append(count);
                count = 0;
            }
        }
        if(sb.length() >= S.length()) return S;
        return sb.toString();
    }
}

// Definition for singly-linked list.
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}
