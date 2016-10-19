#!/bin/sh
#
# This script allows to run the JUnit tests under the "tests" directory.
# But JUnit tests are best run from Eclipse that reports nicely what went wrong.
# However, nightly non-regression tests are best run scripted.
 
JAVA="/homex/opt/jdk1.8.0_31/bin/java -server "

JUNIT="/homex/opt/eclipse-mars/plugins/org.junit_4.12.0.v201504281640/junit.jar:/homex/opt/eclipse-mars/plugins/org.hamcrest.core_1.3.0.v201303031735.jar"

CLASSPATH="-cp ./bin:$JUNIT"

$JAVA $CLASSPATH edu.ujf.tests.utils.Harness $*


