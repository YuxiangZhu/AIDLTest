package com.ly.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.v4.os.IResultReceiver;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * 作者： Alex
 * 日期： 2020-08-04
 * 签名： 保持学习
 * <p>
 * ----------------------------------------------------------------
 */
public class MyService extends Service {
    private List<Student> list = new ArrayList<>();
    //// aidl 接口专用容器
    private RemoteCallbackList<IReceive>callbackList = new RemoteCallbackList<>();
    private static final String TAG = "MyService";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
        list.add(new Student("小明", 12));
        list.add(new Student("小红", 13));
        list.add(new Student("小军", 14));
    }
    private IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
        @Override
        public List<Student> getStudent() throws RemoteException {

            return list;
        }
        @Override
        public void addStudent(Student stu) throws RemoteException {
            Log.e(TAG, "setStudent: " );
            list.add(stu);
            int size = callbackList.beginBroadcast();
            // 向客户端通信
            for(int i=0;i<size;i++){
                IReceive receiver = (IReceive) callbackList.getBroadcastItem(i);
                receiver.onNewStudentAdded(stu);
            }
            callbackList.finishBroadcast();

        }

        @Override
        public void registerListener(IReceive listener) throws RemoteException {
            callbackList.register(listener);
        }

        @Override
        public void unregisterListener(IReceive listener) throws RemoteException {
            callbackList.unregister(listener);
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: " );
        return mBinder;
    }
}
