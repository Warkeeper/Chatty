package edu.scu.aaron.chatty.beans;

/**
 * @author Aaron Yang
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${好友Bean类}
 * @date 2018/3/13 10:25
 */
public class FriendBean extends UserBean {
    private String remark;

    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
