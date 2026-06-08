import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class KitchenConsumer {

    private final static String QUEUE_NAME = "pedidos";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        System.out.println("Cozinha ligada. Aguardando pedidos...\n");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            System.out.println("================================");
            System.out.println("Pedido recebido:");
            System.out.println(message);
            System.out.println("Processando...");
            System.out.println("================================\n");
        };

        channel.basicConsume(
                QUEUE_NAME,
                true,
                deliverCallback,
                consumerTag -> {}
        );
    }
}