package com.kyawhtut.dataminding.util.adapter.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyawhtut.dataminding.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KyawHtut on 3/9/2018.
 */

public class XYViewModel extends RecyclerView.ViewHolder {

    @BindView(R.id.main_layout)
    LinearLayout mainLayout;

    @BindView(R.id.xy_result)
    TextView mXYResult;

    public XYViewModel(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setXY(String name, int x, int y) {
        mXYResult.setText(name + "(" + x + "," + y + ")");
    }
}
