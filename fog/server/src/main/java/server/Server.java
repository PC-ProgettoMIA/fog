package server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.JksOptions;
import io.vertx.ext.mongo.MongoClient;
import server.controllers.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.security.Key;
import java.util.Base64;

/**
 * Server of fog.
 */
public class Server extends AbstractVerticle {

    private static final String PATH_TO_RESOURCES = "server/src/main/resources";
    private static final String ENCRYPTION_PROTOCOL = "AES/ECB/PKCS5PADDING";

    private final int localPort;
    private Routes routes;
    
    public Server(final int localPort) {
        this.localPort = localPort;
    }

    @Override
    public void start() {
        initialize();
        try {
            String password = this.decryptPassword();
            getVertx().createHttpServer(

                    new HttpServerOptions()
                            .setSsl(true)
                            .setKeyStoreOptions(new JksOptions()
                                    .setPath(this.getUserDir() +
                                            this.getFileSeparator() +
                                            PATH_TO_RESOURCES +
                                            "/keystore.jks")
                                    .setPassword(password))
            ).requestHandler(this.routes.getRouter()).listen(localPort);
            System.out.println("Server online on port " + localPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        MongoClient db = MongoClient.create(vertx, new JsonObject().put("db_name", "DigitalTwin"));

        TemperatureController temperatureController = new TemperatureController(db);
        HumidityController humidityController = new HumidityController(db);
        PressureController pressureController = new PressureController(db);
        Co2Controller co2Controller = new Co2Controller(db);
        TVOCController tvocController = new TVOCController(db);
        QualityController qualityController = new QualityController(db);
        WindController windController = new WindController(db);
        RainController rainController = new RainController(db);
        UvController uvController = new UvController(db);

        this.routes = new Routes(vertx, temperatureController, humidityController, pressureController, co2Controller, tvocController
                , qualityController, windController, rainController, uvController);
    }

    public static void main(String[] args) {
        AbstractVerticle service = new Server(3001);
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(service);
    }

    private String decryptPassword() throws Exception {
        BufferedReader keyReader = keyReader = new BufferedReader(
                new FileReader(this.getUserDir() +
                        this.getFileSeparator() +
                        PATH_TO_RESOURCES +
                        "/key.txt"));
        String key = keyReader.readLine();
        keyReader.close();
        BufferedReader passwordReader = new BufferedReader(
                new FileReader(this.getUserDir() +
                        this.getFileSeparator() +
                        PATH_TO_RESOURCES +
                        "/password.txt"));
        String encryptedPassword = passwordReader.readLine();
        passwordReader.close();

        Cipher cipher = Cipher.getInstance(ENCRYPTION_PROTOCOL);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedPassword)));

    }

    private String getUserDir() {
        return System.getProperty("user.dir");
    }

    private String getFileSeparator() {
        return System.getProperty("file.separator");
    }
}
