package com.hyjoy.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;
import javax.xml.soap.Text;
import java.util.Enumeration;

/**
 * Created by hyjoy on 2018/3/24.
 */
public class Publisher {
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

        MessageProducer producer = session.createProducer(null);


        for (int i = 0; i < 100; i++) {

            TextMessage message = new ActiveMQTextMessage();
            message.setText("我是消息" + i);
            producer.send(destination, message);
        }
        connection.close();
        System.exit(0);

    }
}
