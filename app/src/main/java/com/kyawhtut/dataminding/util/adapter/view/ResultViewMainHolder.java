package com.kyawhtut.dataminding.util.adapter.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kyawhtut.dataminding.R;
import com.kyawhtut.dataminding.util.adapter.ResultRvAdapter;
import com.kyawhtut.dataminding.util.data.ResultModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KyawHtut on 3/10/2018.
 */

public class ResultViewMainHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.result_child_rv)
    RecyclerView mResultChildRv;

    private ResultRvAdapter mResultRvAdapter;

    public ResultViewMainHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        mResultChildRv.setHasFixedSize(true);
        mResultChildRv.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false));
    }

    public void bindRv(ResultModel model){

    }
}
