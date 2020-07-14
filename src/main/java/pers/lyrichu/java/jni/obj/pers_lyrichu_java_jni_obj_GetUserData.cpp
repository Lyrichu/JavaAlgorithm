#include "pers_lyrichu_java_jni_obj_GetUserData.h"
#include<string>
#include<iostream>
using namespace std;

JNIEXPORT jobject JNICALL Java_pers_lyrichu_java_jni_obj_GetUserData_getUser
  (JNIEnv * env, jobject obj, jstring name, jint age)
{
  // create a class UserData,like reflection
  jclass userDataClass = env -> FindClass("pers/lyrichu/java/jni/obj/UserData");
  jobject userDataObj = env -> AllocObject(userDataClass);
  jfieldID nameField = env->GetFieldID(userDataClass , "name", "Ljava/lang/String;");
  jfieldID ageFeild = env ->GetFieldID(userDataClass,"age","I");
  env->SetObjectField(userDataObj, nameField, name);
  env->SetIntField(userDataObj,ageFeild,age);
  return userDataObj;
}

JNIEXPORT jstring JNICALL Java_pers_lyrichu_java_jni_obj_GetUserData_printUserData
  (JNIEnv * env, jobject obj, jobject userData)
{
   // Find the id of the Java method to be called
   jclass userDataClass=env->GetObjectClass(userData);
   jmethodID methodId=env->GetMethodID(userDataClass, "getUserData", "()Ljava/lang/String;");

   jstring result = (jstring)env->CallObjectMethod(userData, methodId);
   cout << env -> GetStringUTFChars(result,NULL) << endl;
   return result;
}