package com.xx.itext5.Excel;

import com.xx.itext5.utils.excel.ExcelCell;

/**
 * @author xx
 * @date 2023/1/3
 */
public class StudentExcel {

    /**
     * 名字
     */
    @ExcelCell(column = "B",row = 2)
    private String name;

    /**
     * 年龄

     */
    @ExcelCell(column = "E",row = 2)

    private String age;

    /**
     * 爱好
     */
    @ExcelCell(column = "H",row = 2)

    private String hobby;

    /**
     * 工作
     */
    @ExcelCell(column = "B",row = 3)

    private String work;

    /**
     * 电话
     */
    @ExcelCell(column = "E",row = 3)
    private String phone;

    /**
     * 邮箱
     */
    @ExcelCell(column = "H",row = 3)

    private String email;

    /**
     * 备注
     */
    @ExcelCell(column = "B",row = 4)
    private String bz;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }


    @Override
    public String toString() {
        return "StudentExcel{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", hobby='" + hobby + '\'' +
                ", work='" + work + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", bz='" + bz + '\'' +
                '}';
    }
}
