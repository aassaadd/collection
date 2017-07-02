相信不少同学在处理List的时候遇到过下面的Exception，
````
Exception in thread "main" java.util.ConcurrentModificationException
	at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:859)
	at java.util.ArrayList$Itr.next(ArrayList.java:831)
````
话不多说，接下来列举几个例子说明问题并且分析其原因。

#### 例一

````
package main.java.mo.basic;

import java.util.ArrayList;

/**
 * Created by MoXingwang on 2017/7/2.
 */
public class ConcurrentModificationExceptionTest {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        strings.add("e");

        for (String string : strings) {
            if ("e".equals(string)) {
                strings.remove(string);
            }
        }
    }
}
````

* 执行结果

````
Exception in thread "main" java.util.ConcurrentModificationException
	at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:859)
	at java.util.ArrayList$Itr.next(ArrayList.java:831)
	at main.java.mo.basic.ConcurrentModificationExceptionTest.main(ConcurrentModificationExceptionTest.java:17)
````

* 分析原因

首先我们知道增强for循环其实现原理就是Iterator接口，这一点非常重要，有了个这个知识点
我们才能分析为什么会出现异常，这个知识点也是最重要最核心的。

根据上面的异常信息可以看出，异常是从"for (String string : strings) {"，这一行抛
出的，这一行怎么会出错呢？理解增强for的实现原理了，我们就会知道，执行这一行代买的时候
会调用Iterator实现类的两个方法，hasNext()和next(),所以说这个知识点是最重要最核心
的。

先看ArrayList.Iterator的部分源码
````
int cursor;       // index of next element to return
int lastRet = -1; // index of last element returned; -1 if no such
int expectedModCount = modCount;

public boolean hasNext() {
    return cursor != size;
}

@SuppressWarnings("unchecked")
public E next() {
    checkForComodification();
    int i = cursor;
    if (i >= size)
        throw new NoSuchElementException();
    Object[] elementData = ArrayList.this.elementData;
    if (i >= elementData.length)
        throw new ConcurrentModificationException();
    cursor = i + 1;
    return (E) elementData[lastRet = i];
}
````
