package com.dewan.parentalcontrol.ui.installedapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dewan.packagemanagerhelper.InstalledAppProperty;
import com.dewan.packagemanagerhelper.PackageManagerHelper;
import com.dewan.parentalcontrol.R;
import com.dewan.parentalcontrol.adaptor.InstalledAppAdaptor;
import com.dewan.parentalcontrol.databinding.FragmentInstalledAppBinding;
import com.dewan.parentalcontrol.db.entity.InstalledAppEntity;

import java.util.ArrayList;
import java.util.Collections;


public class InstalledAppFragment extends Fragment {
    private static final String TAG = "InstalledAppFragment";
    private View view;
    private static FragmentInstalledAppBinding fragmentInstalledAppBinding;
    private static RecyclerView recyclerView;
    private static InstalledAppAdaptor installedAppAdaptor;
    private static ArrayList<InstalledAppEntity> appList = new ArrayList<>();
    private static PackageManagerHelper packageManagerHelper;


    private OnFragmentInteractionListener mListener;

    public InstalledAppFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentInstalledAppBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_installed_app, container, false);
        view = fragmentInstalledAppBinding.getRoot();

        recyclerView = fragmentInstalledAppBinding.rvInstallApp;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        installedAppAdaptor = new InstalledAppAdaptor();
        recyclerView.setAdapter(installedAppAdaptor);
        //
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String deviceId = sharedPreferences.getString("device_id","");
        packageManagerHelper = new PackageManagerHelper(getActivity());
        packageManagerHelper.setDeviceId(deviceId);

        /*
        load list only if its empty
         */
        if (appList.size() == 0){
            new GetInstalledAppTask().execute();
        }
        else {
            installedAppAdaptor.setAppList(appList);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private static class GetInstalledAppTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<InstalledAppProperty> list = packageManagerHelper.getInstalledAppInfo();
            for (int i = 0 ; i < list.size() ; i++){
                InstalledAppEntity installedAppEntity = new InstalledAppEntity();
                InstalledAppProperty property = list.get(i);
                installedAppEntity.setAppName(property.getAppName());
                installedAppEntity.setPackageName(property.getPackageName());
                installedAppEntity.setVersionName(property.getVersionName());
                installedAppEntity.setVersionCode(property.getVersionCode());
                installedAppEntity.setAppIcon(property.getAppIcon());
                appList.add(installedAppEntity);

            }
            Collections.sort(appList,InstalledAppEntity.appNameComparator);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            installedAppAdaptor.setAppList(appList);
            //installedAppAdaptor.notifyDataSetChanged();

        }
    }
}
