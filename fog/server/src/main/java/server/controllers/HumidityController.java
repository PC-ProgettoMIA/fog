package server.controllers;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

public class HumidityController {
  private final MongoClient db;

  public HumidityController(MongoClient db) {
    this.db = db;
  }

  public void getHumidity(RoutingContext ctx) {
    String id = ctx.request().getParam("thingId");
    this.db.findOne("digital_twin", new JsonObject().put("thingId", id), null, res -> {
      if (res.succeeded()) {
        JsonObject result = res.result();
        if (result != null) {
          JsonObject properties = CommonController.checkProperties(result);
          if (properties != null && properties.containsKey("humidity")) {
            JsonObject humidity = properties.getJsonObject("humidity");
            if (humidity.containsKey("data")) {
              float value = humidity.getFloat("data");
              HttpServerResponse response = ctx.response();
              response.putHeader("content-type", "application/json");
              response.end(new JsonObject().put("humidity", value).encodePrettily());
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

  }
}
