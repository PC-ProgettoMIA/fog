package server.controllers;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;

public class CommonController {

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

  public static void propertyNotFound(HttpServerResponse response){
    response.putHeader("content-type", "application/json");
    response.setStatusCode(404);
    response.end("Property not found");
  }

  public static void thingNotFound(HttpServerResponse response){
    response.putHeader("content-type", "application/json");
    response.setStatusCode(404);
    response.end("Thing not found");
  }

}
