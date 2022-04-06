#! /bin/bash -x 


# 运行环境shell脚本记录 

# redis  ~/bin/env/redis/src/redis-server.sh ~/bin/env/redis/src/redis.config 
# mysql sudo systemctl start mysql.service 
# zookeeper zookeeperBin目录下 bin/zkServer.sh [--config <conf-dir>] {start|start-foreground|stop|version|restart|status|print-cmd} 
# 可以显式的设置配置文件目录, 不需要指名文件, 会自动查找目录下zoo.cfg 
# kafka 依赖于zookeeper, 注意port端口号的配置 
# 进入kafkabin目录下  ./bin/kafka-server-start.sh ./config/server.properties 

# ubuntu 开发环境一键启动全部环境 
sudo systemctl start mysql.service 
~/bin/env/redis-6.2.6/src/redis-server ~/bin/env/redis-6.2.6/redis.conf 
~/bin/env/apache-zookeeper-3.7.0-bin/bin/zkServer.sh start  # 停止使用stop 
~/bin/env/kafka_2.13-3.0.0/bin/kafka-server-start.sh ~/bin/env/kafka_2.13-3.0.0/config/server.properties






