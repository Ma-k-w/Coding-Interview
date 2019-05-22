# 题目：不修改数组找出重复的数字
在一个长度为n+1的数组里的所有数字都在1到n的范围内，所以数组中至少有一个数字是重复的。请找出数组中任意一个重复的数字，但不能修改输入的数组。例如，如果输入长度为8的数组{2, 3, 5, 4, 3, 2, 6, 7}，那么对应的输出是重复的数字2或者3。
### 1、使用O(n)的辅助空间
思路：这一题与上一题很相似，但是题目要求我们不能修改原数组。由于数组长度为n+1，数字范围都在1到n之间，因此我们依然可以根据数组下标来对应数字。我们可以创建一个相同长度的辅助数组。遍历原数组将数字和辅助数组比较，这样很容易发现哪个数字是重复的。因为要创建一个辅助数组，该方法需要O(n)的辅助空间。

```java
    public int getDuplication(int[] arr) {
        if (arr == null || arr.length == 0)
            return -1;
        boolean[] temp = new boolean[arr.length];
        for (int number : arr) {
            if (temp[number])
                return number;
            else
                temp[number] = true;
        }
        return -1;
    }
```
### 2、二分法
思路：根据题意，我们得知数组至少有一个重复数字。由于数字都是在1-n范围内的，我们对这个范围进行二分，前一半为1\~m，后一半为m+1~n，m=(1+n)/2。
- 如果在1\~m的范围内，数组中1~m的数字超过了m个，说明该范围内有重复数字。此时我们要对这块范围继续二分。
- 否则，说明重复数在另一个范围，对这个范围进行二分。
- 当范围缩小到某个数时：
    - 如果数组中这个数的统计数量超过了1个，说明这个数是一个重复数。
    - 否则，出错。
![03_02不修改数组找出重复的数字](C:\Users\mkw\Desktop\剑指offer插图\03_02不修改数组找出重复的数字.jpg)
```java
    public int getDuplication(int[] arr) {
        if (arr == null || arr.length == 0)
            return -1;
        int start = 1, end = arr.length - 1;
        while (start <= end) {
//            将数字范围一分为二，注意不是根据数组划分的。
            int mid = ((end - start) >> 1) + start;
//            统计数组中属于区域范围的数字。
            int count = count(arr, start, mid);
            if (end == start){
//            范围精确到某个数，并且这个数在数组中有多个，说明找到了一个重复数。
                if(count>1)
                    return start;
                else
                    break;
            }
//            数组中当前范围内的数字数量大于范围本身，说明该数组中该数字范围有重复数。
            if (count > mid - start + 1) {
                end = mid;
            } else
                start = mid + 1;
        }
//        数据有误。
        return -1;
    }
    
//    统计数字范围内的元素有多少个
    public int count(int[] arr, int start, int end) {
        int count = 0;
        for (int number : arr) {
            if (number >= start && number <= end)
                count++;
        }
        return count;
    }

```
分析：根据二分查找的思路，如果输入长度为n的数组，那么函数count()将被调用O(logn)次，每次需要O(n)的时间，因此总的时间复杂度为O(nlogn)，空间复杂度为O(1)。相比上一种算法，相当于以时间换空间。
# 总结
需要指出的是，前者算法可以微调，实现找出所有重复数字，但后者不能。实际中要根据不同需求（找出任意重复数字？找出所有重复数字？）或者性能要求（时间优先？空间优先？），来选取算法。









