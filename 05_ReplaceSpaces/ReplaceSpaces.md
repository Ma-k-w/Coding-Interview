# 题目：替换空格
请实现一个函数，把字符串中的每个空格替换成"%20"。例如输入“We are happy.”，则输出“We%20are%20happy.”。
### 1、从前往后扫描
思路：这是比较普通的做法。我们从头到尾遍历字符串，遇到空格，就把空格后面的字符往后挪动，然后将空格处替换。且不说扩容和越界问题，每次遇到空格我们都需要移动空格后面的字符，因此总的时间复杂度为O(n^2)，造成越后面的字符移动次数越多，这显然是多余的。
### 2、从后往前扫描
思路：既然从前往后扫描的做法不好，那我们就试试从后往前的。
1. 首先变量一遍字符串，统计空格的数量，由此我们可以得到替换后的字符长度并创建这样一个字符数组。
2. 从后往前遍历字符串，每当遇到空格，则在新数组的末尾添加“%20”，否则添加原字符。
```java
    public String replaceBlank(String oldStr) {
        if (oldStr == null)
            return null;
            
//      统计空格数量
        int blank = 0;
        for (int i = 0; i < oldStr.length(); i++) {
            if (oldStr.charAt(i) == ' ')
                blank++;
        }
        
//      计算替换后的长度
        int newLen = (blank << 1) + oldStr.length();
        char[] newChar = new char[newLen];

//      从后往前扫描
        for (int i = oldStr.length() - 1; i >= 0; i--) {
            if (oldStr.charAt(i) == ' ') {
                newChar[--newLen] = '0';
                newChar[--newLen] = '2';
                newChar[--newLen] = '%';
            } else {
                newChar[--newLen] = oldStr.charAt(i);
            }
        }
        
        return new String(newChar);
```
分析：每个字符只会被移动一次，时间复杂度为O(n)。
### 3、使用Java封装的方法
思路：更简便的方法是直接使用Java为我们提供的方法，但是绕过了算法的具体实现，仅作为了解。掌握其背后的底层原理才是我们需要的。
```java
//  逐个添加
    public String replaceBlank(String oldStr) {
        if (oldStr == null)
            return null;
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < oldStr.length(); i++) {
            if (oldStr.charAt(i) == ' ')
                sb.append("%20");
            else
                sb.append(oldStr.charAt(i));
        }
        return sb.toString();
    }

//  或者逐个替换
    public String replaceBlank(String oldStr) {
        if (oldStr == null)
            return null;
        StringBuilder sb = new StringBuilder(oldStr);
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == ' ')
                sb.replace(i, i + 1, "%20");
        }
        return sb.toString();
    }

//  或者使用正则
    public String replaceBlank(String oldStr) {
        if (oldStr == null)
            return null;
        return oldStr.replaceAll("\\s", "%20");
    }
```
# 举一反三

有两个==有序==的数组A和B，A末尾==有足够空间==容纳B。实现一个函数，将B的所有数字插入到A1中，并且所有数字是有序的。
思路：因为A数组有足够空间容纳B，因此我们可以直接在A上进行操作。依然使用从后往前复制的方法，这样能减少移动的次数，从而提高效率。
```java
    /**
     * @param a a数组
     * @param m a中的元素个数（不包括空余空间）
     * @param b b数组
     * @param n b中的元素个数
     * @return
     */
    public static int[] func(int[] a, int m, int[] b, int n) {
        if (a == null || a.length == 0)
            return b;
        if (b == null || b.length == 0)
            return a;
//      新数组的长度。
        int newLen = m-- + n-- - 1;
        while (m >= 0 && n >= 0)
            a[newLen--] = a[m] > b[n] ? a[m--] : b[n--];
//      若a数组遍历先完成，说明b中剩余的元素都是较小值，只要把b中的元素移动到a中可。
//      若b数组遍历先完成，说明a中剩余的元素都是较小值，无需排序，直接输出。
        while (n >= 0)
            a[newLen--] = b[n--];
        return a;
    }
```
分析：合并两个有序数组其实有一种很重要的方法。在归并排序就是用这种方法完成合并的。
# 总结
需要充分理解数组和字符串，特别要注意越界和内存的问题。其次解决问题的关键是扫描的方法，从前往后和从后往前扫描的时间效率完全不一样，要特别注意和理解区别和本质。