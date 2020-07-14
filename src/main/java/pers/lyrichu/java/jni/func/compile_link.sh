#!/usr/bin/env bash
MAIN_CLASS=pers.lyrichu.java.jni.func.SimpleFuncWithParams
JAVA_FILE=SimpleFuncWithParams.java
CPP_FILE=pers_lyrichu_java_jni_func_SimpleFuncWithParams.cpp
OBJECT_FILE=pers_lyrichu_java_jni_func_SimpleFuncWithParams.o
DYNAMIC_LIB_FILE=libnative.dylib # 动态链接库
javac -h . $JAVA_FILE
# 先编译生成 目标文件(.o)
g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/darwin $CPP_FILE -o $OBJECT_FILE
# 再生成动态库
g++ -dynamiclib -o $DYNAMIC_LIB_FILE $OBJECT_FILE -lc
DYNAMIC_LIB_DIR=`pwd`
cd /Users/huchengchun/javaCode/JavaAlgorithm
mvn package
java -cp target/Algorithm-1.0-SNAPSHOT.jar -Djava.library.path=$DYNAMIC_LIB_DIR $MAIN_CLASS
