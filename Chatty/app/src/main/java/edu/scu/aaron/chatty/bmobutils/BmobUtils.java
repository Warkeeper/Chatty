package edu.scu.aaron.chatty.bmobutils;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.BmobIMMessageHandler;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import edu.scu.aaron.chatty.beans.StrangerBean;
import edu.scu.aaron.chatty.beans.UserBean;
import edu.scu.aaron.chatty.bmobutils.listeners.ChattyLogInListener;
import edu.scu.aaron.chatty.bmobutils.messages.AddFriendMessage;
import edu.scu.aaron.chatty.models.BaseModel;
import edu.scu.aaron.chatty.models.UserModel;

/**
 * Created by Aaron on 2018/3/12.
 */

public class BmobUtils {
    private static BmobUtils bmobUtils;
    public static BmobUtils getBmobUtilsInstance(){
        if (bmobUtils==null)
            bmobUtils=new BmobUtils();
        return bmobUtils;
    }

    private String connectResult=null;
    /**
     * 用户注册，这边已经做好了传入String的比较，比如用户名密码是否为空、确认密码是否一致
     * 如果回调的done方法中e为null，则说明注册成功，即可切换页面
     * @param userName 用户名 String
     * @param password  密码 String
     * @param comfirmPassword 确认密码 String
     * @param logInListener 需要new一个LogInListener内部匿名类，并重写 done方法，done方法将会在注册完成时调用，如果注册失败，则会将错误原因写在BmobException中
     */
    public void userRegister(String userName, String password, String comfirmPassword, final LogInListener logInListener){
        UserModel.getUserModelInstance().register(userName,password,comfirmPassword,logInListener);
    }
    /**
     * 用户登录，这边已经做好了传入String的比较，比如用户名密码是否为空
     * 如果回调的done方法中e为null，则说明登陆成功，即可切换页面
     * @param userName 用户名 String
     * @param password 密码 String
     * @param logInListener 需要new一个ChattyLogInListener内部匿名类，并重写 done方法，done方法将会在登录完成时调用，如果登录失败，则会将错误原因写在BmobException中
     *                      可调用ChattyLogInListener.isHasError来判断是否有成功
     */
    public void userLogin(String userName,String password,final LogInListener logInListener){
        UserModel.getUserModelInstance().login(userName,password,logInListener);
    }
    /**
     * 用户登出
     */
    public void userLogout(){
        UserModel.getUserModelInstance().logOut();
    }
    /**
     * 获取当前登录用户的Bean
     * @return UserBean
     */
    public UserBean getCurrentUserBean(){
        return UserModel.getUserModelInstance().getCurrentUser();
    }

    /**
     * 连接至服务器，如果返回结果为null则说明连接成功，会存储UserBean信息到本地数据库
     * 如果返回结果不为Null，则连接失败，失败原因为返回的String
     * @param connectStatusChangeListener 服务连接状态 ConnectStatusChangeListener
     *                                    需new一个ConnectStatusChangeListener匿名内部类，并重写onChange方法，以处理网络状态变更的情况
     * @param application 传入getApplication()即可，用以解决内存泄漏问题
     * @return String
     */
    public String connect(ConnectStatusChangeListener connectStatusChangeListener, Application application){
        final UserBean userBean = getCurrentUserBean();
        if(!TextUtils.isEmpty(userBean.getObjectId())&&
                BmobIM.getInstance().getCurrentStatus().getCode()!= ConnectionStatus.CONNECTED.getCode()){
            BmobIM.connect(userBean.getObjectId(), new ConnectListener() {
                @Override
                public void done(String s, BmobException e) {
                    if(e==null){
                        connectResult=null;
                        BmobIM.getInstance().updateUserInfo(new BmobIMUserInfo(userBean.getObjectId(),userBean.getUsername(),userBean.getAvatar()));
                        //TODO:EventBus通知其他控件
                    }
                    else {
                        connectResult=e.getMessage();
                    }
                }
            });
            BmobIM.getInstance().setOnConnectStatusChangeListener(connectStatusChangeListener);
        }
        //TODO:内存泄漏问题
        return connectResult;
    }

    /**
     * 2018-3-13
     * 模糊搜索固定数量的用户，传入：
     * @param strangerName 用户名，String
     * @param findListener 返回结果回调，FindListener
     *                     重写该类的done方法后，当搜索成功时，会回调done方法，结果为回调方法参数list<StrangerBean>
     */

    public void searchLimitedUsersByLikeName(String strangerName, FindListener<StrangerBean> findListener){
        if(TextUtils.isEmpty(strangerName))
        {
            Log.i("BmobUtils:searchUsers","传入的陌生人名字为空!");
            return;
        }
        UserModel.getUserModelInstance().searchUsersByLikeName(strangerName,BaseModel.DEFAULT_LIMIT,findListener);
    }

    /**
     * 2018-3-13
     * 发送添加好友消息
     * @param strangerBean 对方的bean类
     * @param addMessage 加好友时的附加消息
     * @param messageSendListener MessageSendListener内部匿名类，重写done方法，若方法参数e获取为null则发送成功，否则失败，失败消息为e.getmessage()
     */

    public void sendAddFriendMessage(StrangerBean strangerBean,String addMessage,MessageSendListener messageSendListener){
        if(BmobIM.getInstance().getCurrentStatus().getCode()!=ConnectionStatus.CONNECTED.getCode()){
            messageSendListener.done(null,new BmobException("尚未连接IM服务器"));
            return;
        }
        BmobIMUserInfo bmobIMUserInfo=new BmobIMUserInfo(strangerBean.getObjectId(),strangerBean.getUsername(),strangerBean.getAvatar());
        BmobIMConversation bmobIMConversationEntrance=BmobIM.getInstance().startPrivateConversation(bmobIMUserInfo,true,null);
        BmobIMConversation messageManager=BmobIMConversation.obtain(BmobIMClient.getInstance(),bmobIMConversationEntrance);
        AddFriendMessage addFriendMessage= new AddFriendMessage();
        UserBean correntUser= BmobUser.getCurrentUser(UserBean.class);
        addFriendMessage.setContent(addMessage);
        Map<String,Object> map=new HashMap<>();
        map.put("name",correntUser.getUsername());
        map.put("avatar",correntUser.getAvatar());
        map.put("objid",correntUser.getObjectId());
        messageManager.sendMessage(addFriendMessage,messageSendListener);
    }
    public void sendMessage(){
        if (BmobIM.getInstance().getCurrentStatus().getCode() != ConnectionStatus.CONNECTED.getCode()) {
            //toast("尚未连接IM服务器");
            return;
        }
    }


}
