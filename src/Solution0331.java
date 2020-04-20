import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Solution0331 {
    // 907. 子数组的最小值之和
    // 前驱/后继范围求解
    public int sumSubarrayMins1(int[] A) {
        int n = A.length;
        if (n == 0)
            return 0;
        if (n == 1)
            return A[0];
        int[] left = new int[n], right = new int[n];
        Stack<int[]> s1 = new Stack<>(); //s1.peek()[0]->A[xxx]; s1.peek()[1]->xxx
        // 找左边连续大于元素A[i]的边界
        for (int i = 0; i < n; ++i) {
            while (!s1.empty() && s1.peek()[0] >= A[i])
                s1.pop();
            left[i] = s1.empty() ? -1 : s1.peek()[1];
            s1.push(new int[]{A[i], i});
        }
        Stack<int[]> s2 = new Stack<>(); //s2.peek()[0]->A[xxx]; s2.peek()[1]->xxx
        for (int i = n - 1; i >= 0; --i) {
            while (!s2.empty() && s2.peek()[0] > A[i])
                s2.pop();
            right[i] = s2.empty() ? n : s2.peek()[1];
            s2.push(new int[]{A[i], i});
        }
        int rtn = 0, mod = 1000000007;
        for (int i = 0; i < n; ++i) {
            rtn = (rtn + (right[i] - i) * (i - left[i]) % mod * A[i] % mod) % mod;
        }
        return rtn;
    }

    // 907.维护最小值栈求解
    public int sumSubarrayMins(int[] A) {
        int n = A.length, count = 0, rtn = 0, dot = 0;
        int mod = (int) Math.pow(10, 9) + 7;
        Stack<int[]> stack = new Stack<>();
        int[] temp = new int[2];
        for (int i = 0; i < n; ++i) {
            count = 1;
            while (!stack.empty() && stack.peek()[0] >= A[i]) {
                temp = stack.pop();
                count += temp[1];
                dot -= temp[0] * temp[1] % mod;
            }
            stack.push(new int[]{A[i], count});
            dot += A[i] * count % mod;
            rtn += dot;
            rtn %= mod;
        }
        return rtn;
    }

    // 912. 排序数组-快速排序
    public int[] sortArray(int[] nums) {
//        Arrays.sort(nums);
        quik(nums, 0, nums.length - 1);
        return nums;
    }

    public void quik(int[] nums, int start, int end) {
        if (nums.length < 1 || start < 0 || end >= nums.length || start >= end || start > nums.length || end < 0)
            return;
        int pos = Patition(nums, start, end);
        quik(nums, start, pos - 1);
        quik(nums, pos + 1, end);
    }

    public int Patition(int[] nums, int start, int end) {
        int curPos = start + 1;
        for (int i = start + 1; i <= end; i++) {
            if (nums[i] <= nums[start]) {
                swap(nums, i, curPos);
                curPos++;
            }
        }
        curPos--;
        swap(nums, start, curPos);
        return curPos;
    }

    // 堆排序
    public int[] heapSort(int[] nums) {
        int n = nums.length;
        if (n < 2)
            return nums;
        // 先构建大顶堆
        for (int i = n / 2 - 1; i >= 0; --i) {
            adjust(nums, i, n);
        }
        while (n > 0) {
            swap(nums, 0, n - 1);
            --n;
            adjust(nums, 0, n);
        }
        return nums;
    }

    private void adjust(int[] nums, int i, int length) {
        int left = 2 * i + 1, right = left + 1;
        int maxIndex = i;
        if (left < length && nums[left] > nums[maxIndex])
            maxIndex = left;
        if (right < length && nums[right] > nums[maxIndex])
            maxIndex = right;
        if (i != maxIndex) {
            swap(nums, i, maxIndex);
            adjust(nums, maxIndex, length);
        }
    }

    public void swap(int[] nums, int i, int j) {
        if (i == j)
            return;
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // 1111. 有效括号的嵌套深度
    public int[] maxDepthAfterSplit(String seq) {
        if (seq.length() < 1)
            return new int[]{0};
        int count = 0;
        int[] rtn = new int[seq.length()];
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < seq.length(); ++i) {
            if (seq.charAt(i) == '(') {
                rtn[i] = count;
                count = count == 0 ? 1 : 0;
                stack.push(seq.charAt(i));
            } else {
                count = count == 0 ? 1 : 0;
                rtn[i] = count;
                stack.pop();
            }
        }
        return rtn;
    }

    int[][] isOk;

    // 面试题13. 机器人的运动范围
    public int movingCount(int m, int n, int k) {
        isOk = new int[m][n];
        int count = 0;
        return moving(0, 0, m, n, k);
    }

    public int moving(int i, int j, int m, int n, int k) {
        if (i < 0 || i >= m || j < 0 || j >= n)
            return 0;
        if (isOk[i][j] == 0 && getBitsSum(i, j) <= k) {
            isOk[i][j] = 1;
            return 1 + moving(i - 1, j, m, n, k)
                    + moving(i + 1, j, m, n, k)
                    + moving(i, j - 1, m, n, k)
                    + moving(i, j + 1, m, n, k);
        }
        return 0;
    }

    public int getBitsSum(int x, int y) {
        ArrayList<Integer> array = new ArrayList<>();
        int bitOfX = 0, bitOfY = 0;
        while (x != 0) {
            bitOfX += x % 10;
            x /= 10;
        }
        while (y != 0) {
            bitOfY += y % 10;
            y /= 10;
        }
        return bitOfX + bitOfY;
    }

    // 17. 电话号码的字母组合
    String[] telString = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    ArrayList<String> rtn = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        if (digits.length() < 1)
            return rtn;
        findCombinations(new StringBuilder(""), digits);
        return rtn;
    }

    public void findCombinations(StringBuilder combination, String nextDigits) {
        if (nextDigits.length() == 0)
            rtn.add(combination.toString());
        else {
            int pos = nextDigits.charAt(0);
            String zimu = telString[nextDigits.charAt(0) - '0'];
            for (int i = 0; i < zimu.length(); ++i) {
                combination.append(telString[nextDigits.charAt(0) - '0'].charAt(i));
                findCombinations(combination, nextDigits.substring(1));
                combination.delete(combination.length() - 1, combination.length());
            }
        }
    }

    // 18. 四数之和
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> rtn = new ArrayList<>();
        if (nums.length < 4)
            return rtn;
        Arrays.sort(nums);
        int len = nums.length;
        for (int i = 0; i < len - 3; ++i) {
            if (i > 0 && nums[i] == nums[i - 1])
                continue;
            if (nums[i] + nums[len - 1] + nums[len - 2] + nums[len - 3] < target)
                continue;
            if (nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target)
                continue;
            for (int j = i + 1; j < len - 2; ++j) {
                if (nums[i] + nums[j] + nums[len - 1] + nums[len - 2] < target)
                    continue;
                if (nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target)
                    continue;
                int m = j + 1, n = nums.length - 1;
                while (m < n) {
                    int curSum = nums[i] + nums[j] + nums[m] + nums[n];
                    if (curSum == target) {
                        rtn.add(Arrays.asList(nums[i], nums[j], nums[m], nums[n]));
                        ++m;
                        while (m < n && nums[m] == nums[m - 1])
                            ++m;
                        --n;
                        while (m < n && nums[n] == nums[n + 1])
                            --n;
                    } else if (curSum > target) {
                        --n;
                        while (m < n && nums[n] == nums[n + 1])
                            --n;
                    } else {
                        ++m;
                        while (m < n && nums[m] == nums[m - 1])
                            ++m;
                    }
                }
            }
        }
        return rtn;
    }

    // 19. 删除链表的倒数第N个节点
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null)
            return null;
        ListNode nHead = new ListNode(0), p = nHead, q = nHead;
        nHead.next = head;
        for (int i = 0; i < n; ++i)
            p = p.next;

        while (p != null && p.next != null) {
            p = p.next;
            q = q.next;
        }
        q.next = q.next.next;
        return nHead.next;
    }

    // 22. 括号生成
    // (1) 回溯法-剪枝
    public List<String> generateParenthesis(int n) {

        List<String> rtn = new ArrayList<>();
        if (n < 1)
            return rtn;
        generateParenthesisPro(n, n, new StringBuilder(), rtn);
        return rtn;
    }

    public void generateParenthesisPro(int left, int right, StringBuilder curPar, List<String> rtn) {
        if (left == 0 && right == 0) {
            rtn.add(curPar.toString());
            return;
        }
        if (left > 0) {
            curPar.append('(');
            generateParenthesisPro(left - 1, right, curPar, rtn);
            curPar.deleteCharAt(curPar.length() - 1);
        }
        if (right > 0 && left < right) {
            curPar.append(')');
            generateParenthesisPro(left, right - 1, curPar, rtn);
            curPar.deleteCharAt(curPar.length() - 1);
        }
    }
}
