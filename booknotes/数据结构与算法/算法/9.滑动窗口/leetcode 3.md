给定一个字符串 `s` ，请你找出其中不含有重复字符的 **最长子串** 的长度。

**输入:** s = "abcabcbb"
**输出:** 3 
**解释:** 因为无重复字符的最长子串是 `"abc"，所以其`长度为 3。

**输入:** s = "bbbbb"
**输出:** 1
**解释:** 因为无重复字符的最长子串是 `"b"`，所以其长度为 1。

**输入:** s = "pwwkew"
**输出:** 3
**解释:** 因为无重复字符的最长子串是 `"wke"`，所以其长度为 3。
     请注意，你的答案必须是 **子串** 的长度，`"pwke"` 是一个_子序列，_不是子串。

滑动窗口

```java
class Solution {

    public int lengthOfLongestSubstring(String s) {

        Map<Character, Integer> map = new HashMap<>();

        int n = s.length();

        int max = 0;

        int left = 0;

        for(int i = 0;i<n;i++){

            Character c = s.charAt(i);

            if(map.containsKey(c)){
				//所有的难点就在这里，为什么要确定left的最大值，
				//"abba"中，当i指向最右a的时候，前一个a还在map中，这时如果不判断left最大值，left = 1，max = 3，不符合题意
                left = Math.max(left,map.get(c)+1);

            }

            map.put(c,i);

            max = Math.max(max,i - left + 1);

        }

        return max;

    }

}
```