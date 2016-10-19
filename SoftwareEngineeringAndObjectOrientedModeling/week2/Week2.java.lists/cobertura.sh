#!/bin/sh
# Cobertura allows to get reports for the JUnit results and the corresponding coverage.

cd cobertura

JAVA_HOME="/homex/opt/jdk1.8.0_31"
export PATH=$JAVA_HOME/bin:$PATH

ant clean
ant

firefox reports/junit-html/index.html reports/cobertura-html/index.html &




