package org.tacs.grupocuatro.entity;

import org.tacs.grupocuatro.DAO.UserDao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public abstract class Loggable {

    protected int id;
    protected String username;
    protected String password;

    protected static String hashPassword(String passwordToHash) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            MessageDigest md = MessageDigest.getInstance("SHA_512");
            md.update(salt);
            byte[] hashedPassword = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            return hashedPassword.toString();
        }
        catch(NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.password = hashPassword(password);
    }


    public boolean login() {
        var user = findBy(this.username);
        if (user != null) {
            if (user.getPassword().equals(hashPassword(this.password)))
                return true;
        }
        return false;
    }

    protected static Loggable findBy(String username) {
        var userDao = new UserDao();
        for(User user : userDao.getAll()) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
