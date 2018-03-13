package edu.scu.aaron.chatty.models;

import android.content.Context;

import edu.scu.aaron.chatty.ChattyApplication;

/**
 * Created by Aaron on 2018/3/12.
 */

public abstract class BaseModel {
    public static int CODE_NULL=1000;
    public static int CODE_NOT_EQUAL=1001;

    public static final int DEFAULT_LIMIT=20;
    public Context getContext(){
        return ChattyApplication.getChattyApplicationInstance();
    }
}
