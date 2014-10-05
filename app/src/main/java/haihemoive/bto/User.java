package haihemoive.bto;

/**
 * Created by haoli on 14-10-1.
 */
public class User {
    private String userName;
    private String psw;
    private String sex;
    private int age;

    public String getUserName() {
        return userName;
    }

    public String getPsw() {
        return psw;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
