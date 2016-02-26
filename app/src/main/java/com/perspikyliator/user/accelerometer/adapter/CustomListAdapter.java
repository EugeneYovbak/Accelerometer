package com.perspikyliator.user.accelerometer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.perspikyliator.user.accelerometer.R;
import com.perspikyliator.user.accelerometer.model.AccelData;

import java.util.ArrayList;

/**
 * Adapter sets the list of coordinates into ListFragments
 */
public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.CustomViewHolder> {

    private final Context mContext;
    private final ArrayList<AccelData> mData;

    public CustomListAdapter(Context mContext, ArrayList<AccelData> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.onBind();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate;
        TextView tvInfo;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvInfo = (TextView) itemView.findViewById(R.id.tv_info);
        }

        public void onBind() {
            AccelData data = mData.get(getPosition());
            tvDate.setText(data.getDate());
            tvInfo.setText("X: " + data.getX() +
                    "    Y: " + data.getY() +
                    "    Z: " + data.getZ());
        }
    }
}
