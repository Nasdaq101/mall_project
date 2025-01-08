package com.hmall.common.utils;

public class UserContext {
    private static final ThreadLocal<Long> tl = new ThreadLocal<>();

    /**
     * save current user info to ThreadLocal
     * @param userId user id
     */
    public static void setUser(Long userId) {
        tl.set(userId);
    }

    /**
     * get current user info
     * @return user id
     */
    public static Long getUser() {
        return tl.get();
    }

    /**
     * remove current user info
     */
    public static void removeUser(){
        tl.remove();
    }
}
