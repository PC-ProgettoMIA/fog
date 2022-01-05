package server.controllers;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

/**
 * Pressure Controller
 */
public class PressureController {
    private final MongoClient db;

    public PressureController(MongoClient db) {
        this.db = db;
    }


    /**
     * Getter of pressure data.
     *
     * @param ctx, routing context.
     */
    public void getPressure(RoutingContext ctx) {
        String id = ctx.request().getParam("thingId");
        this.db.findOne("digital_twin", new JsonObject().put("thingId", id), null, res -> {
            if (res.succeeded()) {
                JsonObject result = res.result();
                if (result != null) {
                    JsonObject properties = CommonController.checkProperties(result);
                    if (properties != null && properties.containsKey("pressure")) {
                        JsonObject pressure = properties.getJsonObject("pressure");
                        if (pressure.containsKey("data")) {
                            float value = pressure.getFloat("data");
                            HttpServerResponse response = ctx.response();
                            response.putHeader("content-type", "application/json");
                            response.end(new JsonObject().put("pressure", value).encodePrettily());
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
