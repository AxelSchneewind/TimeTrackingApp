package com.schneewind.timetracking.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.schneewind.timetracking.R;
import com.schneewind.timetracking.timetracking.TimeTrackingData;

public class ListViewFragment extends Fragment implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    ListView trackerList;
    TimeTrackerListAdapter adapter;

    TimeTrackingData timeTrackingData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        timeTrackingData = ((TimeTrackingActivity)getActivity()).getTimeTrackingData();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listview, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new TimeTrackerListAdapter(getContext(), timeTrackingData);
        trackerList = view.findViewById(R.id.tracker_list);
        trackerList.setAdapter(adapter);
        trackerList.setOnItemClickListener(this);
        trackerList.setOnItemLongClickListener(this);

        timeTrackingData.listAdapter = adapter;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        timeTrackingData.removeTimeTracker(timeTrackingData.getTimeTracker(i));
        timeTrackingData.listAdapter.notifyDataSetChanged();
        return false;
    }

    /**
     * TODO: open detail fragment on top of listViewFragment
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        getActivity().findViewById(R.id.fragment_secondary).setVisibility(View.VISIBLE);
        DetailViewFragment detailViewFragment = (DetailViewFragment) getActivity().getSupportFragmentManager().findFragmentByTag("DETAILVIEW");
        detailViewFragment.setDisplayedTimeTracker(timeTrackingData.getTimeTracker(i));
    }
}