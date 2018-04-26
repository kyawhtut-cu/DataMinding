package com.kyawhtut.dataminding.util.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kyawhtut.dataminding.R;
import com.kyawhtut.dataminding.util.adapter.view.XYViewModel;
import com.kyawhtut.dataminding.util.data.XYModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KyawHtut on 3/9/2018.
 */

public class DataRvAdatper extends RecyclerView.Adapter<XYViewModel> {

    private List<XYModel> mXYModelList;

    public DataRvAdatper() {
        mXYModelList = new ArrayList<>();
    }

    public void swapList(List<XYModel> mXYModelList) {
        this.mXYModelList = mXYModelList;
        notifyDataSetChanged();
    }

    @Override
    public XYViewModel onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view;
        view = mInflater.inflate(R.layout.x_y_view, parent, false);
        return new XYViewModel(view);
    }

    @Override
    public void onBindViewHolder(XYViewModel holder, int position) {
        XYModel model = mXYModelList.get(position);

        holder.setXY(model.getName(), (int) model.getX(), (int) model.getY());
    }

    @Override
    public int getItemCount() {
        return (mXYModelList.size() == 0) ? 0 : mXYModelList.size();
    }
}
