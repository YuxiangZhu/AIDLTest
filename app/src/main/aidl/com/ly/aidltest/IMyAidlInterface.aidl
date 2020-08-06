// IMyAidlInterface.aidl
package com.ly.aidltest;
import com.ly.aidltest.Student;
import com.ly.aidltest.IReceive;

// Declare any non-default types here with import statements
/**除了默认的基本类型，就是下面直接生成出来的方法自带的这6种类型不需要import之外，其他的都需要手动import
* 其他类型包括下面几种:
*List 接口（会自动将List接口转为 ArrayList）,且集合的每个元素都必须要么是基本类型，要么是Parcelable实现类
*Map 接口（会自动将 Map 接口转为 HashMap），且每个元素的 key 和 value 要么是基本类型，要么是Parcelable实现类
*Parcelable 的实现类
*AIDL 接口本身
*/


interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    //定义两个方法，也就是提供给客户端的服务
    List<Student> getStudent();
    //这里的in 是有讲究的
    void addStudent(in Student stu);

    void registerListener(IReceive listener);
    void unregisterListener(IReceive listener);
}
