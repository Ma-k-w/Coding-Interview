# 简介

单例模式（Singleton Pattern）是 Java 中最简单的设计模式之一。这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。 这种模式涉及到一个单一的类，该类负责创建自己的对象，同时确保只有单个对象被创建。这个类提供了一种访问其唯一的对象的方式，可以直接访问，不需要实例化该类的对象。

**注意**：

1. 单例类只能有一个实例。
2. 单例类必须自己创建自己的唯一实例。
3. 单例类必须给所有其他对象提供这一实例。

# 单例模式的6种实现方式


### 1、懒汉式(单线程版)[不可用]
是否 Lazy Loading：是(在类加载的时候未完成实例化)

是否多线程安全：否

描述：这种方式是最基本的实现方式。这种实现最大的问题就是不支持多线程。

```java
public class Singleton {  
    private static Singleton instance;  
    private Singleton (){}  
  
    public static Singleton getInstance() {  
        if (instance == null)
            instance = new Singleton();    
        return instance;  
    }  
}
```
### 2、懒汉式(多线程版）[不推荐使用]

是否 Lazy Loading：是

是否多线程安全：是

描述：每次执行getInstance()方法都要加同步锁。而加同步锁是一件非常消耗时间和性能的工作，严重影响效率。我们只有在没有实例化对象时才需要加锁，否则直接返回对象即可。在下一个单例模式实现方法中我们将对此进行优化。

```java
public class Singleton { 
    private static Singleton instance;
    private Singlenton(){}
    
    public static Singleton getInstance(){
        synchronized(Singleton.class){
            if(instance == null)
                instance = new Singlenton();
        }
        return instance;
    }
}
```

### 3、双检锁/双重校验锁（DCL，即 double-checked locking）[推荐使用]
是否 Lazy Loading：是

是否多线程安全：是

JDK版本：1.5。
```java
public class Singleton { 
//    必须使用volatile，new一个新对象是多个过程，必须保证该过程不发生重排序。
    private volatile static Singleton instance;
    private Singlenton(){}
    
    public static Singleton getInstance(){
        if( instance == null){
            synchronized(Singleton.class){
                if(instance == null)
                    instance = new Singlenton();
            }
        }
        return instance;
    }
}
```

### 4、 饿汉式[可使用]
是否 Lazy Loading：否（类加载时已经完成初始化，浪费内存）

是否多线程安全：是

描述：利用类加载机制。缺点是类加载后就一直存在，如果一直未使用，则会浪费内存。
```java
public class Singleton{
    private final static Singleton instance = new Singleton();
    private Singleton() = {}; 
    
    public static Singleton getInstance(){
        return instance;
    }
}
```

###  5、静态内部类法[推荐使用]
是否 Lazy Loading：是

是否多线程安全：是

描述：利用的依然是类加载的机制。并且静态内部类避免了静态实例在Singleton类加载的时候就创建对象，并且由于静态内部类只会被加载一次，所以这种写法也是线程安全的。
```java
public class Singleton{
    priavte Singleton(){}

    private static class SingletonInstance{
        praivate static final Singleton instance = new Singleton(); 
    }
    public static Singleton getInstance(){
        return SingletonInstance.instance;
    }
}
```



上面提到的所有实现方式都有两个共同的缺点：

- 都需要额外的工作(Serializable、transient、readResolve())来实现序列化，否则每次反序列化一个序列化的对象实例时都会创建一个新的实例。
- 可能会有人使用反射强行调用我们的私有构造器（如果要避免这种情况，可以修改构造器，让它在创建第二个实例的时候抛异常）。

当然，还有一种更加优雅的方法来实现单例模式，那就是枚举法。


### 6、枚举法
是否 Lazy Loading：是

是否多线程安全：是

JDK版本：1.5

描述：这种实现方式还没有被广泛采用，但这是实现单例模式的最佳方法。它更简洁，自动支持序列化机制，绝对防止多次实例化。这种方式是 Effective Java 作者 Josh Bloch 提倡的方式，它不仅能避免多线程同步问题，而且还自动支持序列化机制，防止反序列化重新创建新的对象，绝对防止多次实例化。不过，由于 JDK1.5 之后才加入 enum 特性，用这种方式写不免让人感觉生疏，在实际工作中，也很少用。
```java
public enum Singleton {
    INSTANCE;
    public void whateverMethod() {}
}
```



# 总结
单例模式的实现有多种方法，在实际中要根据实际需求还决定实现方案。
1. 不需要考虑内存时，使用方法4饿汉式。
2. 需要考虑内存时，使用方法5静态内部类法。
3. 需要考虑反序列化时，使用方法6枚举法。
4. 有其他特定需求时，使用方法3双检锁法。
