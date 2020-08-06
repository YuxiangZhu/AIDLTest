package com.ly.aidltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private  IMyAidlInterface sub;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sub =  IMyAidlInterface.Stub.asInterface(service);
            try {
                List<Student> list = sub.getStudent();
                for(Student s : list){
                    Log.e(TAG, "onServiceConnected: "+s.getName()+" age:"+s.getAge());
                }
                sub.registerListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sub!=null){
                    try {
                        sub.addStudent(new Student("新来的",20));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Intent intent = new Intent();
        //5.0之后如果使用隐式启动service需要加上package
        intent.setPackage(getPackageName());
        intent.setAction("com.ly.aidltest.MyService");
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    private IReceive.Stub listener = new IReceive.Stub() {
        @Override
        public void onNewStudentAdded(Student stu) throws RemoteException {
            Log.e(TAG, "onNewStudentAdded--threadId"+Thread.currentThread().getId()+" name:"+stu.getName() );

        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(sub!=null){
            try {
                sub.unregisterListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(connection);
    }
}