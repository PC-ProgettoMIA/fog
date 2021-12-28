package server.controllers;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

public class Co2Controller {
  private final MongoClient db;

  public Co2Controller(MongoClient db) {
    this.db = db;
  }

  public void getCo2(RoutingContext ctx) {
    String id = ctx.request().getParam("thingId");
    this.db.findOne("digital_twin", new JsonObject().put("thingId", id), null, res -> {
      if (res.succeeded()) {
        JsonObject result = res.result();
        if (result != null) {
          JsonObject properties = CommonController.checkProperties(result);
          if (properties != null && properties.containsKey("co2")) {
            JsonObject co2 = properties.getJsonObject("co2");
            if (co2.containsKey("data")) {
              int value = co2.getInteger("data");
              HttpServerResponse response = ctx.response();
              response.putHeader("content-type", "application/json");
              response.end(new JsonObject().put("co2", value).encodePrettily());
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
