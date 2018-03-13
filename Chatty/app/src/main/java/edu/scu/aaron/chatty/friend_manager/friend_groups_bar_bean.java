package edu.scu.aaron.chatty.friend_manager;

import android.media.Image;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;

/**
 * Created by Shinelon on 2018/3/12.
 */

public class friend_groups_bar_bean implements Serializable {
//    private String btn;
    private int iv;
    private String tv;

    public friend_groups_bar_bean() {
    }
//构造函数直接获得外部传过来的数据
    public friend_groups_bar_bean(int iv, String tv) {
//        this.btn = btn;
        this.iv = iv;
        this.tv = tv;
    }


    public int getIv() {
        return iv;
    }

    public void setIv(int iv) {
        this.iv = iv;
    }

    public String getTv() {
        return tv;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }
}
