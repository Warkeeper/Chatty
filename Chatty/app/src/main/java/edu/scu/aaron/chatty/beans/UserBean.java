package edu.scu.aaron.chatty.beans;

import cn.bmob.v3.BmobUser;

/**
 * Created by Aaron on 2018/3/12.
 */

public class UserBean extends BmobUser {
    private String avatar;

    public UserBean(){}

    /* TODO:
    public UserBean(NewFriend friend){
        setObjectId(friend.getUid());
        setUsername(friend.getName());
        setAvatar(friend.getAvatar());
    }*/

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
