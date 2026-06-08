import pika
import json

def publicar_pedido(pedido):
    connection = pika.BlockingConnection(
        pika.ConnectionParameters(host="localhost")
    )

    channel = connection.channel()

    channel.queue_declare(
        queue="pedidos",
        durable=True
    )

    channel.basic_publish(
        exchange="",
        routing_key="pedidos",
        body=json.dumps(pedido),
        properties=pika.BasicProperties(
            delivery_mode=2  # persistente
        )
    )

    connection.close()