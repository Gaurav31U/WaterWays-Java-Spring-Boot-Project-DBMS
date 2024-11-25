package com.masters.waterways.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
// @component
public class HarborStatusProvider {
    public static final Map<Integer, String> getHarborStatusDesc = Map.of(
            1, "OPERATIONAL",
            2, "SUSPENDED"
    );

    public static final Map<String, Integer> getHarborStatusCode = Map.of(
            "OPERATIONAL", 1,
            "SUSPENDED", 2
    );


}
