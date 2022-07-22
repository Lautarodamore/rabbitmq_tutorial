import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import com.rabbitmq.client.Delivery
import java.nio.charset.StandardCharsets.UTF_8


fun main() {
    Receiver().receive()
}

class Receiver {
    val queueName = "hello"

    fun receive() {
        val factory = ConnectionFactory()
        factory.host = "localhost"
        val connection = factory.newConnection()
        val channel = connection.createChannel()
        channel.queueDeclare(queueName, false, false, false, null)
        println(" [*] Waiting for messages. To exit press CTRL+C")

        val deliverCallback = DeliverCallback { consumerTag: String?, delivery: Delivery ->
            val message = String(delivery.body, UTF_8)
            println(" [x] Received '$message'")
        }

        channel.basicConsume(queueName, true, deliverCallback) { consumerTag: String? -> {} }
    }
}