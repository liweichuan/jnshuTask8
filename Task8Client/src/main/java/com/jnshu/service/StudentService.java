package com.jnshu.service;

import com.jnshu.pojo.Student;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**这里student服务层的接口功能和Dao层一样，实现学员报名系统
 * */
@MapperScan
public interface StudentService {
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
