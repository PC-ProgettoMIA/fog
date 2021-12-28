package server.controllers;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

public class TVOCController {
  private final MongoClient db;

  public TVOCController(MongoClient db) {
    this.db = db;
  }

  public void getTVOC(RoutingContext ctx) {
    String id = ctx.request().getParam("thingId");
    this.db.findOne("digital_twin", new JsonObject().put("thingId", id), null, res -> {
      if (res.succeeded()) {
        JsonObject result = res.result();
        if (result != null) {
          JsonObject properties = CommonController.checkProperties(result);
          if (properties != null && properties.containsKey("tvoc")) {
            JsonObject tvoc = properties.getJsonObject("tvoc");
            if (tvoc.containsKey("data")) {
              int value = tvoc.getInteger("data");
              HttpServerResponse response = ctx.response();
              response.putHeader("content-type", "application/json");
              response.end(new JsonObject().put("tvoc", value).encodePrettily());
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
