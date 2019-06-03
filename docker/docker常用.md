- 启动rabbitMQ
```
docker run -d --hostname my-rabbit --name some-rabbit -e RABBITMQ_DEFAULT_USER=dev -e RABBITMQ_DEFAULT_PASS=dev_user -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```