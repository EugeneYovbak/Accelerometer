package com.perspikyliator.user.accelerometer.interfaces;

import com.telerik.widget.chart.visualization.cartesianChart.RadCartesianChartView;

public interface CallbackGraph {
    void onGraphSuccess(RadCartesianChartView view);
    void onGraphFail(String message);
}
