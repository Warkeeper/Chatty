package edu.scu.aaron.chatty.bmobutils.messages;

import cn.bmob.newim.bean.BmobIMExtraMessage;
import edu.scu.aaron.chatty.bmobutils.MessageType;

/**
 * @author Aaron Yang
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/3/13 14:02
 */
public class AddFriendMessage extends BmobIMExtraMessage {
    //private static final MessageType messageType=MessageType.ADDFRIEND;
    //private static final boolean ifTransient=true;

    /*public static MessageType getMessageType() {
        return messageType;
    }*/
    public static final String ADD="AddFriend";

    @Override
    public String getMsgType() {
        return ADD;
    }

    @Override
    public boolean isTransient() {
        return true;
    }
    //TODO:ConvertFriend
}
