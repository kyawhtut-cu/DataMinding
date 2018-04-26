package com.kyawhtut.dataminding.util.event;

import com.kyawhtut.dataminding.util.data.XYModel;

import java.util.List;

/**
 * Created by KyawHtut on 3/9/2018.
 */

public class EventListener {

    public static class InputEventListener {
        private List<XYModel> xyInputModel, clusterInputModel;
        private int roundOperation;
        private String xyInputString, clusterInputString;

        public InputEventListener(List<XYModel> xyInputModel, List<XYModel> clusterInputModel, int roundOperation, String xyInputString, String clusterInputString) {
            this.xyInputModel = xyInputModel;
            this.clusterInputModel = clusterInputModel;
            this.roundOperation = roundOperation;
            this.xyInputString = xyInputString;
            this.clusterInputString = clusterInputString;
        }

        public List<XYModel> getXyInputModel() {
            return xyInputModel;
        }

        public List<XYModel> getClusterInputModel() {
            return clusterInputModel;
        }

        public int getRoundOperation() {
            return roundOperation;
        }

        public String getXyInputString() {
            return xyInputString;
        }

        public String getClusterInputString() {
            return clusterInputString;
        }
    }
}
