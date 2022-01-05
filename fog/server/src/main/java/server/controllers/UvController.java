package server.controllers;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

/**
 * UV Controller
 */
public class UvController {
    private final MongoClient db;

    public UvController(MongoClient db) {
        this.db = db;
    }

    /**
     * Getter of uv data.
     *
     * @param ctx, routing context.
     */
    public void getUv(RoutingContext ctx) {
        String id = ctx.request().getParam("thingId");
        this.db.findOne("digital_twin", new JsonObject().put("thingId", id), null, res -> {
            if (res.succeeded()) {
                JsonObject result = res.result();
                if (result != null) {
                    JsonObject properties = CommonController.checkProperties(result);
                    if (properties != null && properties.containsKey("uv")) {
                        JsonObject uv = properties.getJsonObject("uv");
                        if (uv.containsKey("data")) {
                            float value = uv.getFloat("data");
                            HttpServerResponse response = ctx.response();
                            response.putHeader("content-type", "application/json");
                            response.end(new JsonObject().put("uv", value).encodePrettily());
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
