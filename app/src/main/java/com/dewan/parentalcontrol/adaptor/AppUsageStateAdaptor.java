package com.dewan.parentalcontrol.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dewan.parentalcontrol.R;
import com.dewan.parentalcontrol.databinding.RvAppUsageStatsViewBinding;
import com.dewan.parentalcontrol.db.entity.AppUsageEntity;
import com.dewan.usagestatshelper.AppUsageStatsProperty;

import java.util.ArrayList;

public class AppUsageStateAdaptor extends RecyclerView.Adapter<AppUsageStateAdaptor.ViewHolder>{
    private ArrayList<AppUsageEntity> appList = new ArrayList<>();

    public void setAppList(ArrayList<AppUsageEntity> appList) {
        this.appList = appList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_app_usage_stats_view,parent,false);
        return new ViewHolder(view);*/
        RvAppUsageStatsViewBinding rvAppUsageStatsViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                ,R.layout.rv_app_usage_stats_view,parent,false );
        return new ViewHolder(rvAppUsageStatsViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppUsageEntity property = appList.get(position);
        holder.rvAppUsageStatsViewBinding.setAppUsageStatsProperty(property);

    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private RvAppUsageStatsViewBinding rvAppUsageStatsViewBinding;

        public ViewHolder(@NonNull RvAppUsageStatsViewBinding rvAppUsageStatsViewBinding) {
            super(rvAppUsageStatsViewBinding.getRoot());

            this.rvAppUsageStatsViewBinding = rvAppUsageStatsViewBinding;
        }
    }
}
