import com.rabbitmq.client.ConnectionFactory

fun main() {
    Sender().send()
}

class Sender {
    private val queueName = "hello"

    fun send() {
        val factory = ConnectionFactory()
        factory.host = "localhost"
        factory.newConnection().use {
            val channel = it.createChannel()
            channel.queueDeclare(queueName, false, false, false, null)
            val message = "Hello World!"
            channel.basicPublish("", queueName, null, message.toByteArray())
            println(" [x] Sent '$message'")
        }
    }
}