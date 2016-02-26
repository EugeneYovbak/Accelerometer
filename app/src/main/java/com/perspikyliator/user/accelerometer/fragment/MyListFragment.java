package com.perspikyliator.user.accelerometer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perspikyliator.user.accelerometer.MainActivity;
import com.perspikyliator.user.accelerometer.R;
import com.perspikyliator.user.accelerometer.adapter.CustomListAdapter;
import com.perspikyliator.user.accelerometer.model.AccelData;

import java.util.ArrayList;

public class MyListFragment extends Fragment implements ValueEventListener {

    private View view;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_list, container, false);
        } else {
            ((ViewGroup) view.getParent()).removeView(view);
        }

        Firebase mRef = new Firebase(MainActivity.URL);
        mRef.addValueEventListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    /**
     * Method listens the changes in the firebase,
     * and reacts according to that
     * @param dataSnapshot contains the list of all elements in a firebase
     */
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        ArrayList<AccelData> mData = new ArrayList<>();
        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
            mData.add(new AccelData(postSnapshot.child("date").getValue().toString(),
                    postSnapshot.child("x").getValue().toString(),
                    postSnapshot.child("y").getValue().toString(),
                    postSnapshot.child("z").getValue().toString()));
        }
        RecyclerView.Adapter mAdapter = new CustomListAdapter(getActivity(), mData);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
        Toast.makeText(getActivity(), MainActivity.ERROR, Toast.LENGTH_SHORT).show();
    }
}
