#!/bin/bash

PHOME=$(dirname `readlink -f "$0"`)
PHOME=$(dirname $PHOME)

JAVA_OPTS="-server -Xms2048m -Xmx2048m -Xmn768m -XX:MaxPermSize=64m \
-Xss256k -XX:+UseConcMarkSweepGC \
-XX:+UseParNewGC -XX:CMSFullGCsBeforeCompaction=5 \
-XX:+UseCMSCompactAtFullCollection \
-XX:+PrintGC -Xloggc:/data1/logs/apollo/apollo-charging-sncmcc-zx-auth/gc.log"

pid=`ps -eo pid,args | grep apollo-charging-sncmcc-zx-auth | grep java | grep -v grep | awk '{print $1}'`
if [ -n "$pid" ]
then
    kill -3 ${pid}
    kill ${pid} && sleep 3
    if [  -n "`ps -eo pid | grep $pid`" ]
    then
        kill -9 ${pid}
    fi
    echo "kill pid: ${pid}"
fi

java $JAVA_OPTS -cp $PHOME/conf:$PHOME/lib/* com.cnbn.apollo.charging.sncmcc.zx.http.BootStrapApplication $1 > /dev/null 2>&1 &