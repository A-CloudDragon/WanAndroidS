package com.jiyun.wanandroids.ui.wan.Wan_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.model.entity.WanProjectIteminfo;
import com.jiyun.wanandroids.ui.wan.Wan_me.adapter.WanMeItemAdapter;

import java.util.List;

public class WanProjectItemAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<WanProjectIteminfo.DataBean.DatasBean> list;


    private WanMeItemAdapter.OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(WanMeItemAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public WanProjectItemAdapter(Context context, List<WanProjectIteminfo.DataBean.DatasBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.wan_project_rlv_item, null);
        return new WanProjectItemViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        WanProjectIteminfo.DataBean.DatasBean datasBean = list.get(position);
        WanProjectItemViewHolder wpiv = (WanProjectItemViewHolder) holder;


        Glide.with(context).load(datasBean.getEnvelopePic()).into(wpiv.envelopePic);

        wpiv.title.setText(datasBean.getTitle());
        wpiv.desc.setText(datasBean.getDesc());
        wpiv.niceDate.setText(datasBean.getNiceDate());
        wpiv.author.setText(datasBean.getAuthor());

        wpiv.view.setOnClickListener(new View.OnClickListener() {
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


    class WanProjectItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView envelopePic;
        private TextView title;
        private TextView desc;
        private TextView niceDate;
        private TextView author;
        private View view;
        public WanProjectItemViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            envelopePic = itemView.findViewById(R.id.wan_project_item_img1);
            title = itemView.findViewById(R.id.wan_project_item_title);
            desc = itemView.findViewById(R.id.wan_project_item_desc);
            niceDate = itemView.findViewById(R.id.wan_project_item_niceDate);
            author = itemView.findViewById(R.id.wan_project_item_author);


        }
    }

    public interface OnItemClickListener{
        void OnItemClick(int i);
    }
}
