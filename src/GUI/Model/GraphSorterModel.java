/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Model;

import BE.Guild;
import BLL.BLLFacade;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Mecaa
 */
public class GraphSorterModel
{

    public List<XYChart.Series<Number, Number>> sortGraph(Guild guild)
    {
        BLLFacade bllFac = new BLLFacade();
        ArrayList<HashMap<Integer, Integer>> sortedData = bllFac.graphSorter(guild);

        ArrayList<XYChart.Series<Number, Number>> graphData = new ArrayList<>();

        XYChart.Series<Number, Number> seriesManager = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesVolunteer = new XYChart.Series<>();

        seriesManager.setName("Manager Work Hours");
        seriesVolunteer.setName("Volunteer Contribution Hours");

        for (int i = 0; i < sortedData.size(); i++)
        {

            HashMap<Integer, Integer> sorted = sortedData.get(i);
            for (int q = 0; q < 12; q++)
            {
                if (i == 0)
                {
                    seriesManager.getData().add(new XYChart.Data<>(q + 1, sorted.get(q)));
                }
                else
                {
                    seriesVolunteer.getData().add(new XYChart.Data<>(q + 1, sorted.get(q)));
                }
            }

        }
        graphData.add(seriesManager);
        graphData.add(seriesVolunteer);

        return graphData;
    }

}
