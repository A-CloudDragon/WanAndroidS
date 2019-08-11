package com.jiyun.wanandroids.ui.wan.Wan_knowledge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.model.entity.WanKnowledgeinfo;
import com.jiyun.wanandroids.ui.wan.Wan_me.adapter.WanMeItemAdapter;

import java.util.List;

public class WanKnowledgeAdapter extends RecyclerView.Adapter<WanKnowledgeAdapter.WanKnowlwdgeViewHolder> {

    private Context context;
    private List<WanKnowledgeinfo.DataBean> list;
    private  OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public WanKnowledgeAdapter(Context context, List<WanKnowledgeinfo.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public WanKnowlwdgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.wan_knowledge_item,null);
        return new WanKnowlwdgeViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull WanKnowlwdgeViewHolder holder, final int position) {
        WanKnowledgeinfo.DataBean datasBean = list.get(position);
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0;i<datasBean.getChildren().size();i++){
            String name = datasBean.getChildren().get(i).getName();
            stringBuffer.append("      ");
            stringBuffer.append(name);
        }
        holder.superChapterName.setText(datasBean.getName());
        holder.chapterName.setText(stringBuffer);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener!=null){
                    mOnItemClickListener.OnItemClickListener(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class WanKnowlwdgeViewHolder extends RecyclerView.ViewHolder {
        private TextView superChapterName;
        private TextView chapterName;
        private View view;


        public WanKnowlwdgeViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            superChapterName = itemView.findViewById(R.id.wan_knowledge_item_superChapterName);
            chapterName = itemView.findViewById(R.id.wan_knowledge_item_chapterName);
        }
    }




    public interface OnItemClickListener{
        void OnItemClickListener(int i);
    }
}
