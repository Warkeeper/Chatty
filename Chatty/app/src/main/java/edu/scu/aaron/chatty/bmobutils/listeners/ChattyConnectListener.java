package edu.scu.aaron.chatty.bmobutils.listeners;

import cn.bmob.newim.listener.ConnectListener;

/**
 * @author Aaron Yang
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/3/13 11:33
 */
@Deprecated
public abstract class ChattyConnectListener extends ConnectListener {
    private boolean hasError;
    private String errorReason;

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }
}
