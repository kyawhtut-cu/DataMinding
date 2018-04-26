package com.kyawhtut.dataminding.util.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kyawhtut.dataminding.R;
import com.kyawhtut.dataminding.util.data.XYModel;
import com.kyawhtut.dataminding.util.event.EventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KyawHtut on 3/9/2018.
 */

public class XYInputDialog {

    public LayoutInflater mInflater;
    public View mView;
    public AlertDialog.Builder mABuilder;
    public AlertDialog mAlert;
    private List<XYModel> xyInputModel, clusterInputModel;

    public void showDialog(Context mContext, String xyInput, String clusterInput, int roundOperation) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(mContext);
            mABuilder = new AlertDialog.Builder(mContext);
        }
        mView = mInflater.inflate(R.layout.x_y_input_dialog, null);
        mABuilder.setView(mView);
        mAlert = mABuilder.create();

        final EditText mXYInput = mView.findViewById(R.id.xy_input);
        final EditText mClusterInput = mView.findViewById(R.id.cluster_input);
        final EditText mRoundOperation = mView.findViewById(R.id.round_operation);

        TextView mOk = mView.findViewById(R.id.ok);
        TextView mCancel = mView.findViewById(R.id.cancel);

        if (xyInput != null) {
            mXYInput.setText(xyInput);
        }
        if (clusterInput != null) {
            mClusterInput.setText(clusterInput);
        }
        if (roundOperation != 0) {
            mRoundOperation.setText(roundOperation + "");
        }

        mOk.setOnClickListener(v -> {
            xyInputModel = new ArrayList<>();
            clusterInputModel = new ArrayList<>();

            String tmpXYInput = mXYInput.getText().toString();
            String tmpClusterInput = mClusterInput.getText().toString();
            String tmpRoundOperation = mRoundOperation.getText().toString();

            boolean error_mode = false;

            if (!TextUtils.isEmpty(tmpXYInput)) {
                try {
                    xyInputModel = convertToList(tmpXYInput);
                } catch (Exception e) {
                    error_mode = true;
                    setError(mXYInput, "Please enter correct input data type.");
                }
            }
            if (!TextUtils.isEmpty(tmpClusterInput)) {
                try {
                    clusterInputModel = convertToList(tmpClusterInput);
                } catch (Exception e) {
                    error_mode = true;
                    setError(mClusterInput, "Please enter correct input data type.");
                }
            }
            if (!error_mode) {
                mAlert.dismiss();
                if (!TextUtils.isEmpty(tmpRoundOperation)) {
                    EventBus.getDefault().post(new EventListener.InputEventListener(xyInputModel, clusterInputModel, convertInt(tmpRoundOperation), tmpXYInput, tmpClusterInput));
                } else
                    EventBus.getDefault().post(new EventListener.InputEventListener(xyInputModel, clusterInputModel, 0, tmpXYInput, tmpClusterInput));
            }
        });
        mCancel.setOnClickListener(v -> mAlert.dismiss());
        mAlert.show();
    }

    private List<XYModel> convertToList(String inputString) {
        List<XYModel> model = new ArrayList<>();
        String[] arr = inputString.split(" ");
        for (int index = 0; index < arr.length; index++) {
            String tmp = arr[index];
            String name = tmp.substring(0, tmp.indexOf('('));
            String tmpString = tmp.substring((tmp.indexOf('(') + 1), tmp.indexOf(')'));
            String[] xyArr = tmpString.split(",");
            model.add(new XYModel(name, convertInt(xyArr[0]), convertInt(xyArr[1])));
        }
        return model;
    }

    private int convertInt(String data) {
        return Integer.parseInt(data);
    }

    private void setError(EditText view, String errorMsg) {
        view.setError(errorMsg);
    }
}
