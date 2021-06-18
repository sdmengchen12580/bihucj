package com.aganyun.acode.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aganyun.acode.R;
import com.aganyun.acode.entity.quicknewsbus.ClickShareBus;
import com.aganyun.acode.http.content.bean.GetFlashsBean.DataBean.ResponseBean.RowsBean;
import com.aganyun.acode.utils.ObjIsNull;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by 孟晨 on 2018/7/18.
 */

public class QuickNewsInnerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<RowsBean> mList;


    public QuickNewsInnerAdapter(Context context, List<RowsBean> list) {
        this.context = context;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.singleitem_quick_news_inner_layout, null);
        return new ImgsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        if (holder instanceof ImgsViewHolder) {
            final RowsBean rowsBean = mList.get(position);
            //总时间
            LinearLayout ll_all_time = ((ImgsViewHolder) holder).ll_all_time;
            TextView tv_all_time = ((ImgsViewHolder) holder).tv_all_time;
            if (!ObjIsNull.isEmpty(rowsBean.getDateFormat().trim())) {
                ll_all_time.setVisibility(View.VISIBLE);
                tv_all_time.setText(rowsBean.getDateFormat());
            }

            //时间
            TextView tv_time = ((ImgsViewHolder) holder).tv_time;
            tv_time.setText(rowsBean.getTime());

            //标题
            TextView tv_title = ((ImgsViewHolder) holder).tv_title;
            tv_title.setText(rowsBean.getTTitle());

            //内容
            TextView tv_content = ((ImgsViewHolder) holder).tv_content;
            tv_content.setText(rowsBean.getBriefIntro());

            //h5
            final String webUrl = rowsBean.getDetailH5();
            LinearLayout ll_2_web = ((ImgsViewHolder) holder).ll_2_web;
            ll_2_web.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ObjIsNull.isEmpty(webUrl)) {
                        EventBus.getDefault().post(new ClickShareBus(1, webUrl, null, null, null, null));
                    }
                }
            });

            //分享
            LinearLayout ll_share = ((ImgsViewHolder) holder).ll_share;
            ll_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new ClickShareBus(2,
                            null,
                            null,
                            rowsBean.getBriefIntro(),
                            rowsBean.getAddTimeStr(),
                            rowsBean.getTTitle()));
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    private class ImgsViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_time, tv_title, tv_content, tv_all_time;
        private LinearLayout ll_share, ll_all_time, ll_2_web;

        public ImgsViewHolder(View itemView) {
            super(itemView);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            ll_share = itemView.findViewById(R.id.ll_share);
            ll_all_time = itemView.findViewById(R.id.ll_all_time);
            tv_all_time = itemView.findViewById(R.id.tv_all_time);
            ll_2_web = itemView.findViewById(R.id.ll_2_web);
        }
    }


    //-----------------------------------------工具类--------------------------------------------------
    public void clearData() {
        if (mList.size() > 0 && mList != null) {
            mList.clear();
            notifyDataSetChanged();
        }
    }

    public int getListSize() {
        return (mList == null) ? 0 : mList.size();
    }

    public void refreshData(List<RowsBean> list) {
        if (list.size() > 0 && mList != null) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void addBottomData(List<RowsBean> list) {
        if (list.size() > 0 && list != null) {
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

}