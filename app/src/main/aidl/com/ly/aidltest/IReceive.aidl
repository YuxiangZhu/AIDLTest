package com.ly.aidltest;
import com.ly.aidltest.Student;
interface IReceive {
    void onNewStudentAdded(in Student stu);
}
