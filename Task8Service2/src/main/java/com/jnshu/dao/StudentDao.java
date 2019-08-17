package com.jnshu.dao;

import com.jnshu.pojo.Student;

import java.util.List;

public interface StudentDao {
    //增加
    Long addStudent(Student student);
    //删除
    Boolean deleteStudentByName(String name);
    //更新
    Boolean updateStudentById(Student student);
    //靠name查询
    Student findStudentByName(String name);
    //靠id查询
    Student findStudentById(Long student_id);
    //查询
    List<Student> findAll();
}
