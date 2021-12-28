package server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import server.controllers.*;

public class Server extends AbstractVerticle {

  private final int localPort;

  private Routes routes;

  public Server(final int localPort){
    this.localPort = localPort;
  }

  @Override
  public void start() throws Exception {
    initialize();
    getVertx().createHttpServer().requestHandler(this.routes.getRouter()).listen(localPort);
    System.out.println("Server online on port " + localPort);
  }

  private void initialize(){
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
}
