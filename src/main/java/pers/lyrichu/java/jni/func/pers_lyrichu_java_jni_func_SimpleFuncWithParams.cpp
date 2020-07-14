#include "pers_lyrichu_java_jni_func_SimpleFuncWithParams.h"
#include<iostream>
#include<string>
using namespace std;

JNIEXPORT jint JNICALL Java_pers_lyrichu_java_jni_func_SimpleFuncWithParams_integerSum
  (JNIEnv * env, jobject obj, jint first, jint second)
{
    cout<<"first:"<<first<<","<<"second:"<<second<<endl;
    return first + second;
}

JNIEXPORT jstring JNICALL Java_pers_lyrichu_java_jni_func_SimpleFuncWithParams_sayHelloToMe
  (JNIEnv * env, jobject obj, jstring name, jboolean isFemale) {
  const char* nameCharPointer = env -> GetStringUTFChars(name,NULL);
  string title;
  if (isFemale) {
    title = "Mrs.";
  } else {
    title = "Mr.";
  }
  string fullname = "Hello," + title + nameCharPointer;
  return env -> NewStringUTF(fullname.c_str());
}