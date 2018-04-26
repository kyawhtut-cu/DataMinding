package com.kyawhtut.dataminding.util.adapter.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by KyawHtut on 3/10/2018.
 */

public class ResultViewModel extends RecyclerView.ViewHolder {

    public ResultViewModel(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
