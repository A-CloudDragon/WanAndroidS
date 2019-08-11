package com.jiyun.wanandroids.ui.wan.Wan_knowledge.Wan_Knowledge_Item.adapter;

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
import com.jiyun.wanandroids.model.entity.WanHomeinfo;
import com.jiyun.wanandroids.model.entity.WanKnowledgeIteminfo;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

public class WanKnowledgeItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<WanKnowledgeIteminfo.DataBean.DatasBean> list;

    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public WanKnowledgeItemAdapter(Context context, List<WanKnowledgeIteminfo.DataBean.DatasBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View inflate1 = LayoutInflater.from(context).inflate(R.layout.wan_homer_rlv_item, null);
                return new WanKnowledgeItemViewHolder(inflate1);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        WanKnowledgeIteminfo.DataBean.DatasBean datasBean = list.get(position);



        WanKnowledgeItemViewHolder whv = (WanKnowledgeItemViewHolder) holder;
            whv.author.setText(datasBean.getAuthor());
            whv.chapterName.setText(datasBean.getChapterName());
            whv.title.setText(datasBean.getTitle());
            whv.superChapterName.setText(datasBean.getSuperChapterName()+"/");
            whv.niceDate.setText(datasBean.getNiceDate());

            whv.view.setOnClickListener(new View.OnClickListener() {
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







    class WanKnowledgeItemViewHolder extends RecyclerView.ViewHolder {

        private TextView author;
        private TextView chapterName;
        private TextView title;
        private TextView superChapterName;
        private TextView niceDate;

        private View view;
        public WanKnowledgeItemViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            author = itemView.findViewById(R.id.wan_homer_author);
            chapterName = itemView.findViewById(R.id.wan_homer_chapterName);
            title = itemView.findViewById(R.id.wan_homer_title);
            superChapterName = itemView.findViewById(R.id.wan_homer_superChapterName);
            niceDate = itemView.findViewById(R.id.wan_homer_niceDate);

        }
    }


    public interface OnItemClickListener{
        void OnItemClick(int i);
    }

}