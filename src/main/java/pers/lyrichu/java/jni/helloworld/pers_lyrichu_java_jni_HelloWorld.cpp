#include "pers_lyrichu_java_jni_HelloWorld.h"
#include<iostream>
using namespace std;
JNIEXPORT void JNICALL Java_pers_lyrichu_java_jni_HelloWorld_sayHello(JNIEnv * env,jobject obj) {
cout << "hello world!" << endl;
}
