package utility;

import io.vertx.core.json.JsonObject;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class SendEmailTLS {

    public static void send(final String thingId, final List<String> sensors) {
        if (!sensors.isEmpty()) {
            final String username = "progetto.mia.notification@gmail.com";
            final String password = "progettoMIA2021";

            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true"); //TLS

            Session session = Session.getInstance(prop,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("progetto.mia.notification@gmail.com"));
                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse("enrico.gnagnarella@studio.unibo.it") /*, enrico.mp@alice.it*/
                );
                message.setSubject("Error on house MIA: " + thingId);
                String text = "La casina di rilevazione con id: " + thingId + "mostra problemi nei seguenti sensori: \n\n";
                for (String sensorName : sensors) {
                    text = text + "\t- sensore " + sensorName + ";\n";
                }
                text = text + " \n E' richiesto l'intervento di un operatore per riavviare la casetta e controllare i sensori." +
                        "\n\nGrazie dell'attenzione. \n\nProgetto MIA team.";
                message.setText("Ti voglio bene");
                Transport.send(text);

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SendEmailTLS.send("house01", CheckSensor.corruptedSensors(new JsonObject("{\n" +
                "    \"thingId\": \"my.houses:house01\",\n" +
                "    \"policyId\": \"house.mia:policy\",\n" +
                "    \"attributes\": {\n" +
                "        \"school\": \"Istituto Comprensivo Carchidio\",\n" +
                "        \"location\": {\n" +
                "            \"position\": {\n" +
                "                \"latitude\": 44.22996646689267,\n" +
                "                \"longitude\": 12.023054639722368\n" +
                "            }\n" +
                "        },\n" +
                "        \"serial number\": \"100\"\n" +
                "    },\n" +
                "    \"features\": {\n" +
                "        \"measurements\": {\n" +
                "            \"properties\": {\n" +
                "                \"humidity\": {\n" +
                "                    \"sensor\": \"Si7021hum\",\n" +
                "                    \"timestamp\": 1641420725102,\n" +
                "                    \"data\": 37.674468994140625\n" +
                "                },\n" +
                "                \"rain\": {\n" +
                "                    \"sensor\": \"rain sensor\",\n" +
                "                    \"timestamp\": 1641420725100,\n" +
                "                    \"data\": false\n" +
                "                },\n" +
                "                \"quality\": {\n" +
                "                    \"sensor\": \"PMS5003\",\n" +
                "                    \"timestamp\": 1641420725100,\n" +
                "                    \"data\": [\n" +
                "                        {\n" +
                "                            \"pm1_0_std\": 1\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"pm2_5_std\": 2\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"pm10_std\": 2\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                \"tvoc\": {\n" +
                "                    \"sensor\": \"SGP30\",\n" +
                "                    \"timestamp\": 1641420725100,\n" +
                "                    \"data\": 256\n" +
                "                },\n" +
                "                \"wind\": {\n" +
                "                    \"sensor\": \"anemometer\",\n" +
                "                    \"timestamp\": 1641420725100,\n" +
                "                    \"data\": 0\n" +
                "                },\n" +
                "                \"co2\": {\n" +
                "                    \"sensor\": \"SGP30\",\n" +
                "                    \"timestamp\": 1641420725100,\n" +
                "                    \"data\": 400\n" +
                "                },\n" +
                "                \"pressure\": {\n" +
                "                    \"sensor\": \"BMP280\",\n" +
                "                    \"timestamp\": 1641420725100,\n" +
                "                    \"data\": 997.4351026093183\n" +
                "                },\n" +
                "                \"temperature\": {\n" +
                "                    \"sensor\": \"Si7021\",\n" +
                "                    \"timestamp\": 1641420725100,\n" +
                "                    \"data\": 24.396823730468746\n" +
                "                },\n" +
                "                \"uv\": {\n" +
                "                    \"sensor\": \"UV sensor\",\n" +
                "                    \"timestamp\": 1641420725100,\n" +
                "                    \"data\": 0\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}")));

    }


}