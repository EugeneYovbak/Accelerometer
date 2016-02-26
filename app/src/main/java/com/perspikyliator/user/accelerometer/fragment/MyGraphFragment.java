package com.perspikyliator.user.accelerometer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.perspikyliator.user.accelerometer.MainActivity;
import com.perspikyliator.user.accelerometer.R;
import com.perspikyliator.user.accelerometer.interfaces.CallbackGraph;
import com.perspikyliator.user.accelerometer.loader.MyGraphAsyncTask;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;

public class MyGraphFragment extends Fragment implements ValueEventListener, CallbackGraph, View.OnClickListener {

    private View view;
    private ViewGroup rootView;
    private TextView tvCoordinate;
    private Context context;
    private String argument;
    private DataSnapshot mDataSnapshot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_graph, container, false);
        } else {
            ((ViewGroup) view.getParent()).removeView(view);
        }

        findViews();

        Firebase mRef = new Firebase(MainActivity.URL);
        mRef.addValueEventListener(this);

        return view;
    }

    /**
     * Method finds and sets all views
     */
    public void findViews() {
        context = getActivity();
        rootView = (ViewGroup) view.findViewById(R.id.container);
        tvCoordinate = (TextView) view.findViewById(R.id.tv_coord);
        Button buttonX = (Button) view.findViewById(R.id.btn_x);
        Button buttonY = (Button) view.findViewById(R.id.btn_y);
        Button buttonZ = (Button) view.findViewById(R.id.btn_z);
        buttonX.setOnClickListener(this);
        buttonY.setOnClickListener(this);
        buttonZ.setOnClickListener(this);
    }

    /**
     * Method listens the changes in the firebase,
     * and reacts according to that
     * @param dataSnapshot contains the list of all elements in a firebase
     */
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        this.mDataSnapshot = dataSnapshot;
        if (argument == null || argument.equals(""))
            argument = MainActivity.X;
        new MyGraphAsyncTask(context, dataSnapshot, this).execute(argument);
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
        Toast.makeText(getActivity(), MainActivity.ERROR, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method changes a ChartView with graph according to
     * chosen coordinate button
     * @param v shows selected button with  the coordinate
     */
    @Override
    public void onClick(View v) {
        if (mDataSnapshot != null) {
            switch (v.getId()) {
                case R.id.btn_x:
                    argument = MainActivity.X;
                    tvCoordinate.setText(MainActivity.X.toUpperCase() + MainActivity.COORDINATE);
                    new MyGraphAsyncTask(context, mDataSnapshot, this).execute(argument);
                    break;
                case R.id.btn_y:
                    argument = MainActivity.Y;
                    tvCoordinate.setText(MainActivity.Y.toUpperCase() + MainActivity.COORDINATE);
                    new MyGraphAsyncTask(context, mDataSnapshot, this).execute(argument);
                    break;
                case R.id.btn_z:
                    argument = MainActivity.Z;
                    tvCoordinate.setText(MainActivity.Z.toUpperCase() + MainActivity.COORDINATE);
                    new MyGraphAsyncTask(context, mDataSnapshot, this).execute(argument);
                    break;
            }
        }
    }

    @Override
    public void onGraphSuccess(RadCartesianChartView view) {
        rootView.removeAllViews();
        rootView.addView(view);
    }

    @Override
    public void onGraphFail(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
