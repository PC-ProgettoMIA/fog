package utility;

import io.vertx.core.json.JsonObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CheckSensor {

    private static JsonObject checkProperties(JsonObject result) {
        JsonObject properties = null;
        if (result.containsKey("features")) {
            JsonObject features = result.getJsonObject("features");
            if (features.containsKey("measurements")) {
                JsonObject measurements = features.getJsonObject("measurements");
                if (measurements.containsKey("properties")) {
                    properties = measurements.getJsonObject("properties");
                }
            }
        }
        return properties;
    }

    /**
     * Obtain timestamp from json object.
     *
     * @param result, integral json object.
     * @return json timestamp.
     */
    public static List<String> corruptedSensors(final JsonObject result) {
        List<Long> timestampList = new LinkedList<>();
        List<String> sensorList = new LinkedList<>();
        List<String> keys = Arrays.asList("temperature", "humidity",
                "pressure", "co2", "tvoc", "quality", "wind", "rain", "uv");
        JsonObject properties = checkProperties(result);
        if (properties != null) {
            for (String key : keys) {
                if (properties.containsKey(key)) {
                    JsonObject property = properties.getJsonObject(key);
                    if (property.containsKey("timestamp") && property.containsKey("sensor")) {
                        timestampList.add((Long.valueOf(property.getString("timestamp"))));
                        sensorList.add(property.getString("sensor"));
                    }
                }
            }
        }
        if (timestampList.stream().distinct().count() > 1) {
            List<String> list = new LinkedList<>();
            List<Long> temp = timestampList.stream().distinct().collect(Collectors.toList());
            temp.remove(0);
            for (Long elem : temp) {
                list.add(sensorList.get(timestampList.indexOf(elem)));
            }
            return list;
        } else {
            return new LinkedList<String>();
        }
    }
}
