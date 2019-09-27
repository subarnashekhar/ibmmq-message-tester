 docker run --env LICENSE=accept  --env MQ_QMGR_NAME=QM1 --publish 1414:1414 --publish 9443:9443 --detach --env MQ_APP_PASSWORD=password ibmcom/mq:latest
