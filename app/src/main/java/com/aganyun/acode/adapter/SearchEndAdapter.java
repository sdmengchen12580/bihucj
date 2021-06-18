package com.aganyun.acode.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aganyun.acode.R;
import com.aganyun.acode.http.content.bean.GetFlashsBean.DataBean.ResponseBean.RowsBean;
import com.aganyun.acode.utils.ObjIsNull;

import java.util.List;

/**
 * Created by 孟晨 on 2018/7/19.
 */

public class SearchEndAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RowsBean> mList;
    private Context context;

    public SearchEndAdapter(Context context, List<RowsBean> list) {
        this.context = context;
        this.mList = list;
    }

    public void clearData() {
        if (mList.size() > 0 && mList != null) {
            mList.clear();
            notifyDataSetChanged();
        }
    }

    public void addBottomData(List<RowsBean> list) {
        if (list.size() > 0 && list != null) {
            mList.addAll(list);
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.singleitem_search_end_layout, null);
        return new ImgsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        if (holder instanceof ImgsViewHolder) {
            final RowsBean rowsBean = mList.get(position);

            TextView tv_time = ((ImgsViewHolder) holder).tv_time;
            tv_time.setText(rowsBean.getTime());

            TextView tv_title = ((ImgsViewHolder) holder).tv_title;
            tv_title.setText(rowsBean.getTTitle());

            TextView tv_content = ((ImgsViewHolder) holder).tv_content;
            tv_content.setText(rowsBean.getBriefIntro());


            LinearLayout ll_share = ((ImgsViewHolder) holder).ll_share;
            ll_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ObjIsNull.ObjIsNull(clickItemCallback)) {
                        clickItemCallback.clickShare(rowsBean.getBriefIntro(),
                                rowsBean.getAddTimeStr(),
                                rowsBean.getTTitle());
                    }
                }
            });


            LinearLayout ll_single_item = ((ImgsViewHolder) holder).ll_single_item;
            ll_single_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ObjIsNull.ObjIsNull(clickItemCallback)) {
                        if (!ObjIsNull.isEmpty(rowsBean.getDetailH5())) {
                            clickItemCallback.clickCancelAttention("新闻详情", rowsBean.getDetailH5());
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    private class ImgsViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_content, tv_title, tv_time;
        private LinearLayout ll_single_item, ll_share;

        public ImgsViewHolder(View itemView) {
            super(itemView);
            ll_single_item = itemView.findViewById(R.id.ll_single_item);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_time = itemView.findViewById(R.id.tv_time);
            ll_share = itemView.findViewById(R.id.ll_share);
            tv_title = itemView.findViewById(R.id.tv_title);

        }
    }

    //-----------------------------------点击的接口----------------------------------------------
    private ClickItemCallback clickItemCallback;

    public interface ClickItemCallback {
        void clickCancelAttention(String webTitle, String webUrl);

        void clickShare(String content, String time, String title);
    }

    public void setOnItenClickCallBack(ClickItemCallback clickItemCallback) {
        this.clickItemCallback = clickItemCallback;
    }
}

