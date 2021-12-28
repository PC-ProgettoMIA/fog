import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authentication.UsernamePasswordCredentials;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;

/**
 * Application to send data to cloud.
 */
public class AppToCloud {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DB_NAME = "DigitalTwin";
    private static final String COLLECTION_NAME = "digital_twin";

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        WebClient client = WebClient.create(vertx);
        JsonObject config = new JsonObject();
        config.put("connection_string", CONNECTION_STRING);
        config.put("db_name", DB_NAME);
        MongoClient db = MongoClient.create(vertx, config);

        long timerID = vertx.setPeriodic(1000, new Handler<Long>() {

            @Override
            public void handle(Long aLong) {
                db.find(COLLECTION_NAME, new JsonObject(), dbResponse -> {
                    if (dbResponse.succeeded()) {
                        for (JsonObject jsonDocument : dbResponse.result()) {
                            if (jsonDocument.containsKey("thingId")) {
                                String thingId = jsonDocument.getString("thingId");
                                String id = thingId.split(":")[1];
                                HttpRequest<Buffer> request = client.put(3128, "137.204.107.148", "/api/ditto/" + id);
                                MultiMap headers = request.headers();
                                headers.set("content-type", "application/json");
                                jsonDocument.remove("_id");
                                System.out.println(jsonDocument);
                                request.authentication(new UsernamePasswordCredentials("ditto", "ditto"));
                                request.sendJsonObject(jsonDocument)
                                        .onSuccess(res -> {
                                            System.out.println(res.body());
                                            res.statusCode(200);
                                        });
                            } else {
                                throw new InternalError("Presence of corrupted digital twins in system.");
                            }
                        }
                    } else {
                        dbResponse.cause().printStackTrace();
                        throw new InternalError("Database offline.");
                    }
                });
            }
        });
    }
}
