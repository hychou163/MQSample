### ActiveMQ 说明

##### 使用步骤
* 建立ConnectionFactory工厂对象，需要填入用户名、密码以及要连接的地址
* 通过ConnectionFactory创建一个Connection连接，并且调用Connection的start方开启连接
* 通过Connection创建Session，用于接收消息，
    * arg1: 是否启用事务
    * arg2: 签收模式，一般我们设置为自动签收
* 通过Session创建Destination对象，指的是一个客户端用来指定生成消息目标和消费消息来源的对象，
    * 在PTP模式中，Destination被称作Queue即队列
    * 在Pub/Sub模式，Destination被称作Topic即主题
* 通过Session创建消息的发送和接受对象（MessageProducer、MessageConsumer）
* 使用MessageProducer的setDeliveryMode方法为期设置持久化特性
* 最后使用TextMessage形式创建数据，并通过MessageProducer的send发送数据，客户端使用receive方法进行接受数据，最后不要忘记关闭Connection


##### 安全机制
* web管理界面：http://127.0.0.1:8161/admin
* 实际项目中，只有符合认证的用户才能进行发送和获取消息，可以在activemq.xml里去添加安全验证配置
    
    <plugins>
        <simpleAuthenticationPlugin>
            <users>
                <authenticationUser username="admin" password="admin" groups="users, admins"/>
            </users>
        </simpleAuthenticationPlugin>
    </plugins>
    
##### 持久化
可以切换不同的存储技术（默认是kahadb、leveldb、mysql、oracle）

     <persistenceAdapter>
        <kahaDB directory="${activemq.data}/kahadb"/>
        <jdbcPersistenceAdapter directory="#mysql-ds"/>
     </persistenceAdapter>
     
     <!-- mysql 数据库持久层 -->
     <bean id="mysql-ds" class="org.apache.commons.dbcp.BasicDataSource" destory-method="close">
         <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
         <property name="url" value="jdbc:mysql://localhost:3306/test?relaxAutoCommit=true"/>
         <property name="username" value="root"/>
         <property name="password" value="123456"/>
         <property name="maxActive" value="200"/>
         <property name="pollPreparedStatements" value="true"/>
     </bean>


