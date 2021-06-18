package com.aganyun.acode.adapter;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aganyun.acode.R;
import com.aganyun.acode.http.details.bean.GetTickerDetailsBean.DataBean.ResponseBean.RowsBean;
import com.aganyun.acode.utils.DensityUtil;
import com.aganyun.acode.utils.GlideUtils;
import com.aganyun.acode.utils.ObjIsNull;
import com.aganyun.acode.utils.TypeTransform;
import com.aganyun.acode.utils.ViewUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 孟晨 on 2018/9/13.
 */

public class DetailsInnerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RowsBean> mList;
    private Context context;
    private LinearLayout.LayoutParams layoutParams;
    private java.text.DecimalFormat df;

    public DetailsInnerAdapter(Context context, List<RowsBean> list) {
        this.context = context;
        this.mList = list;
        df = new java.text.DecimalFormat("#.##");
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(40));
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
        View view = LayoutInflater.from(context).inflate(R.layout.singleitem_now_details_inner_layout, null);
        return new ImgsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        if (holder instanceof ImgsViewHolder) {
            final RowsBean rowsBean = mList.get(position);
            LinearLayout ll_single_item = ((ImgsViewHolder) holder).ll_single_item;
            ll_single_item.setLayoutParams(layoutParams);

            ImageView img_bi = ((ImgsViewHolder) holder).img_bi;
            GlideUtils.glideLoadImg_DefaultImg(context, rowsBean.getExchangeImgUrl(), img_bi, R.drawable.img_bi);

            TextView tv_bi_type = ((ImgsViewHolder) holder).tv_bi_type;
            tv_bi_type.setText(rowsBean.getExchangeName());

            //成交价
            TextView tv_bi_money = ((ImgsViewHolder) holder).tv_bi_money;
            tv_bi_money.setText("¥" + df.format(TypeTransform.strToDouble(rowsBean.getClose())));
            //颜色
            switch (rowsBean.getCloseArrow().toString().trim()) {
                case "1":
                    tv_bi_money.setTextColor(context.getResources().getColor(R.color.red));
                    break;

                case "-1":
                    tv_bi_money.setTextColor(context.getResources().getColor(R.color.green_text));
                    break;
            }

            //币种
            TextView tv_bi_change_type = ((ImgsViewHolder) holder).tv_bi_change_type;
            tv_bi_change_type.setText(rowsBean.getBase() + "/" + rowsBean.getCurrency());


            //24H变化
            TextView tv_bi_24h_change = ((ImgsViewHolder) holder).tv_bi_24h_change;
            if (rowsBean.getDegree().toString().trim().substring(0, 1).equals("-")) {
                tv_bi_24h_change.setTextColor(context.getResources().getColor(R.color.green_text));
            } else {
                tv_bi_24h_change.setTextColor(context.getResources().getColor(R.color.red));
            }
            tv_bi_24h_change.setText(df.format(TypeTransform.strToDouble(rowsBean.getDegree())) + "%");
        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    private class ImgsViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_bi_type, tv_bi_money, tv_bi_change_type, tv_bi_24h_change;
        private LinearLayout ll_single_item;
        private ImageView img_bi;


        public ImgsViewHolder(View itemView) {
            super(itemView);
            ll_single_item = itemView.findViewById(R.id.ll_single_item);
            img_bi = itemView.findViewById(R.id.img_bi);
            tv_bi_type = itemView.findViewById(R.id.tv_bi_type);
            tv_bi_money = itemView.findViewById(R.id.tv_bi_money);
            tv_bi_change_type = itemView.findViewById(R.id.tv_bi_change_type);
            tv_bi_24h_change = itemView.findViewById(R.id.tv_bi_24h_change);
        }
    }
}


