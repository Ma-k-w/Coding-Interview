# 题目：找出数组中重复的数字
在一个长度为n的数组里的所有数字都在0到n-1的范围内。数组中某些数字是重复的，但不知道有几个数字重复了， 也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。例如，如果输入长度为7的数组{2, 3, 1, 0, 2, 5, 3}，  那么对应的输出是重复的数字2或者3。
### 1、排序法
分析：最容易想到的就是先对数组进行排序，再对数组进行一遍遍历，比较相邻的元素是否相等。时间复杂度为O(nlogn)。
```java
    public boolean duplicate(int[] arr) {
        if (arr == null || arr.length == 0)
            return false;
        Arrays.sort(arr);
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == arr[i + 1])
                return true;
        }
        return false;
    }
```
### 2、利用Set集合
分析：因为要判断每个元素是否只有一个，使用Set集合来判断也是不错的选择。只要遍历数组每个元素，如果Set中已经有该元素，说明有重复，直接返回true；如果没有该元素，则添加进Set。时间复杂度是O(n)，但是消耗了O(n)的空间作为代价。
```java
public boolean duplicate(int[] arr) {
   if (arr == null || arr.length == 0)
      return false;

   Set<Integer> set = new HashSet<>();
   for (int i = 0; i < arr.length; i++) {
      if (set.contains(arr[i]))
         return true;
      else
         set.add(arr[i]);
   }
   return false;
}
```
### 3、空间复杂度为O(1)的解法
思路：重新分析题干，我们发现：这是一个长度为n的数组，并且元素都在0到n-1之间。也就是说，如果这是数组中没有重复的元素，那么元素中存储的就是数组的下标，只不过可能是乱序的。 我们只需将每一个数字放到它应该存放的下标上即可。
遍历数组，先将当前下标 i 与 arr[i] 比较:
- 如果不相等，将 arr[i] 与 arr[arr[i]] 比较:
     - 如果不相等，则将这两个数字进行交换。
     - 如果相等，则说明有重复数字。
 - 如果相等：
   - 跳过，进入下一轮循环。
![03_01找出数组中重复的数字](https://github.com/Ma-k-w/Coding-Interview/blob/master/pic/03_01%E6%89%BE%E5%87%BA%E6%95%B0%E7%BB%84%E4%B8%AD%E9%87%8D%E5%A4%8D%E7%9A%84%E6%95%B0%E5%AD%97.jpg )
```java
    public boolean duplicate(int[] arr) {
        if(arr == null || arr.length == 0)
            return false;
        for(int i = 0;i<arr.length;i++) {
//            相等时，说明该数字已经在属于它的位置上。继续下一轮循环。
            while(arr[i] != i) {
//                相等时，说明已找到数组中有相同数字。否则，交换两个数字。
                if(arr[i] == arr[arr[i]]) 
                    return true;
                else {
                    int temp = arr[i];
                    arr[i] = arr[temp];
                    arr[temp] = temp;
                }
            }
        }
        return false;
    }
```
分析：代码中尽管有一个两重循环，但每个数字最多只要交换两次就能找到属于它自己的位置，因此总的时间复杂度是O(n)。另外，所有的操作步骤都是在输入数组上进行的，不需要额外分配内存，因此空间复杂度为O(1)。
# 总结
- 考查对一维数组的理解和编程能力。该题中，我们通过过题干分析、规律总结，发现可以根据下标定位对应的元素来查找重复数字，这是解题的突破口。
