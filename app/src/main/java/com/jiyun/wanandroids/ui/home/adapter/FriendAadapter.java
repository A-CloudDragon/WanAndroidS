package com.jiyun.wanandroids.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.model.entity.HomerPopFriendinfo;
import com.jiyun.wanandroids.ui.wan.Wan_navigation.view.FlowLayout;


import java.util.List;

public class FriendAadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<HomerPopFriendinfo.DataBean> friends;
    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public FriendAadapter(Context context, List<HomerPopFriendinfo.DataBean> friends) {
        this.context = context;
        this.friends = friends;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(context).inflate(R.layout.pop_friend_rlv_item, null);
        return new FriendViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        HomerPopFriendinfo.DataBean dataBean = friends.get(position);
        FriendViewHolder fvh = (FriendViewHolder) holder;

        TextView label = (TextView) LayoutInflater.from(context).inflate(R.layout.item_laber, null);
        label.setText(dataBean.getName());

        fvh.tv.addView(label);

        fvh.view.setOnClickListener(new View.OnClickListener() {
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
        return friends.size();
    }



    class FriendViewHolder extends RecyclerView.ViewHolder {
        public FlowLayout tv;
        private View view;
        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tv = itemView.findViewById(R.id.fl);
        }
    }



    public interface OnItemClickListener{
        void OnItemClick(int i);
    }



}
