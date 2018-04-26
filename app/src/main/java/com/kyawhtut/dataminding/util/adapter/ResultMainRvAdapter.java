package com.kyawhtut.dataminding.util.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kyawhtut.dataminding.R;
import com.kyawhtut.dataminding.util.adapter.view.ResultViewMainHolder;
import com.kyawhtut.dataminding.util.data.ResultModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KyawHtut on 3/10/2018.
 */

public class ResultMainRvAdapter extends RecyclerView.Adapter<ResultViewMainHolder> {

    List<ResultModel> mResultModelList;

    public ResultMainRvAdapter() {
        mResultModelList = new ArrayList<>();
    }

    public void swapList(List<ResultModel> mResultModelList) {
        this.mResultModelList = mResultModelList;
        notifyDataSetChanged();
    }

    @Override
    public ResultViewMainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.result_main_view, parent, false);
        return new ResultViewMainHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewMainHolder holder, int position) {
        holder.bindRv(mResultModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return (mResultModelList.size() == 0) ? 0 : mResultModelList.size();
    }
}
