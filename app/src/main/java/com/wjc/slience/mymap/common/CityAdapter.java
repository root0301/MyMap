package com.wjc.slience.mymap.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wjc.slience.mymap.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 城市列表适配器
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.cityHolder> {

    private final Context mContext;
    private final LayoutInflater layoutInflater;
    private List<String> mName;
    private int mType;
    private List<Integer> checkPositionList;


    public CityAdapter(Context context, List<String> name,int type) {
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        mName = name;
        mType = type;
        checkPositionList = new ArrayList<Integer>();
    }


    @Override
    public cityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new cityHolder(layoutInflater.inflate(R.layout.city_item, parent, false), mType);
    }

    /**
     * 绑定cityHolder
     */
    @Override
    public void onBindViewHolder(final cityHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.checkBox.setTag(new Integer(position));
        holder.mName.setText(mName.get(position));
        if (checkPositionList != null) {
            ((cityHolder) holder).checkBox.setChecked((checkPositionList.contains(new Integer(position)) ? true : false));
        } else {
            ((cityHolder) holder).checkBox.setChecked(false);
        }
        if(mType == 2 || mType == 1) {
            holder.checkBox.setVisibility(View.GONE);
        }

        if (mOnItemClickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);

                   if(holder.checkBox.isChecked()) {
                        holder.checkBox.setChecked(false);
                    } else {
                        holder.checkBox.setChecked(true);
                    }
                }
            });
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        if(!checkPositionList.contains(holder.checkBox.getTag()));

                            checkPositionList.add(new Integer(position));
                    } else {
                        if (checkPositionList.contains(holder.checkBox.getTag())) {
                            checkPositionList.remove(new Integer(position));
                        }
                    }
                }
            });

        }

    }


    @Override
    public int getItemCount() {
        return mName.size();
    }



    public static class cityHolder extends RecyclerView.ViewHolder {

        TextView mName;
        CheckBox checkBox;

        public cityHolder(View view, int type) {
            super(view);
            mName = (TextView) view.findViewById(R.id.city_name);
            checkBox = (CheckBox) view.findViewById(R.id.check);
        }
    }


    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickLitener)
    {
        this.mOnItemClickListener = mOnItemClickLitener;
    }

}
