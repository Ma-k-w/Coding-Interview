public class Singleton {
//	注意volatile关键字
	private volatile Singleton instance = null;

	private Singleton() {
//		可防止反射破解
		if(instance !=null)			
			throw new RuntimeException();
	}
	
	/**
	 * 版本1：
	 * 		懒汉式，线程不安全
	 * 缺点：只适用于单线程。
	 * @return
	 */
	public Singleton Instance_1() {
		if (this.instance == null)
			this.instance = new Singleton();
		return this.instance;
	}
	
	
	/**
	 * 版本2：
	 * 		懒汉式，线程安全
	 * 实现：使用多线程(同时使用volatile关键字)
	 * 缺点：
	 * 		效率低下，每次调用方法都会试图加同步锁，而加同步锁是一件非常耗性能的事。
	 * @return
	 */
	public Singleton Instance_2() {
		synchronized (Singleton.class) {
			if (this.instance == null)
				this.instance = new Singleton();
		}
		return this.instance;
	}
	
	/**
	 *  版本3：
	 *		双检锁/双重校验锁（DCL，即 double-checked locking）
	 * 		线程安全，jdk1.5
	 *  实现：加同步锁前后两次判断实例是否已经存在
	 * 		我们只是在实例还没有创建之前需要加同步锁。而判断出实例已经创建后，就不需要加锁了
	 * 		必须使用volatile，new一个新对象是多个过程（必须保证子线程不发生重排序）
	 * 			详见：https://blog.csdn.net/u012723673/article/details/80682208
	 *  缺点：
	 * 		这样的代码实现起来复杂，且容易出错。
	 * 		在jdk1.5版本前，由于volatile，双重检查锁形式的单例模式是无法保证线程安全的。
	 * @return
	 */
	public Singleton Instance_3() {
		if(this.instance == null) {
			synchronized (Singleton.class) {
				if(this.instance == null)
					this.instance = new Singleton();
			}
		}
		return this.instance;
	}

	/**
	 * 版本4：
	 * 		饿汉式，线程安全，类加载时就被初始化，浪费内存
	 */
	private static Singleton s = new _01_Singleton();		//饿汉关键
	public static Singleton getInstance1() {
		return s;
	}
	
	/**
	 * 版本5：
	 * 		登记式/静态内部类，线程安全
	 * 实现：
	 * 		我们可以把实例放到一个静态内部类中，这样就避免了静态实例在Singleton类加载的时候就创建对象，
	 * 		并且由于静态内部类只会被加载一次，所以这种写法也是线程安全的
	 * 缺点：
	 * 		但是，上面提到的所有实现方式都有两个共同的缺点：
			1.都需要额外的工作(Serializable、transient、readResolve())来实现序列化，
				否则每次反序列化一个序列化的对象实例时都会创建一个新的实例。
    			2.可能会有人使用反射强行调用我们的私有构造器
    				（如果要避免这种情况，可以修改构造器，让它在创建第二个实例的时候抛异常）。
	 */
	private static class Holder{
		private static final Singleton instance = new Singleton(); 
		
	}
	private static Singleton getInstance() {
		return Holder.instance;
	}
	
	/**
	 * 版本6：
	 * 		枚举法，线程安全，jdk1.5，自动支持序列化机制
	 * 
	 *s
	 * @author:
	 * @version：2019年5月7日 下午8:44:16
	 */
	public enum SingletonEnum{
		INSTANCE;
		public void whateverMethod() {};
	}
}
