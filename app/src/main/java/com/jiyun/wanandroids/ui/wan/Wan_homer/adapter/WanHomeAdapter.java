package com.jiyun.wanandroids.ui.wan.Wan_homer.adapter;

import android.content.Context;
import android.util.Log;
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
import com.jiyun.wanandroids.util.JudgeUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import java.util.List;

public class WanHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<WanHomeinfo.DataBean.DatasBean> list;
    private List<String> banner;
    private List<String> bannerLink;
    private List<String> bannertitle;

    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public WanHomeAdapter(Context context, List<WanHomeinfo.DataBean.DatasBean> list, List<String> banner, List<String> bannerLink, List<String> bannertitle) {
        this.context = context;
        this.list = list;
        this.banner = banner;
        this.bannerLink = bannerLink;
        this.bannertitle = bannertitle;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View inflate = LayoutInflater.from(context).inflate(R.layout.wan_homer_banner_item, null);
                return new WanHomeBannerViewHolder(inflate);

            case 1:
                View inflate1 = LayoutInflater.from(context).inflate(R.layout.wan_homer_rlv_item, null);
                return new WanHomeRlvViewHolder(inflate1);


        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        WanHomeinfo.DataBean.DatasBean datasBean = list.get(position);

        int type = getItemViewType(position);


        if (type == 0) {
            WanHomeBannerViewHolder whbanner = (WanHomeBannerViewHolder) holder;
//            whbanner.banner.setImages(banner);
//            whbanner.banner.setBannerTitles(bannertitle);
//            whbanner.banner.set;
//            whbanner.banner.setImageLoader(new ImageLoader() {
//                @Override
//                public void displayImage(Context context, Object path, ImageView imageView) {
//                    Glide.with(context).load(path).into(imageView);
//                }
//            });
//            whbanner.banner.start();




//            @Override
//            public void showBannerData(List<BannerData> bannerDataList) {
//                mBannerTitleList = new ArrayList<>();
//                List<String> bannerImageList = new ArrayList<>();
//                mBannerUrlList = new ArrayList<>();
//                for (BannerData bannerData : bannerDataList) {
//                    mBannerTitleList.add(bannerData.getTitle());
//                    bannerImageList.add(bannerData.getImagePath());
//                    mBannerUrlList.add(bannerData.getUrl());
//                }
                //设置banner样式
            whbanner.banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
                //设置图片加载器
            whbanner.banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context).load(path).into(imageView);
                }
            });
                //设置图片集合
            whbanner.banner.setImages(banner);
                //设置banner动画效果
            whbanner.banner.setBannerAnimation(Transformer.DepthPage);
                //设置标题集合（当banner样式有显示title时）
            whbanner.banner.setBannerTitles(bannertitle);
                //设置自动轮播，默认为true
            whbanner.banner.isAutoPlay(true);
                //设置轮播时间
//            whbanner.banner.setDelayTime(banner.size() * 400);
                //设置指示器位置（当banner模式中有指示器时）
            whbanner.banner.setIndicatorGravity(BannerConfig.CENTER);

//            whbanner.banner.setOnBannerListener(i -> JudgeUtils.startArticleDetailActivity(_mActivity, null,
//                        0, bannertitle.get(i), bannerLink.get(i),
//                        false, false, true));
                //banner设置方法全部调用完毕时最后调用
            whbanner.banner.start();
            whbanner.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener!=null){
                        mOnItemClickListener.OnItemClick(position);
                    }
                }
            });


        } else if (type == 1) {
            WanHomeRlvViewHolder whv = (WanHomeRlvViewHolder) holder;
            whv.author.setText(datasBean.getAuthor());
            whv.chapterName.setText(datasBean.getChapterName());
            whv.title.setText(datasBean.getTitle());
            whv.superChapterName.setText(datasBean.getSuperChapterName()+"/");
            whv.niceDate.setText(datasBean.getNiceDate());

            if (datasBean.isFresh()){
                whv.img4.setVisibility(View.VISIBLE);
            }else{
                whv.img4.setVisibility(View.GONE);
            }




            whv.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener!=null){
                        mOnItemClickListener.OnItemClick(position);
                    }
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    class WanHomeBannerViewHolder extends RecyclerView.ViewHolder {
        private Banner banner;
        private View view;
        public WanHomeBannerViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            banner = itemView.findViewById(R.id.wan_home_banner);
        }

    }


    class WanHomeRlvViewHolder extends RecyclerView.ViewHolder {

        private TextView author;
        private TextView chapterName;
        private TextView title;
        private TextView superChapterName;
        private TextView niceDate;
        private ImageView img4;

        private View view;
        public WanHomeRlvViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            img4 = itemView.findViewById(R.id.wan_homer_img4);
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



//    public static class KeepOneH<VH extends RecyclerView.ViewHolder & Expandable> {
//        //    opened为-1表示所有item是关闭状态，open为pos值的表示pos位置的item为展开的状态
//        private int _opened = -1;
//        public void bind(VH holder, int pos) {
//            if (pos == _opened)
////                3
////            直接显示expandView 无动画
//                ExpandableViewHoldersUtil.openH(holder, holder.getExpandView(), false);
//            else
////            直接关闭expandView 无动画
//                ExpandableViewHoldersUtil.closeH(holder, holder.getExpandView(), false);
//        }

    }