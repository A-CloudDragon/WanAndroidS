package com.jiyun.wanandroids.ui.wan.Wan_me.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.model.entity.WanMeIteminfo;

import java.util.List;

public class WanMeItemAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<WanMeIteminfo.DataBean.DatasBean> list;

    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public WanMeItemAdapter(Context context, List<WanMeIteminfo.DataBean.DatasBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.wan_homer_rlv_item, null);
        return new WanMeItemViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        WanMeIteminfo.DataBean.DatasBean datasBean = list.get(position);

        WanMeItemViewHolder wm = (WanMeItemViewHolder) holder;
        wm.author.setText(datasBean.getAuthor());
        wm.superChapterName.setText(datasBean.getSuperChapterName());
        wm.chapterName.setText(datasBean.getChapterName());
        wm.title.setText(datasBean.getTitle());
        wm.niceDate.setText(datasBean.getNiceDate());
        wm.img4.setVisibility(View.GONE);


        wm.view.setOnClickListener(new View.OnClickListener() {
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


    class WanMeItemViewHolder extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView superChapterName;
        private TextView chapterName;
        private TextView title;
        private TextView niceDate;
        private ImageView img4;
        private View view;
        public WanMeItemViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            img4 = itemView.findViewById(R.id.wan_homer_img4);
            author = itemView.findViewById(R.id.wan_homer_author);
            superChapterName = itemView.findViewById(R.id.wan_homer_superChapterName);
            chapterName = itemView.findViewById(R.id.wan_homer_chapterName);
            title = itemView.findViewById(R.id.wan_homer_title);
            niceDate = itemView.findViewById(R.id.wan_homer_niceDate);
        }
    }




    public interface OnItemClickListener{
        void OnItemClick(int i);
    }
}
