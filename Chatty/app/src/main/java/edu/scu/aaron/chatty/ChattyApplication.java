package edu.scu.aaron.chatty;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by Aaron on 2018/3/12.
 */

public class ChattyApplication extends Application{
    private static ChattyApplication chattyApplicationInstance;

    public  static ChattyApplication getChattyApplicationInstance(){
        return chattyApplicationInstance;
    }
    private void setInstance(ChattyApplication ca){
        setChattyApplicationInstance(ca);
    }
    private  static void setChattyApplicationInstance(ChattyApplication ca) {
        chattyApplicationInstance=ca;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        Bmob.initialize(this,"c57031145d850e29120ca6d6e7b0903a");
    }
}
