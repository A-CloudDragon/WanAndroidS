package com.jiyun.wanandroids.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.model.entity.HomerPopHotkeyinfo;
import com.jiyun.wanandroids.ui.wan.Wan_me.adapter.WanMeItemAdapter;
import com.jiyun.wanandroids.ui.wan.Wan_navigation.view.FlowLayout;


import java.util.List;

public class HotkeuyAdapter1 extends RecyclerView.Adapter {
    private Context context;
    private List<HomerPopHotkeyinfo.DataBean> list;
    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public HotkeuyAdapter1(Context context, List<HomerPopHotkeyinfo.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.pop_friend_rlv_item, null);
        return new HotkeuyViewHolder1(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        HomerPopHotkeyinfo.DataBean dataBean = list.get(position);
        HotkeuyViewHolder1 hvh = (HotkeuyViewHolder1) holder;


        TextView label = (TextView) LayoutInflater.from(context).inflate(R.layout.item_laber, null);
        label.setText(dataBean.getName());

        hvh.tv.addView(label);




        hvh.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener!=null){
                    mOnItemClickListener.OnItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class HotkeuyViewHolder1 extends RecyclerView.ViewHolder {
        private FlowLayout tv;
        private View view;
        public HotkeuyViewHolder1(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tv = itemView.findViewById(R.id.fl);
        }
    }


    public interface OnItemClickListener {
        void OnItemClick(int i);
    }
}
