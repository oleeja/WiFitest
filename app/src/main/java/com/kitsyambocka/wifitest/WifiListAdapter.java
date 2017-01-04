package com.kitsyambocka.wifitest;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Developer on 04.01.2017.
 */

public class WifiListAdapter extends RecyclerView.Adapter<WifiListAdapter.ViewHolder> {

    private List<ScanResult> scanResults;
    private Context context;

    public WifiListAdapter(List<ScanResult> scanResults, Context context) {
        this.scanResults = scanResults;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d("MyTagh", scanResults.size()+" "+scanResults.get(position).SSID);

        holder.textView.setText(scanResults.get(position).SSID);

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnListItemClick onListItemClick = (OnListItemClick)context;
                onListItemClick.onWifiClick(scanResults.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("MyTagc", (scanResults == null ? 0 : scanResults.size())+"");
        return scanResults == null ? 0 : scanResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_view);
        }
    }

    public void setList(List<ScanResult>l){
        scanResults = l;
        Log.d("MyTagn", scanResults.size()+" ");
        notifyDataSetChanged();
    }
}
