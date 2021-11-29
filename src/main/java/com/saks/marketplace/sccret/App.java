package com.saks.marketplace.sccret;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("----------app start----------");
        String[] secrets = SecretManager.process();
        System.out.println("user: " + secrets[0]);
        System.out.println("paswword: " + secrets[1]);
        System.out.println("----------app end----------");
    }
}
