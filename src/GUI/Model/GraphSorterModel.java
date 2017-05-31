
package GUI.Model;

import BE.Guild;
import BLL.BLLFacade;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.scene.chart.XYChart;

public class GraphSorterModel
{

    public List<XYChart.Series<String, Number>> sortGraph(Guild guild, LocalDate periodOne, LocalDate periodTwo)
    {
        BLLFacade bllFac = new BLLFacade();
        ArrayList<HashMap<String, Integer>> sortedData = bllFac.graphSorter(guild, periodOne, periodTwo);

        ArrayList<XYChart.Series<String, Number>> graphData = new ArrayList<>();

        XYChart.Series<String, Number> seriesManager = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesVolunteer = new XYChart.Series<>();

        seriesManager.setName("Manager Work Hours");
        seriesVolunteer.setName("Volunteer Contribution Hours");

        for (int i = 0; i < sortedData.size(); i++)
        {

            HashMap<String, Integer> sorted = sortedData.get(i);
            for (int q = 0; q < 12; q++)
            {
                if (i == 0)
                {
                    Iterator it = sorted.entrySet().iterator();
                    while (it.hasNext())
                    {
                        Map.Entry pair = (Map.Entry) it.next();
                        seriesManager.getData().add(new XYChart.Data<>((String) pair.getKey(), (Integer) pair.getValue()));
                        it.remove(); // avoids a ConcurrentModificationException
                    }

                }
                else
                {
                    Iterator it = sorted.entrySet().iterator();
                    while (it.hasNext())
                    {
                        Map.Entry pair = (Map.Entry) it.next();
                        seriesVolunteer.getData().add(new XYChart.Data<>((String) pair.getKey(), (Integer) pair.getValue()));
                        
                        it.remove(); // avoids a ConcurrentModificationException
                    }
                }
            }

        }
        graphData.add(seriesManager);
        graphData.add(seriesVolunteer);

        return graphData;
    }

}
