package com.kyawhtut.dataminding.util.data;

import java.util.List;

/**
 * Created by KyawHtut on 3/10/2018.
 */

public class ResultModel {

    private float[][] matrix;
    private List<XYModel> model;
    private List<XYModel> newCluster;
    private List<XYModel> oldCluster;
    private List<List<Integer>> clusterIndex;

    public ResultModel(float[][] matrix, List<XYModel> model, List<XYModel> newCluster, List<XYModel> oldCluster, List<List<Integer>> clusterIndex) {
        this.matrix = matrix;
        this.model = model;
        this.newCluster = newCluster;
        this.oldCluster = oldCluster;
        this.clusterIndex = clusterIndex;
    }

    public float[][] getMatrix() {
        return matrix;
    }

    public List<XYModel> getModel() {
        return model;
    }

    public List<XYModel> getNewCluster() {
        return newCluster;
    }

    public List<XYModel> getOldCluster() {
        return oldCluster;
    }

    public List<List<Integer>> getClusterIndex() {
        return clusterIndex;
    }
}
