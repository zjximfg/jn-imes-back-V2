package test;

import org.apache.shiro.crypto.hash.Md5Hash;

public class CreatePassword {
    public static void main(String[] args) {
        String password = new Md5Hash("123456", "张杨", 5).toString();
        System.out.println(password);
    }
}
