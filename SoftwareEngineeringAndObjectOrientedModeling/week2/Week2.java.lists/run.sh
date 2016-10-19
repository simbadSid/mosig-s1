#!/bin/sh
#
# This script allows to run the tests under the "tests" directory.
# It can be used to run the JUnit tests from the command line.
# In particular, this is useful if one is interested to produce 
# a profiling, through HPROF.
# To run tests with HPROF, you must set the HPROF variable, 
# see below. Be aware that turning HPROF hurst performance
# significantly.

# HPROF Details:
#    http://docs.oracle.com/javase/7/docs/technotes/samples/hprof.html
# Uncomment the following lines and adapt them to your needs, 
# if you wish HPROF. However, know HPROF is really slow.
#
#HPROF="-agentlib:hprof=cpu=times,verbose=y,file=java.hprof"

# Please find below some common usage...
#
#    ARGS="-nowarmup edu.ujf.perfs.utils.TestA edu.ujf.perfs.utils.TestB"
#
#    ARGS="edu.ujf.perfs.utils.TestA edu.ujf.perfs.utils.TestB"
#
#    ARGS="-jvisualvm edu.ujf.perfs.utils.TestA edu.ujf.perfs.utils.TestB"
# 

JAVA="/homex/opt/jdk1.8.0_31/bin/java -server "
JUNIT="/homex/opt/eclipse-mars/plugins/org.junit_4.12.0.v201504281640/junit.jar:/homex/opt/eclipse-mars/plugins/org.hamcrest.core_1.3.0.v201303031735.jar"
CLASSPATH="-cp ./bin:$JUNIT"
CLASSPATH="-cp ./bin"
$JAVA $HPROF $CLASSPATH edu.ujf.perfs.Harness $ARGS $*


