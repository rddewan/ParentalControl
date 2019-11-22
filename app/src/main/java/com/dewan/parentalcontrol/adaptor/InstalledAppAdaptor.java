package com.dewan.parentalcontrol.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dewan.parentalcontrol.R;
import com.dewan.parentalcontrol.databinding.RvAppInstalledViewBinding;
import com.dewan.parentalcontrol.db.entity.InstalledAppEntity;

import java.util.ArrayList;

public class InstalledAppAdaptor extends RecyclerView.Adapter<InstalledAppAdaptor.ViewHolder>{
    private ArrayList<InstalledAppEntity> appList = new ArrayList<>();

    public void setAppList(ArrayList<InstalledAppEntity> appList) {
        this.appList = appList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvAppInstalledViewBinding appInstalledViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.rv_app_installed_view,parent,false);

        return new ViewHolder(appInstalledViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InstalledAppEntity installedAppEntity = appList.get(position);
        holder.rvAppInstalledViewBinding.setInstalledAppProperty(installedAppEntity);

    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private RvAppInstalledViewBinding rvAppInstalledViewBinding;

        public ViewHolder(@NonNull RvAppInstalledViewBinding rvAppInstalledViewBinding) {
            super(rvAppInstalledViewBinding.getRoot());

            this.rvAppInstalledViewBinding = rvAppInstalledViewBinding;
        }
    }
}
