package com.wjc.slience.mymap.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wjc.slience.mymap.R;
import com.wjc.slience.mymap.model.Way;

import java.util.List;

/**
 * Created by slience on 2016/4/11.
 */
public class WayAdapter extends RecyclerView.Adapter<WayAdapter.wayHolder> {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<Way> list;

    public WayAdapter(Context context, List<Way> list) {
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public WayAdapter.wayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new wayHolder(layoutInflater.inflate(R.layout.way_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final WayAdapter.wayHolder holder, final int position) {
        holder.startCity.setText(list.get(position).getStart_city());
        holder.endCity.setText(list.get(position).getEnd_city());
        holder.startTime.setText(Float.toString(list.get(position).getStart_time()));
        holder.endTime.setText(Float.toString(list.get(position).getEnd_time()));
        holder.money.setText(Float.toString(list.get(position).getCost()));
        holder.time.setText(Integer.toString(list.get(position).getAll_time()));

        if (mOnItemClickListener == null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class wayHolder extends RecyclerView.ViewHolder {

        TextView startCity;
        TextView endCity;
        TextView startTime;
        TextView endTime;
        TextView time;
        TextView money;

        public wayHolder(View view) {
            super(view);
            startCity = (TextView)view.findViewById(R.id.start_city_txt);
            endCity = (TextView) view.findViewById(R.id.end_city_txt);
            startTime = (TextView) view.findViewById(R.id.start_time_txt);
            endTime = (TextView) view.findViewById(R.id.end_time_txt);
            time = (TextView) view.findViewById(R.id.time);
            money = (TextView) view.findViewById(R.id.money);
        }
    }

    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }
    private onItemClickListener mOnItemClickListener;

    public void setOnClickListener(onItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

}
