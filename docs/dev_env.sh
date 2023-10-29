#! /bin/bash -x 

# 存在问题 raspberry 和线上环境无法直接启动kafka 无法完成部署 

# 运行环境shell脚本记录 

# redis  ~/bin/env/redis/src/redis-server.sh ~/bin/env/redis/src/redis.config 
# mysql sudo systemctl start mysql.service 
# zookeeper zookeeperBin目录下 bin/zkServer.sh [--config <conf-dir>] {start|start-foreground|stop|version|restart|status|print-cmd} 
# 可以显式的设置配置文件目录, 不需要指名文件, 会自动查找目录下zoo.cfg 
# kafka 依赖于zookeeper, 注意port端口号的配置 
# 进入kafka bin目录下  ./bin/kafka-server-start.sh ./config/server.properties 

# ubuntu 开发环境一键启动全部环境 
sudo systemctl start mysql.service 
~/bin/env/redis-6.2.6/src/redis-server ~/bin/env/redis-6.2.6/redis.conf 
~/bin/env/apache-zookeeper-3.7.0-bin/bin/zkServer.sh start 
~/bin/env/kafka_2.13-3.0.0/bin/kafka-server-start.sh ~/bin/env/kafka_2.13-3.0.0/config/server.properties


# raspberry pi 启动服务端环境脚本
# mysql, redis 直接启动服务
# zookeeper   kafka需要手动启动和执行
sudo systemctl start mysql.service
sudo systemctl start redis-server.service
/home/ubuntu/bin/apache-zookeeper-3.7.0-bin/bin/zkServer.sh start
/home/ubuntu/bin/kafka_2.13-3.0.0/bin/kafka-server-start.sh /home/ubuntu/bin/kafka_2.13-3.0.0/config/server.properties 
# 全部运行在树莓派空间不足, 需要交错



# prod环境开发环境启动
mysql redis 使用systemctl start ... 启动
/home/ubuntu/bin/apache-zookeeper-3.6.3-bin/bin/zkServer.sh start
/home/ubuntu/bin/kafka_2.13-2.8.0/bin/kafka-server-start.sh /home/ubuntu/bin/kafka_2.13-2.8.0/config/server.properties



#



