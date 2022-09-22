package com.xx.mail.pojo;

/**
 * <h2> UserVo </h2>
 *
 * @Description: TODO
 * @Author xiaoxing
 * @Date 2022/9/22
 */
public class UserVo {
    private String username;

    private String password;

    private String email;
    //    验证码
    private String code;

    public UserVo() {
    }

    public UserVo(String username, String password, String email, String code) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
