package com.schneewind.timetracking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment {
    ListView trackerList;
    TimeTrackerListAdapter adapter;

    TimeTrackingData timeTrackingData;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        timeTrackingData = ((MainActivity)getActivity()).timeTrackingData;

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new TimeTrackerListAdapter(getContext(), timeTrackingData);
        trackerList = view.findViewById(R.id.tracker_list);
        trackerList.setAdapter(adapter);

        timeTrackingData.listAdapter = adapter;

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    public void UpdateUI(){
        adapter.notifyDataSetChanged();
    }
}