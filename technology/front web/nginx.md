* 代理配置

````aidl
    server {
        listen       8002;
        server_name  127.0.0.1;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;


        location /p-trade-oc-admin/ {
            proxy_pass http://urms.uat1.rs.com:80;
        }

        location / {
            root /Users/M/work/redstar/p-trade/p-trade-oc/p-trade-oc-admin/src/main/resources/public/;
            index pages/BackOrder/backOrderList.html;
            try_files $uri  $uri/ /index.html = 404;
        }

    }

````