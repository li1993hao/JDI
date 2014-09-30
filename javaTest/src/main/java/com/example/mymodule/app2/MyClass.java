package com.example.mymodule.app2;

import java.io.IOException;

public class MyClass {
    static public void main(String[] args) throws IOException {
        String a = "action";
        System.out.print(a.split(":").length);
    }
}
