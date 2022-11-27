**安装 Redis**

**直接执行命令：brew install redis 就可以了**

**安装完成之后，就可以直接在终端执行命令：**

- **redis-server：启动 redis 服务器，默认端口 6379**
- **redis-cli：启动 redis 客户端**

**配置 Redis 集群环境**

**redis 的默认配置文件（redis.conf）的路径位于：/usr/local/etc**

**创建虚拟节点目录、修改配置文件**

**创建 Redis 虚拟节点目录**

**在 /usr/local/etc 下创建 cluster 目录，并在 cluster 目录下创建目录：7000、7001、7002、7003、7004、7005。需要执行的命令：**

```bash
# 需要在 /usr/local/etc 路径下
mkdir -p redis/cluster/7000
mkdir -p redis/cluster/7001
mkdir -p redis/cluster/7002
mkdir -p redis/cluster/7003
mkdir -p redis/cluster/7004
mkdir -p redis/cluster/7005
```

**修改配置文件**

**拷贝 Redis 默认的配置文件（/usr/local/etc/redis.conf）到 7000、7001、7002、7003、7004、7005 这6个目录中。修改每一个目录下的配置文件，这里以 7000 为例：**

```bash
# cp redis.conf redis/cluster/7000/7000.conf
port 7000                                     # Redis 节点的端口号
cluster-enabled yes                           # 实例以集群模式运行
cluster-config-file nodes-7000.conf           # 节点配置文件路径
cluster-node-timeout 5000                     # 节点间通信的超时时间
appendonly yes                                # 数据持久化
```

**启动 Redis，并验证各个节点的状态**

**在任意目录执行下面的6个命令，即启动6个 Redis 节点：**

```bash
redis-server /usr/local/etc/redis/cluster/7000/7000.conf &
redis-server /usr/local/etc/redis/cluster/7001/7001.conf &
redis-server /usr/local/etc/redis/cluster/7002/7002.conf &
redis-server /usr/local/etc/redis/cluster/7003/7003.conf &
redis-server /usr/local/etc/redis/cluster/7004/7004.conf &
redis-server /usr/local/etc/redis/cluster/7005/7005.conf &
```

**通过命令启动之后，查看当前系统是否存在对应的进程。如果能看到如下类似的结果，则说明启动成功。**

```bash
ps -ef | grep redis
501 17291     1   0 12:26下午 ??         0:08.77 redis-server 127.0.0.1:7000 [cluster] 
501 17311     1   0 12:30下午 ??         0:07.86 redis-server 127.0.0.1:7001 [cluster] 
501 17319     1   0 12:33下午 ??         0:07.21 redis-server 127.0.0.1:7002 [cluster] 
501 17321     1   0 12:33下午 ??         0:07.12 redis-server 127.0.0.1:7003 [cluster] 
501 17323     1   0 12:33下午 ??         0:07.14 redis-server 127.0.0.1:7004 [cluster] 
501 17325     1   0 12:33下午 ??         0:07.06 redis-server 127.0.0.1:7005 [cluster]
```

**创建集群**

**当前系统中已经有了6个正在运行的 Redis 实例，这里使用的命令是create，因为我们要创建一个新集群。该选项`--cluster-replicas 1`意味着我们希望为每个创建的母版创建一个副本。执行以下命令：**

```bash
redis-cli --cluster create 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 --cluster-replicas 1
```

**`redis-cli`将提出一个配置。通过键入yes接受建议的配置。集群将被配置和加入，这意味着实例将被引导到彼此交谈。最后，如果一切顺利，您将看到如下消息：**

```bash
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
```

**验证**

**登录任意一个节点，执行命令**

```bash
# 登录任意一个节点，执行 set、get 等命令
redis-cli -c -p 7000
127.0.0.1:7000> set foo bar
-> Redirected to slot [12182] located at 127.0.0.1:7002
OK
127.0.0.1:7002> set hello word
-> Redirected to slot [866] located at 127.0.0.1:7000
OK
127.0.0.1:7000> get hello
"word"
127.0.0.1:7000> get foo
-> Redirected to slot [12182] located at 127.0.0.1:7002
"bar"
127.0.0.1:7002> 
```

**到此，Redis 集群的搭建就完成了。**