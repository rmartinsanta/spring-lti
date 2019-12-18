package rmartin.lti.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IOUtils {

    @SuppressWarnings("unchecked")
    public static Map.Entry<String, Object>[] object2Map(Object o){
        Map.Entry<String, Object>[] map = (Map.Entry<String, Object>[]) new ObjectMapper().convertValue(o, HashMap.class)
                .entrySet()
                .stream()
                .filter(e -> !((Map.Entry) e).getKey().equals("launchRequests"))
                .toArray(Map.Entry[]::new);
        Arrays.sort(map, Map.Entry.comparingByKey());
        return map;
    }
}
