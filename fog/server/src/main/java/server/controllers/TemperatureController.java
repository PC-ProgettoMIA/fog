package server.controllers;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

/**
 * Temperature Controller
 */
public class TemperatureController {

    private final MongoClient db;

    public TemperatureController(MongoClient db) {
        this.db = db;
    }

    /**
     * Getter of temperature data.
     *
     * @param ctx, routing context.
     */
    public void getTemperature(RoutingContext ctx) {
        String id = ctx.request().getParam("thingId");
        this.db.findOne("digital_twin", new JsonObject().put("thingId", id), null, res -> {
            if (res.succeeded()) {
                JsonObject result = res.result();
                if (result != null) {
                    JsonObject properties = CommonController.checkProperties(result);
                    if (properties != null && properties.containsKey("temperature")) {
                        JsonObject temperature = properties.getJsonObject("temperature");
                        if (temperature.containsKey("data")) {
                            float value = temperature.getFloat("data");
                            HttpServerResponse response = ctx.response();
                            response.putHeader("content-type", "application/json");
                            response.end(new JsonObject().put("temperature", value).encodePrettily());
                        }
                    } else {
                        CommonController.propertyNotFound(ctx.response());
                    }
                } else {
                    CommonController.thingNotFound(ctx.response());
                }
            } else {
                res.cause().printStackTrace();
            }
        });

    /*
    this.db.find("digital_twin", new JsonObject(),  res -> {
      if (res.succeeded()) {
        for (JsonObject json : res.result()) {
          double temperature = json.getDouble("temp");
          HttpServerResponse response = ctx.response();
          response.putHeader("content-type", "application/json");
          response.end(new JsonObject().put("temp", temperature).encodePrettily());
        }
      } else {
        res.cause().printStackTrace();
      }
    });

     */

    }
}
