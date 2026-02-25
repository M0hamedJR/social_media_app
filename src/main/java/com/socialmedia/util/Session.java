package com.socialmedia.util;

import com.socialmedia.model.User;

public class Session {
    private static int currentUserId;

    public static void setCurrentUserId(int id) {
        currentUserId = id;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }
}

