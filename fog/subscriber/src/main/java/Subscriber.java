import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.exceptions.Mqtt3ConnAckException;
import com.hivemq.client.mqtt.mqtt3.exceptions.Mqtt3SubAckException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Subscriber {

    private static final String MONGO_CONNECTION = "mongodb://localhost:27017";
    private static final String DB_NAME = "DigitalTwin";
    private static final String COLLECTION_NAME = "digital_twin";
    private static final String MQQT_TOPIC = "house/mia";

    private final Mqtt3AsyncClient mqttClient;
    private final MongoClient mongoClient;
    private final MongoDatabase database;


    /**
     * Subscriber MQTT that receive message from the edge.
     */
    public Subscriber() {
        this.mqttClient = MqttClient.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost("137.204.107.250")
                .serverPort(3128)
                .useMqttVersion3()
                .buildAsync();

        this.mongoClient = MongoClients.create(MONGO_CONNECTION);
        this.database = this.mongoClient.getDatabase(DB_NAME);

        this.mqttClient.connect().whenCompleteAsync(
                (mqtt3ConnAck, throwable) -> {
                    if (throwable != null) {
                        throw new Mqtt3ConnAckException(mqtt3ConnAck, "Error on connection!", throwable);
                    } else {
                        mqttClient.subscribeWith().topicFilter(MQQT_TOPIC).callback(message -> {
                                    String mess = new String(message.getPayloadAsBytes(), StandardCharsets.UTF_8);
                                    JSONObject jsonDT = new JSONObject(mess);
                                    String id = jsonDT.getString("thingId");
                                    MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
                                    long count = collection.countDocuments(new Document().append("thingId", id));
                                    if (count != 0) {
                                        collection.replaceOne(new Document().append("thingId", id), Document.parse(mess));

                                    } else {
                                        collection.insertOne(Document.parse(mess));
                                    }
                                }).send()
                                .whenComplete((mqtt3SubAck, subThrowable) -> {
                                    if (subThrowable != null) {
                                        throw new Mqtt3SubAckException(mqtt3SubAck, "Error on message!", subThrowable);
                                    } else {
                                        System.out.println("Connected to the broker.");
                                    }
                                });
                    }
                });
    }

    public static void main(String[] args) {
        new Subscriber();
    }

}
