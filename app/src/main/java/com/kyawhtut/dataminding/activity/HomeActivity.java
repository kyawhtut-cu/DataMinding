package com.kyawhtut.dataminding.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kyawhtut.dataminding.R;
import com.kyawhtut.dataminding.util.Util;
import com.kyawhtut.dataminding.util.adapter.DataRvAdatper;
import com.kyawhtut.dataminding.util.data.ResultModel;
import com.kyawhtut.dataminding.util.data.XYModel;
import com.kyawhtut.dataminding.util.dialog.XYInputDialog;
import com.kyawhtut.dataminding.util.event.EventListener;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.xy_input_rv)
    RecyclerView mXYInputRv;

    @BindView(R.id.center_point_rv)
    RecyclerView mCenterPointRv;

    @BindView(R.id.calculate_btn)
    TextView mCalculateBtn;

    @BindView(R.id.result_layout)
    LinearLayout mResultLayout;

    @BindView(R.id.input_layout)
    RelativeLayout mInputLayout;

    @BindView(R.id.loading_progress)
    ProgressBar mLoadingProgress;

    @BindView(R.id.result_web)
    WebView webView;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @BindView(R.id.round_number)
    TextView mRoundNumber;

    private FlowLayoutManager mFlowLayoutManagerInput, mFlowLayoutManagerCenter;
    private DataRvAdatper mXYDataRvAdapter, mClusterDataRvAdapter;

    private XYInputDialog mXyInputDialog;
    private Util.ConvertResultToHtml toHtml;
    private List<XYModel> mXYInputModel, mClusterInputModel;

    private int round = 0;

    private String xyInputString, clusterInputString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mXYInputModel = new ArrayList<>();
        mClusterInputModel = new ArrayList<>();

        mXyInputDialog = new XYInputDialog();

        mFlowLayoutManagerInput = new FlowLayoutManager();
        mFlowLayoutManagerInput.setAutoMeasureEnabled(true);

        mFlowLayoutManagerCenter = new FlowLayoutManager();
        mFlowLayoutManagerCenter.setAutoMeasureEnabled(true);

        mXYDataRvAdapter = new DataRvAdatper();
        mClusterDataRvAdapter = new DataRvAdatper();

        mXYInputRv.setHasFixedSize(true);
        mXYInputRv.setLayoutManager(mFlowLayoutManagerInput);
        mXYInputRv.setAdapter(mXYDataRvAdapter);
        mXYInputRv.setNestedScrollingEnabled(false);

        mCenterPointRv.setHasFixedSize(true);
        mCenterPointRv.setLayoutManager(mFlowLayoutManagerCenter);
        mCenterPointRv.setAdapter(mClusterDataRvAdapter);
        mCenterPointRv.setNestedScrollingEnabled(false);

        webView.getSettings().setJavaScriptEnabled(true);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mXyInputDialog.showDialog(HomeActivity.this, null, null, 0);
            }
        });

        mCalculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CalculatingKMean().execute();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onGetInputData(EventListener.InputEventListener eventListener) {

        webView.loadDataWithBaseURL("", "", "text/html", "UTF-8", "");

        mXYInputModel = eventListener.getXyInputModel();
        mClusterInputModel = eventListener.getClusterInputModel();

        round = eventListener.getRoundOperation();
        xyInputString = eventListener.getXyInputString();
        clusterInputString = eventListener.getClusterInputString();

        mXYDataRvAdapter.swapList(mXYInputModel);
        mClusterDataRvAdapter.swapList(mClusterInputModel);
        mRoundNumber.setText(round + "");

        if (mXYInputModel.size() > 0 || mClusterInputModel.size() > 0 || round > 0) {
            mInputLayout.setVisibility(View.VISIBLE);
            mResultLayout.setVisibility(View.VISIBLE);
        } else {
            mResultLayout.setVisibility(View.GONE);
            mInputLayout.setVisibility(View.GONE);
        }

        if (round <= 0) {
            mCalculateBtn.setBackgroundResource(R.drawable.unactive_btn);
            mCalculateBtn.setEnabled(false);
        } else {
            mCalculateBtn.setBackgroundResource(R.drawable.active_btn);
            mCalculateBtn.setEnabled(true);
        }
    }

    @OnClick(R.id.edit_input)
    public void onClickEditInput() {
        mXyInputDialog.showDialog(HomeActivity.this, xyInputString, clusterInputString, round);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String k_mean() {
        Log.i(HomeActivity.class.getSimpleName(), round + "");
        List<ResultModel> mResultModelList = new ArrayList<>();
        mResultModelList.clear();

        for (int index = 0; index < round; index++) {

            float[][] matrix = new float[mXYInputModel.size()][(mClusterInputModel.size() + 1)];

            int[] lastIndex = new int[mXYInputModel.size()];

            //calculate |x_2-x_1| + |y_2-y_1| and then insert to matrix
            for (int row = 0; row < mXYInputModel.size(); row++) {
                float x_1 = mXYInputModel.get(row).getX();
                float y_1 = mXYInputModel.get(row).getY();
                for (int col = 0; col < mClusterInputModel.size(); col++) {
                    float x_2 = mClusterInputModel.get(col).getX();
                    float y_2 = mClusterInputModel.get(col).getY();
                    float result = Math.abs(x_2 - x_1) + Math.abs(y_2 - y_1);
                    matrix[row][col] = result;
                }
            }

            //finding min number index each row and then insert index insert to last index of matrix
            //insert index to lastIndex array
            for (int row = 0; row < mXYInputModel.size(); row++) {
                int tmpIndex = 0;
                float tmpNo = matrix[row][0];
                for (int col = 0; col < mClusterInputModel.size(); col++) {
                    tmpNo = (tmpNo > matrix[row][col]) ? matrix[row][col] : tmpNo;
                    tmpIndex = (tmpNo == matrix[row][col]) ? col : tmpIndex;
                }
                matrix[row][mClusterInputModel.size()] = tmpIndex;
                lastIndex[row] = tmpIndex;
            }

            //tmpClusterModel data change new cluster
            //tmpClusterModel data change new cluster
            boolean[] check = new boolean[mClusterInputModel.size()];
            for (int i = 0; i < check.length; i++) {
                check[i] = false;
            }
            List<XYModel> oldCluster = new ArrayList<>();
            for (XYModel old : mClusterInputModel) {
                oldCluster.add(new XYModel(old.getName(), old.getX(), old.getY()));
            }
            List<List<Integer>> clusterIndex = new ArrayList<>();
            for (int row = 0; row < lastIndex.length; row++) {
                //collection of equal index of index
                List<Integer> tmpIndex = new ArrayList<>();
                if (!check[lastIndex[row]]) {
                    //index is checked now
                    check[lastIndex[row]] = true;
                    for (int col = 0; col < lastIndex.length; col++) {
                        if (lastIndex[row] == lastIndex[col]) {
                            tmpIndex.add(col);
                        }
                    }

                    //calculating new cluster
                    float total_x = 0, total_y = 0, total_count = tmpIndex.size();
                    for (int col = 0; col < tmpIndex.size(); col++) {
                        XYModel tmpModel = mXYInputModel.get(tmpIndex.get(col));
                        total_x += tmpModel.getX();
                        total_y += tmpModel.getY();
                    }
                    clusterIndex.add(tmpIndex);
                    total_x /= total_count;
                    total_y /= total_count;
                    total_x = convertToDecimalParse(total_x, "#.##");
                    total_y = convertToDecimalParse(total_y, "#.##");
                    mClusterInputModel.get(lastIndex[row]).setX(total_x);
                    mClusterInputModel.get(lastIndex[row]).setY(total_y);
                }
            }
            //insert final result matrix and xy input
            List<XYModel> newCluster = new ArrayList<>();
            for (XYModel nCluster : mClusterInputModel) {
                newCluster.add(new XYModel(nCluster.getName(), nCluster.getX(), nCluster.getY()));
            }
            mResultModelList.add(new ResultModel(matrix, mXYInputModel, newCluster, oldCluster, clusterIndex));
        }
        String html = new Util.ConvertResultToHtml().getHtml(mResultModelList);
        return html;
    }

    private float convertToDecimalParse(float value, String format) {
        DecimalFormat mDecimalFormat = new DecimalFormat(format);
        mDecimalFormat.setRoundingMode(RoundingMode.CEILING);
        return Float.parseFloat(mDecimalFormat.format(value));
    }

    private class CalculatingKMean extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mLoadingProgress.setVisibility(View.GONE);
            mCalculateBtn.setBackgroundResource(R.drawable.unactive_btn);
            mCalculateBtn.setEnabled(false);
            webView.loadDataWithBaseURL("", result, "text/html", "UTF-8", "");
        }

        @Override
        protected String doInBackground(Void... voids) {
            return k_mean();
        }
    }
}
