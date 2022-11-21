package ru.senla.realestatemarket.controller.cart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Slotin (<a href="https://github.com/alexsnitol">@alexsnitol</a>) <p>
 * 2022 Nov
 */
@Controller
@RequestMapping("/canvas")
public class CanvasjsChartController {

    @GetMapping
    public String springMvc(ModelMap modelMap) {
        List<List<Map<Object, Object>>> canvasjsDataList = new ArrayList<>();

        List<Map<Object,Object>> dataPoints1 = new ArrayList<>();

        Map<Object, Object> map;
        
        map = new HashMap<>(); map.put("x", 10); map.put("y", 69); map.put("indexLabel", ""); dataPoints1.add(map);
        map = new HashMap<>(); map.put("x", 20); map.put("y", 48); map.put("indexLabel", ""); dataPoints1.add(map);
        map = new HashMap<>(); map.put("x", 30); map.put("y", 26); map.put("indexLabel", "Lowest"); dataPoints1.add(map);
        map = new HashMap<>(); map.put("x", 40); map.put("y", 50); map.put("indexLabel", ""); dataPoints1.add(map);
        map = new HashMap<>(); map.put("x", 50); map.put("y", 67); map.put("indexLabel", ""); dataPoints1.add(map);
        map = new HashMap<>(); map.put("x", 60); map.put("y", 38); map.put("indexLabel", ""); dataPoints1.add(map);
        map = new HashMap<>(); map.put("x", 70L); map.put("y", 94); map.put("indexLabel", "Highest"); dataPoints1.add(map);
        map = new HashMap<>(); map.put("x", 80); map.put("y", 63); map.put("indexLabel", ""); dataPoints1.add(map);
        map = new HashMap<>(); map.put("x", 90); map.put("y", 57); map.put("indexLabel", ""); dataPoints1.add(map);
        map = new HashMap<>(); map.put("x", 100); map.put("y", 60); map.put("indexLabel", ""); dataPoints1.add(map);
        map = new HashMap<>(); map.put("x", 110); map.put("y", 38); map.put("indexLabel", ""); dataPoints1.add(map);
        map = new HashMap<>(); map.put("x", 120); map.put("y", 49); map.put("indexLabel", ""); dataPoints1.add(map);
        map = new HashMap<>(); map.put("x", 130); map.put("y", 37); map.put("indexLabel", ""); dataPoints1.add(map);

        canvasjsDataList.add(dataPoints1);

        modelMap.addAttribute("dataPointsList", dataPoints1);

        modelMap.addAttribute("greeting", "TEST");

        return "chart";
    }

}
