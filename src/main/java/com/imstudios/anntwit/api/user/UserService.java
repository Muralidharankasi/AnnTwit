package com.imstudios.anntwit.api.user;

import com.imstudios.anntwit.db.user.UserUtil;

public class UserService {
    private static UserService userService = null;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public boolean validateUserLogin(String userName, String password) {
        String reqPassword = UserUtil.getInstance().validateUserLogin(userName);
        if (reqPassword == null) return false;
        return reqPassword.equals(password);
    }

    public boolean isUserExists(String userName) {
        return UserUtil.getInstance().isUserExists(userName);
    }

    public boolean deleteUser(String userName, String password) {
        return UserUtil.getInstance().deleteUser(userName, password);
    }

    public boolean createUser(String userName, String password, String emailID) {
        return UserUtil.getInstance().createUser(userName, password, emailID);
    }
}
