package com.aganyun.acode.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aganyun.acode.R;
import com.aganyun.acode.http.suger.bean.GetPearlsBean.DataBean.ResponseBean.RowsBean;
import com.aganyun.acode.utils.DensityUtil;
import com.aganyun.acode.utils.WindowParamUtils;

import java.util.List;

/**
 * Created by 孟晨 on 2018/8/30.
 */

public class GetPearlsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RowsBean> mList;
    private Context context;

    public GetPearlsAdapter(Context context, List<RowsBean> list) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.singleitem_getperal_layout, null);
        return new ImgsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        if (holder instanceof ImgsViewHolder) {
            RowsBean rowsBean = mList.get(position);

            LinearLayout ll_item = ((ImgsViewHolder) holder).ll_item;
            ll_item.setLayoutParams(new ViewGroup.LayoutParams(WindowParamUtils.screenWidth((Activity) context),
                    DensityUtil.dip2px(70)));

            try {
                //糖果数量  "aganPearl":588,
                TextView tv_num = ((ImgsViewHolder) holder).tv_num;
                tv_num.setText("+" + rowsBean.getAganPearl());
            } catch (Exception e) {
                e.printStackTrace();
            }

            //糖果类型  "pearlType":"",
            TextView tv_type = ((ImgsViewHolder) holder).tv_type;
            tv_type.setText(rowsBean.getRemark());

            //获取时间  "addTime":"2018-08-30 22:25:09"
            TextView tv_time = ((ImgsViewHolder) holder).tv_time;
            tv_time.setText(rowsBean.getAddTime());
        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    private class ImgsViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_num, tv_type, tv_time;
        private LinearLayout ll_item;

        public ImgsViewHolder(View itemView) {
            super(itemView);
            tv_num = itemView.findViewById(R.id.tv_num);
            ll_item = itemView.findViewById(R.id.ll_item);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }

    //-----------------------------------点击的接口----------------------------------------------
    private ClickItemCallback clickItemCallback;

    public interface ClickItemCallback {
        void clickAnalyse(String webTitle, String webUrl, String id, int position);
    }

    public void setOnItenClickCallBack(ClickItemCallback clickItemCallback) {
        this.clickItemCallback = clickItemCallback;
    }
}


