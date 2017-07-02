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

先看ArrayList.Iterator的部分源码,以及ArrayList.remove(Object o)的部分源码
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
...
final void checkForComodification() {
 if (expectedModCount != ArrayList.this.modCount)
     throw new ConcurrentModificationException();
}

````
````
public boolean remove(Object o) {
    if (o == null) {
        for (int index = 0; index < size; index++)
            if (elementData[index] == null) {
                fastRemove(index);
                return true;
            }
    } else {
        for (int index = 0; index < size; index++)
            if (o.equals(elementData[index])) {
                fastRemove(index);
                return true;
            }
    }
    return false;
}

/*
 * Private remove method that skips bounds checking and does not
 * return the value removed.
 */
private void fastRemove(int index) {
    modCount++;
    int numMoved = size - index - 1;
    if (numMoved > 0)
        System.arraycopy(elementData, index+1, elementData, index,
                         numMoved);
    elementData[--size] = null; // clear to let GC do its work
}
````
我们会发现当执行remove(Object o)方法后，ArrayList对象的size减一此时size==4,
modCount++了，然后Iterator对象中的cursor==5，hasNext发回了true，导致增强for循
环去寻找下一个元素调用next()方法，checkForComodification做校验的时候，发现modCount
已经和Iterator对象中的expectedModCount不一致，说明ArrayList对象已经被修改过，
为了防止错误，抛出异常ConcurrentModificationException。
