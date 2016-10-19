#!/bin/sh

echo_usage() {
    echo "Usage: choose between threads or events."
    echo "    $ ./connect.sh threads"
    echo "    $ ./connect.sh events"
}

if [ ! ${JAVA_HOME} ] ; then
    echo "Please define JAVA_HOME."
    exit
fi
JAVA=$JAVA_HOME/bin/java

if [ ! $1 ] ; then
    echo_usage
    exit
fi

$JAVA -cp ./bin edu.ujf.samples.netcat.$1.Main -accept -port=5555
