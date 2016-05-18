package com.wjc.slience.mymap.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wjc.slience.mymap.R;

import java.util.List;


/**
 * 城市列表适配器
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.cityHolder> {

    private final Context mContext;
    private final LayoutInflater layoutInflater;
    private List<String> mName;
    private int mType;


    public CityAdapter(Context context, List<String> name,int type) {
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        mName = name;
        mType = type;
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

        holder.mName.setText(mName.get(position));
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
