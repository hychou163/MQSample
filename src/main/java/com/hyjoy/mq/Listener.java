package com.hyjoy.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;

/**
 * Created by hyjoy on 2018/3/24.
 */
public class Listener {
    public static void main(String[] args) throws JMSException {
        // 第一步： 建立ConnectionFactory工厂对象，需要填入用户名密码，以及要连接的地址，均使用默认即可
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://192.168.33.132:61616"
        );

        // 第二步
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // 第三部： 通过Connection对象创建Session回话，用于接受消息
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 第四步： 通过Session穿件Destination对象，指的是一个客户端用来指定生成消息和消费消息来源的对象
        Destination destination = session.createQueue("first");

        MessageConsumer consumer = session.createConsumer(destination);

        while (true) {
            Message message = consumer.receive();
            if (message instanceof TextMessage) {
                System.out.println(((TextMessage) message).getText());
            }
        }


    }
}
