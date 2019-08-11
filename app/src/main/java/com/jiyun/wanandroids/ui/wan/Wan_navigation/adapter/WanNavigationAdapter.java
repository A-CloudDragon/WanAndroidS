package com.jiyun.wanandroids.ui.wan.Wan_navigation.adapter;

import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.model.entity.WanNavigationinfo;
import com.jiyun.wanandroids.ui.wan.Wan_navigation.view.FlowLayout;

import java.util.List;

public class WanNavigationAdapter extends RecyclerView.Adapter<WanNavigationAdapter.ViewHolder> {
    private Context context;
    private List<WanNavigationinfo.DataBean> list;

    public WanNavigationAdapter(Context context, List<WanNavigationinfo.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void addData(List<WanNavigationinfo.DataBean> dataBeans) {
        list.addAll(dataBeans);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.wannavigationitem, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        WanNavigationinfo.DataBean dataBean = list.get(i);

        final List<WanNavigationinfo.DataBean.ArticlesBean> articles = dataBean.getArticles();

        if (articles != null && articles.size() > 0) {
            for (int j = 0; j < articles.size(); j++) {

                TextView label = (TextView) LayoutInflater.from(context).inflate(R.layout.item_laber, null);
                label.setText(articles.get(j).getTitle());


                final int finalI = j;

                //标签点击事件
                label.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ToastUtil.showLong(articles.get(finalI).getTitle());
                    }
                });
                //将TextView标签添加到flowlayout
                viewHolder.fl.addView(label);
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
         public FlowLayout fl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             fl = itemView.findViewById(R.id.fl);
        }
    }
}
