package com.jnshu.pojo;

import lombok.Data;

import java.io.Serializable;

/**这里是报名贴信息
 * */

@Data
public class Student implements Serializable {
    private Long id;
    private String name;
    private Long qq;
    private String type;
    private Long start_time;
    private String graduation;
    private Long student_id;
    private String link;
    private String wish;
    private String senior;
    private int status;
    private String create_by;
    private String update_by;
    private Long create_time;
    private Long update_time;
    //getter&getter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQq() {
        return qq;
    }

    public void setQq(Long qq) {
        this.qq = qq;
    }

    public Long getStart_time() {
        return start_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }

    public String getGraduation() {
        return graduation;
    }

    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }

    public String getSenior() {
        return senior;
    }

    public void setSenior(String senior) {
        this.senior = senior;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public Long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Long update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", qq=" + qq +
                ", start_time=" + start_time +
                ", graduation='" + graduation + '\'' +
                ", student_id=" + student_id +
                ", link='" + link + '\'' +
                ", wish='" + wish + '\'' +
                ", senior='" + senior + '\'' +
                ", status=" + status +
                ", create_by='" + create_by + '\'' +
                ", update_by='" + update_by + '\'' +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}
