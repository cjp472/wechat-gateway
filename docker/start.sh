#!/bin/sh
if [ ! -n "$1" ]; then
    echo "Usage: start.sh ( xxx.jar ... )"
    exit 1
else
    JAR_NAME="$1"
fi

# memory
limit_in_bytes=$(cat /sys/fs/cgroup/memory/memory.limit_in_bytes)
soft_limit_in_bytes=$(cat /sys/fs/cgroup/memory/memory.soft_limit_in_bytes)
if [ "$soft_limit_in_bytes" -gt "8589934592" ]
then
    soft_limit_in_bytes=134217728
fi
# If not default limit_in_bytes in cgroup
#if [ "$limit_in_bytes" -ne "9223372036854775807" ]
if [ "$limit_in_bytes" -lt "8589934592" ]
then
    limit_heap_size=$(expr $limit_in_bytes - $soft_limit_in_bytes)
#    limit_in_megabytes=$(expr $limit_in_bytes \/ 1048576)
#    heap_size=$(expr $limit_in_megabytes - $RESERVED_MEGABYTES)
    heap_size=$(expr $limit_heap_size \/ 1048576)
    export JAVA_OPTS="-Xmx${heap_size}m $JAVA_OPTS"
fi
JAVA_CMD=`which java`
JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom $JAVA_OPTS"
SPRING_OPTS="$SPRING_OPTS"

if [ "$ENV_NAME" == "" -o "$ENV_NAME" == "test" ]; then
    SPRING_OPTS="--spring.profiles.active=test $SPRING_OPTS"
elif [ "$ENV_NAME" == "preview" ]; then
    SPRING_OPTS="--spring.profiles.active=preview $SPRING_OPTS"
elif [ "$ENV_NAME" == "prod" ]; then
    SPRING_OPTS="--spring.profiles.active=prod $SPRING_OPTS"
fi

echo "    ENV_NAME : $ENV_NAME"
echo "    JAR_NAME : $JAR_NAME"
echo "    JAVA_CMD : $JAVA_CMD"
echo "   JAVA_OPTS : $JAVA_OPTS"
echo " SPRING_OPTS : $SPRING_OPTS"
exec "$JAVA_CMD" ${JAVA_OPTS} -jar "$@" ${SPRING_OPTS}