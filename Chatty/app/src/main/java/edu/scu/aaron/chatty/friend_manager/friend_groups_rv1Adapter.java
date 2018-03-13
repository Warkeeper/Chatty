package edu.scu.aaron.chatty.friend_manager;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import edu.scu.aaron.chatty.R;

/**
 * 日期：2018.3.12
 作者：yrp
 内容：好友管理界面的第一个recycleView的适配器
 */


//创建rv1的Adapter,将item（可以使String也可以是Bean）和textview关联起来
class friend_groups_rv1Adapter extends BaseQuickAdapter<friend_groups_bar_bean,BaseViewHolder> {

    public friend_groups_rv1Adapter(int layoutResId, @Nullable List<friend_groups_bar_bean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, friend_groups_bar_bean item) {
        helper.setText(R.id.friend_groupbartv1,item.getTv());
        //Brvah框架中图片加载使用setImageResource
        helper.setImageResource(R.id.friend_groups_iv1,item.getIv());

    }


}
