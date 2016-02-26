package com.perspikyliator.user.accelerometer.loader;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.client.DataSnapshot;
import com.perspikyliator.user.accelerometer.MainActivity;
import com.perspikyliator.user.accelerometer.interfaces.CallbackGraph;
import com.perspikyliator.user.accelerometer.model.AccelData;
import com.telerik.widget.chart.engine.databinding.DataPointBinding;
import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;
import com.telerik.widget.chart.visualization.cartesianChart.axes.CategoricalAxis;
import com.telerik.widget.chart.visualization.cartesianChart.axes.LinearAxis;
import com.telerik.widget.chart.visualization.cartesianChart.series.categorical.LineSeries;

import java.util.ArrayList;

public class MyGraphAsyncTask extends AsyncTask<String, Void, RadCartesianChartView> {

    private Context mContext;
    private DataSnapshot mDataSnapshot;
    private CallbackGraph mCallbackGraph;

    public MyGraphAsyncTask(Context mContext, DataSnapshot mDataSnapshot, CallbackGraph mCallbackGraph) {
        this.mContext = mContext;
        this.mDataSnapshot = mDataSnapshot;
        this.mCallbackGraph = mCallbackGraph;
    }

    private RadCartesianChartView mChartView;

    /**
     * Method draws the graph in the new empty chartView
     * @param params contains the neede coordinate
     * @return - returns the drawn chartView
     */
    @Override
    protected RadCartesianChartView doInBackground(String... params) {
        mChartView = new RadCartesianChartView(mContext);
        ArrayList<AccelData> mData = new ArrayList<>();
        for (DataSnapshot postSnapshot: mDataSnapshot.getChildren()) {
            mData.add(new AccelData(postSnapshot.child(MainActivity.DATE).getValue().toString(),
                    postSnapshot.child(MainActivity.X).getValue().toString(),
                    postSnapshot.child(MainActivity.Y).getValue().toString(),
                    postSnapshot.child(MainActivity.Z).getValue().toString()));
        }

        if (mData.size() != 0) {
            drawGraph(params[0], mData);
        }

        return mChartView;
    }

    /**
     * Method returns the drawn chartView to the UI
     * @param radCartesianChartView contains the cartView with the graph
     */
    @Override
    protected void onPostExecute(RadCartesianChartView radCartesianChartView) {
        super.onPostExecute(radCartesianChartView);
        if (radCartesianChartView != null)
            mCallbackGraph.onGraphSuccess(radCartesianChartView);
        else
            mCallbackGraph.onGraphFail(MainActivity.ERROR);
    }

    /**
     * Method draw the graph in the chartView
     * @param arg contains a needed coordinate
     * @param data contains the list of the coordinates
     */
    private void drawGraph(final String arg, ArrayList<AccelData> data) {
        LineSeries lineSeries = new LineSeries(mContext);
        lineSeries.setCategoryBinding(new DataPointBinding() {
            @Override
            public Object getValue(Object o) throws IllegalArgumentException {
                return ((AccelData) o).getDate();
            }
        });

        lineSeries.setValueBinding(new DataPointBinding() {
            @Override
            public Object getValue(Object o) throws IllegalArgumentException {
                String rep = "0.00";
                switch (arg) {
                    case MainActivity.X:
                        rep = ((AccelData) o).getX().replace(',', '.');
                        break;
                    case MainActivity.Y:
                        rep = ((AccelData) o).getY().replace(',', '.');
                        break;
                    case MainActivity.Z:
                        rep = ((AccelData) o).getZ().replace(',', '.');
                        break;
                }
                return Float.parseFloat(rep);
            }
        });

        lineSeries.setData(data);
        mChartView.getSeries().add(lineSeries);

        CategoricalAxis horAxis = new CategoricalAxis(mContext);
        mChartView.setHorizontalAxis(horAxis);

        LinearAxis verAxis = new LinearAxis(mContext);
        mChartView.setVerticalAxis(verAxis);
    }
}
