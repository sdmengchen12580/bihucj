package com.aganyun.acode.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aganyun.acode.R;
import com.aganyun.acode.http.content.bean.GetQuotationsBean.DataBean.ResponseBean.RowsBean;
import com.aganyun.acode.utils.GlideUtils;
import com.aganyun.acode.utils.ObjIsNull;

import java.util.List;

/**
 * Created by 孟晨 on 2018/8/17.
 */

public class HomeAnlyseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RowsBean> mList;
    private Context context;

    public HomeAnlyseAdapter(Context context, List<RowsBean> list) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.singleitem_homenews_layout, null);
        return new ImgsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        if (holder instanceof ImgsViewHolder) {
            final RowsBean rowsBean = mList.get(position);

            //id
            final String id = rowsBean.getId();

            TextView tv_content = ((ImgsViewHolder) holder).tv_content;
            tv_content.setText(rowsBean.getTTitle().toString().trim());

            TextView tv_has_looked_num = ((ImgsViewHolder) holder).tv_has_looked_num;
            tv_has_looked_num.setText(rowsBean.getTDjcs() + "");

            TextView tv_time = ((ImgsViewHolder) holder).tv_time;
            tv_time.setText(rowsBean.getAddTimeStr());

            TextView tv_name = ((ImgsViewHolder) holder).tv_name;
            if(!ObjIsNull.isEmpty(rowsBean.getCFrom())){
                tv_name.setText(rowsBean.getCFrom());
            }

            ImageView img_news = ((ImgsViewHolder) holder).img_news;
            GlideUtils.glideLoadImg(context, rowsBean.getImgUrl(), img_news);

            LinearLayout ll_single_homenews = ((ImgsViewHolder) holder).ll_single_homenews;
            ll_single_homenews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ObjIsNull.ObjIsNull(clickItemCallback)) {
                        if (!ObjIsNull.isEmpty(rowsBean.getDetailH5())) {
                            clickItemCallback.clickAnalyse("行情", rowsBean.getDetailH5(),
                                    id, position, rowsBean.getTTitle().toString().trim());
                        }
                    }
                }
            });
        }
    }

    public void lookNumUpOne(int position) {
        mList.get(position).setTDjcs((mList.get(position).getTDjcs() + 1));
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    private class ImgsViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_content, tv_has_looked_num, tv_time, tv_name;
        private ImageView img_news;
        private LinearLayout ll_single_homenews;

        public ImgsViewHolder(View itemView) {
            super(itemView);
            ll_single_homenews = itemView.findViewById(R.id.ll_single_homenews);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_has_looked_num = itemView.findViewById(R.id.tv_has_looked_num);
            tv_time = itemView.findViewById(R.id.tv_time);
            img_news = itemView.findViewById(R.id.img_news);
        }
    }

    //-----------------------------------点击的接口----------------------------------------------
    private ClickItemCallback clickItemCallback;

    public interface ClickItemCallback {
        void clickAnalyse(String webTitle, String webUrl, String id, int position, String content);
    }

    public void setOnItenClickCallBack(ClickItemCallback clickItemCallback) {
        this.clickItemCallback = clickItemCallback;
    }
}

