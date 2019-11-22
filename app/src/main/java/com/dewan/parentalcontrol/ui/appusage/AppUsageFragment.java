package com.dewan.parentalcontrol.ui.appusage;

import android.app.usage.UsageStats;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dewan.parentalcontrol.R;
import com.dewan.parentalcontrol.adaptor.AppUsageStateAdaptor;
import com.dewan.parentalcontrol.appstats.AppUsageStats;
import com.dewan.parentalcontrol.databinding.FragmentAppUsageBinding;
import com.dewan.parentalcontrol.db.AppDatabase;
import com.dewan.parentalcontrol.db.dao.AppUsageDao;
import com.dewan.parentalcontrol.db.entity.AppUsageEntity;
import com.dewan.usagestatshelper.AppUsageStatsProperty;
import com.dewan.usagestatshelper.UsageStatsHelper;

import java.text.CollationElementIterator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.SortedMap;


public class AppUsageFragment extends Fragment {
    private static final String TAG = "AppUsageFragment";

    private static RecyclerView recyclerView;
    private static AppUsageStateAdaptor appUsageStateAdaptor;
    private static ArrayList<AppUsageEntity> appList = new ArrayList<>();
    private static FragmentAppUsageBinding fragmentAppUsageBinding;
    private static AppDatabase appDatabase;
    private static AppUsageDao appUsageDao;
    private static DecimalFormat decimalFormat;
    private View view;

    private OnFragmentInteractionListener mListener;

    public AppUsageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        decimalFormat = new DecimalFormat("#.##");
        appList.clear();
        //database
        appDatabase = AppDatabase.getInstance(getActivity());
        //data binding
        fragmentAppUsageBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_app_usage,container,false);
        view = fragmentAppUsageBinding.getRoot();
        //recycle view
        recyclerView = fragmentAppUsageBinding.rvAppUsageStats;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        appUsageStateAdaptor = new AppUsageStateAdaptor();
        recyclerView.setAdapter(appUsageStateAdaptor);

        new GetAppUsageStatsTask(appDatabase,getActivity()).execute();
        // Inflate the layout for this fragment
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

    private static class GetAppUsageStatsTask extends AsyncTask<Void,Void,Void> {
        private AppUsageDao appUsageDao;
        private Context context;

        public GetAppUsageStatsTask(AppDatabase appDatabase, Context context) {
            this.appUsageDao = appDatabase.appUsageDao();
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                UsageStatsHelper.setPackageManager(context.getPackageManager());
                SortedMap<Long, UsageStats> mySortedMap = UsageStatsHelper.getForegroundApp(context);
                if (!mySortedMap.isEmpty()) {
                    ArrayList<AppUsageStatsProperty> appStatsLists = UsageStatsHelper.getAppUsageStatsList(mySortedMap);
                    //sort array list
                    Collections.sort(appStatsLists,AppUsageStatsProperty.totalUsageTimeComparator);
                    AppUsageStatsProperty property = appStatsLists.get(0);
                    Log.e(TAG, "MAX: " + property.getTotalTimeInForeground());

                    for (int i = 0; i < appStatsLists.size(); i++){
                        AppUsageEntity appUsageEntity = new AppUsageEntity();
                        AppUsageStatsProperty appUsageStatsProperty = new AppUsageStatsProperty();
                        appUsageStatsProperty = appStatsLists.get(i);

                        appUsageEntity.setApp_name(appUsageStatsProperty.getAppName());
                        appUsageEntity.setPackage_name(appUsageStatsProperty.getPackageName());
                        appUsageEntity.setApp_icon(appUsageStatsProperty.getAppIcon());
                        double totalTime = appUsageStatsProperty.getTotalTimeInForeground();
                        double totalSec = totalTime / 1000;
                        double totalMin = totalTime / 1000 / 60;
                        double totalHr = totalTime / 1000 / 60 / 60;

                        appUsageEntity.setTotalAppInForegroundSec(Double.parseDouble(decimalFormat.format(totalSec)));
                        appUsageEntity.setTotalAppInForegroundMin(Double.parseDouble(decimalFormat.format(totalMin)));
                        appUsageEntity.setTotalAppInForegroundHr(Double.parseDouble(decimalFormat.format(totalHr)));
                        appList.add(appUsageEntity);
                    }
                    //Collections.sort(appList, AppUsageEntity.totalUsageTimeComparator);

                }
            } catch (Exception er) {
                Log.e(TAG, Objects.requireNonNull(er.getLocalizedMessage()));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            appUsageStateAdaptor.setAppList(appList);
            //appUsageStateAdaptor.notifyDataSetChanged();
        }
    }
}
