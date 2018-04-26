package com.kyawhtut.dataminding.util;

import com.kyawhtut.dataminding.util.data.ResultModel;
import com.kyawhtut.dataminding.util.data.XYModel;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by KyawHtut on 3/9/2018.
 */

public class Util {

    public static final int INPUT_XY_DIALOG = 1000;
    public static final int INPUT_CENTER_XY_DIALOG = 1001;

    public static class ConvertResultToHtml {

        String htmlHeader = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t\t<meta charset=\"utf-8\">\n" +
                "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "\t<title>Test</title>\n" +
                "\t<style>\n" +
                "\t\t.table>thead>tr>th {\n" +
                "\t\t\tvertical-align: bottom;\n" +
                "\t\t\tborder-bottom: 2px solid #FF4081;\n" +
                "\t\t}\n" +
                "\t\t.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td, .table>tbody>tr>td, .table>tfoot>tr>td {\n" +
                "\t\t\tpadding: 8px;\n" +
                "\t\t\tline-height: 1.846;\n" +
                "\t\t\tvertical-align: top;\n" +
                "\t\t\tborder-top: 1px solid #FF4081;\n" +
                "\t\t}\n" +
                "\t\t.table-bordered>thead>tr>th, .table-bordered>thead>tr>td {\n" +
                "\t\t\tborder-bottom-width: 2px;\n" +
                "\t\t}\n" +
                "\t\ttd, th {\n" +
                "\t\t\tdisplay: table-cell;\n" +
                "\t\t\tvertical-align: inherit;\n" +
                "\t\t}\n" +
                "\t\t.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td, .table>tbody>tr>td, .table>tfoot>tr>td {\n" +
                "\t\t\tpadding: 8px;\n" +
                "\t\t\tline-height: 1.846;\n" +
                "\t\t\tvertical-align: top;\n" +
                "\t\t\tborder-top: 1px solid #FF4081;\n" +
                "\t\t}\n" +
                "\t\t.table-bordered>thead>tr>th, .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, .table-bordered>tbody>tr>td, .table-bordered>tfoot>tr>td {\n" +
                "\t\t\tborder: 1px solid #FF4081;\n" +
                "\t\t}\n" +
                "\t\t.table-hover>tbody>tr, .table-hover>tbody>tr>th, .table-hover>tbody>tr>td {\n" +
                "\t\t\t-webkit-transition: all 0.2s;\n" +
                "\t\t\t-o-transition: all 0.2s;\n" +
                "\t\t\ttransition: all 0.2s;\n" +
                "\t\t}\n" +
                "\t\ttr {\n" +
                "\t\t\tdisplay: table-row;\n" +
                "\t\t\tvertical-align: inherit;\n" +
                "\t\t\tborder-color: inherit;\n" +
                "\t\t}\n" +
                "\t\ttbody {\n" +
                "\t\t\tdisplay: table-row-group;\n" +
                "\t\t\tvertical-align: middle;\n" +
                "\t\t\tborder-color: inherit;\n" +
                "\t\t}\n" +
                "\t\tthead {\n" +
                "\t\t\tdisplay: table-header-group;\n" +
                "\t\t\tvertical-align: middle;\n" +
                "\t\t\tborder-color: inherit;\n" +
                "\t\t}\n" +
                "\t\t.table-bordered {\n" +
                "\t\t\tborder: 1px solid #FF4081;\n" +
                "\t\t}\n" +
                "\t\t.table {\n" +
                "\t\t\twidth: 100%;\n" +
                "\t\t\tmax-width: 100%;\n" +
                "\t\t\tmargin-bottom: 23px;\n" +
                "\t\t}\n" +
                "\t\ttable {\n" +
                "\t\t\tbackground-color: transparent;\n" +
                "\t\t}\n" +
                "\t\ttable {\n" +
                "\t\t\tborder-collapse: collapse;\n" +
                "\t\t\tborder-spacing: 0;\n" +
                "\t\t}\n" +
                "\t</style>\n" +
                "</head>\n" +
                "<body>";

        public String getHtml(List<ResultModel> rmodel) {
            String html = "";
            for (int j = 0; j < rmodel.size(); j++) {
                ResultModel model = rmodel.get(j);
                html += "<p><font size='6dp'><b><u>Iteration " + (j + 1) + " : </u></b></font></p>";
                html += "<p>" +
                        "Cluster : ";
                for (XYModel old : model.getOldCluster()) {
                    html += old.getName() + "(" + (int) old.getX() + "," + (int) old.getY() + ")" +
                            " ";
                }
                html += "</p>";
                html += "<table class='table table-hover table-bordered'>\n" +
                        "\t\t<thead>\n" +
                        "\t\t\t<tr>\n" +
                        "\t\t\t\t<th>\n" +
                        "\t\t\t\t\tPoint\n" +
                        "\t\t\t\t</th>";
//                        "<th>" + clusterModel[j].get(0).name + "(" + clusterModel[j].get(0).x + "," + clusterModel[j].get(0).y + ")</th>";
                for (XYModel old : model.getOldCluster()) {
                    html += "<th>" + old.getName() +
                            "</th>";
                }
                html += "<th>Cluster</th>";
                html += "</tr></thead><tbody>";
                for (int index = 0; index < model.getModel().size(); index++) {
                    html += "<tr>" +
                            "<td>" + model.getModel().get(index).getName() + "(" + (int) model.getModel().get(index).getX() + "," + (int) model.getModel().get(index).getY() + ")" +
                            "</td>";
                    for (float value : model.getMatrix()[index]) {
                        html += "<td>" + convertToDecimalParse(value, "#.##") + "</td>";
                    }
                    html += "</tr>";
                }
                html += "</tbody>\n" +
                        "\t</table>";

                html += "<p>Cluster Group :</p>";
                for (int out = 0; out < model.getOldCluster().size(); out++) {
                    XYModel cluster = model.getOldCluster().get(out);
                    html += "<p>" + cluster.getName() + "={";
                    for (int inner = 0; inner < model.getClusterIndex().get(out).size(); inner++) {
                        int index = model.getClusterIndex().get(out).get(inner);
                        XYModel cmodel = model.getModel().get(index);
                        html += cmodel.getName();
                        if ((inner + 1) != model.getClusterIndex().get(out).size()) {
                            html += ",";
                        } else {
                            html += "}</p>";
                        }
                    }
                }

                html += "<p>New Center : ";
                for (XYModel newModel : model.getNewCluster()) {
                    html += newModel.getName() + "(" + newModel.getX() + "," + newModel.getY() + ") ";
                }
                html += "</p>";
            }
            html += "\n" +
                    "</body>\n" +
                    "</html>";
            return htmlHeader + "" + html;
        }

        private float convertToDecimalParse(float value, String format) {
            DecimalFormat mDecimalFormat = new DecimalFormat(format);
            mDecimalFormat.setRoundingMode(RoundingMode.CEILING);
            return Float.parseFloat(mDecimalFormat.format(value));
        }
    }
}
