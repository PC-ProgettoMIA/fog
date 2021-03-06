/*
 *
 *  * Copyright (c) 2021.  Ylenia Battistini, Enrico Gnagnarella, Matteo Scucchia
 *  *
 *  *                              Licensed under the Apache License, Version 2.0 (the "License");
 *  *                              you may not use this file except in compliance with the License.
 *  *                              You may obtain a copy of the License at
 *  *
 *  *                                  http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *                              Unless required by applicable law or agreed to in writing, software
 *  *                              distributed under the License is distributed on an "AS IS" BASIS,
 *  *                              WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *                              See the License for the specific language governing permissions and
 *  *                              limitations under the License.
 *
 */

plugins {
    id("common-java")
    java
    application
}

sourceSets {
    main {
        resources {
            srcDir("src/main/java")
        }
    }
}

application {
    mainClass.set("Subscriber")
}


dependencies {
    implementation("io.vertx:vertx-web:4.2.1")
    implementation("com.sun.mail:javax.mail:1.6.2")
    implementation("org.json:json:20210307")
    implementation("org.slf4j:slf4j-api:_")
    implementation("com.hivemq:hivemq-mqtt-client:_")
    implementation("org.mongodb:mongo-java-driver:_")
}

