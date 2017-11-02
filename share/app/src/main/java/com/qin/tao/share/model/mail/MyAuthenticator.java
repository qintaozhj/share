package com.qin.tao.share.model.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author qintao on 2017/9/13 11:58
 */

public class MyAuthenticator extends Authenticator {
    String userName = null;
    String password = null;

    public MyAuthenticator() {
    }

    public MyAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}
