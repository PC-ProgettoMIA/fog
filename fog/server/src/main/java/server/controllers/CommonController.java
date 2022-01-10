package server.controllers;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;

/**
 * Common Controller
 */
public class CommonController {

    private static final int ERROR_CODE = 404;

    /**
     * Get properties in json.
     *
     * @param result entire Json object.
     * @return properties.
     */
    public static JsonObject checkProperties(JsonObject result) {
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
     * Handler of property not found.
     *
     * @param response response
     */
    public static void propertyNotFound(HttpServerResponse response) {
        response.putHeader("content-type", "application/json");
        response.setStatusCode(ERROR_CODE);
        response.end("Property not found");
    }

    /**
     * Handler of thing not found.
     *
     * @param response response
     */
    public static void thingNotFound(HttpServerResponse response) {
        response.putHeader("content-type", "application/json");
        response.setStatusCode(ERROR_CODE);
        response.end("Thing not found");
    }

}
