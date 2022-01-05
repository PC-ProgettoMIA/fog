package server.controllers;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

/**
 * Wind Controller
 */
public class WindController {
    private final MongoClient db;

    public WindController(MongoClient db) {
        this.db = db;
    }


    /**
     * Getter of wind data.
     *
     * @param ctx, routing context.
     */
    public void getWind(RoutingContext ctx) {
        String id = ctx.request().getParam("thingId");
        this.db.findOne("digital_twin", new JsonObject().put("thingId", id), null, res -> {
            if (res.succeeded()) {
                JsonObject result = res.result();
                if (result != null) {
                    JsonObject properties = CommonController.checkProperties(result);
                    if (properties != null && properties.containsKey("wind")) {
                        JsonObject wind = properties.getJsonObject("wind");
                        if (wind.containsKey("data")) {
                            float value = wind.getFloat("data");
                            HttpServerResponse response = ctx.response();
                            response.putHeader("content-type", "application/json");
                            response.end(new JsonObject().put("wind", value).encodePrettily());
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

