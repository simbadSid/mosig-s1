#!/bin/sh

echo_usage() {
    echo "Usage: choose between threads or events."
    echo "    $ ./connect.sh socket"
    echo "    $ ./connect.sh threads"
    echo "    $ ./connect.sh events [channel2]"
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

if [ ! $2 ] ; then
    CHANNEL=channel
else
    CHANNEL=$2
fi

$JAVA -cp ./bin edu.ujf.samples.$CHANNEL.$1.Main -accept -port=5555
