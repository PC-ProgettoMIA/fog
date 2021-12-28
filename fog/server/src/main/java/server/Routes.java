package server;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import server.controllers.*;

public class Routes {
  private final Router router;

  Routes(Vertx vertx,
         TemperatureController temperatureController,
         HumidityController humidityController,
         PressureController pressureController,
         Co2Controller co2Controller,
         TVOCController tvocController,
         QualityController qualityController,
         WindController windController,
         RainController rainController,
         UvController uvController) {
    this.router = Router.router(vertx);
    router.route().handler(BodyHandler.create());

    router.get("/api/temp/:thingId").handler(temperatureController::getTemperature);
    router.get("/api/hum/:thingId").handler(humidityController::getHumidity);
    router.get("/api/press/:thingId").handler(pressureController::getPressure);
    router.get("/api/co2/:thingId").handler(co2Controller::getCo2);
    router.get("/api/tvoc/:thingId").handler(tvocController::getTVOC);
    router.get("/api/qual/:thingId").handler(qualityController::getQuality);
    router.get("/api/wind/:thingId").handler(windController::getWind);
    router.get("/api/uv/:thingId").handler(uvController::getUv);
    router.get("/api/rain/:thingId").handler(rainController::getRain);

    router.errorHandler(404, routingContext -> {
        routingContext.response().setStatusCode(404).end("Route not found");
    });

  }

  Router getRouter() {
    return this.router;
  }



}
