package edu.scu.aaron.chatty.models;

import android.text.TextUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import edu.scu.aaron.chatty.beans.StrangerBean;
import edu.scu.aaron.chatty.beans.UserBean;
import edu.scu.aaron.chatty.bmobutils.listeners.ChattyLogInListener;

/**
 * Created by Aaron on 2018/3/12.
 */

public class UserModel extends BaseModel {
    private static UserModel userModelInstance = new UserModel();

    public static UserModel getUserModelInstance(){
        return userModelInstance;
    }
    private UserModel(){
    }

    /**
     * 用户注册，这边已经做好了传入String的比较，比如用户名密码是否为空、确认密码是否一致
     * 如果回调的done方法中e为null，则说明注册成功，即可切换页面
     * @param userName 用户名 String
     * @param password  密码 String
     * @param comfirmPassword 确认密码 String
     * @param logInListener 需要new一个LogInListener内部匿名类，并重写 done方法，done方法将会在注册完成时调用，如果注册失败，则会将错误原因写在BmobException中
     */
    public void register(String userName, String password, String comfirmPassword, final LogInListener logInListener){
        if(TextUtils.isEmpty(userName)){
            logInListener.done(null,new BmobException(CODE_NULL,"用户名为空"));
        }
        if(TextUtils.isEmpty(password)){
            logInListener.done(null,new BmobException(CODE_NULL,"密码为空"));
        }
        if(TextUtils.isEmpty(comfirmPassword)){
            logInListener.done(null,new BmobException(CODE_NULL,"确认密码为空"));
        }
        if(!password.equals(comfirmPassword)){
            logInListener.done(null,new BmobException(CODE_NOT_EQUAL,"两次输入的密码不一致，请重新输入"));
        }
        final UserBean userBean=new UserBean();
        userBean.setUsername(userName);
        userBean.setPassword(password);
        userBean.signUp(new SaveListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                if(e==null){
                    logInListener.done(null,null);
                }else {
                    logInListener.done(null,e);
                }
            }
        });
    }

    /**
     * 用户登录，这边已经做好了传入String的比较，比如用户名密码是否为空
     * 如果回调的done方法中e为null，则说明登陆成功，即可切换页面
     * @param userName 用户名 String
     * @param password 密码 String
     * @param logInListener 需要new一个LogInListener内部匿名类，并重写 done方法，done方法将会在注册完成时调用，如果注册失败，则会将错误原因写在BmobException中
     */
    public void login(String userName,String password,final LogInListener logInListener){
        if(TextUtils.isEmpty(userName)){
            //logInListener.setHasError(true);
            //logInListener.setErrorReason("用户名为空");
            logInListener.done(null,new BmobException(CODE_NULL,"用户名为空"));
        }
        if(TextUtils.isEmpty(password)){
            logInListener.done(null,new BmobException(CODE_NULL,"密码为空"));
            //logInListener.setHasError(true);
            //logInListener.setErrorReason("密码为空");
        }
        final UserBean userBean =new UserBean();
        userBean.setUsername(userName);
        userBean.setPassword(password);
        userBean.login(new SaveListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                if(e==null){
                    //logInListener.setHasError(false);
                    //logInListener.setErrorReason(null);
                    logInListener.done(null,null);
                }else {
                    //logInListener.setHasError(true);
                    //logInListener.setErrorReason(e.getMessage());
                    logInListener.done(null,e);
                }
            }
        });
    }

    /**
     * 用户登出
     */
    public void logOut(){
        BmobUser.logOut();
    }

    /**
     * 获取当前登录用户的Bean
     * @return UserBean
     */
    public UserBean getCurrentUser(){
        return BmobUser.getCurrentUser(UserBean.class);
    }

    /**
     * 模糊用户查询
     * @param username
     * @param limit
     * @param listener
     */
    public void searchUsersByLikeName(String username, final int limit, final FindListener<StrangerBean> listener){
        BmobQuery<StrangerBean> query =new BmobQuery<>();
        try {
            BmobUser user=BmobUser.getCurrentUser();
            query.addWhereNotEqualTo("username",user.getUsername());
        }catch (Exception e){
            e.printStackTrace();
        }
        query.addWhereContains("username",username);
        query.setLimit(limit);
        query.order("-username");
        query.findObjects(new FindListener<StrangerBean>() {
            @Override
            public void done(List<StrangerBean> list, BmobException e) {
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        listener.done(list, e);
                    } else {
                        listener.done(list, new BmobException(CODE_NULL, "查无此人"));
                    }
                } else {
                    listener.done(list, e);
                }
            }
        });
    }





}
