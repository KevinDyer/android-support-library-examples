/*
 * onload.cpp
 *
 *  Created on: Apr 17, 2014
 *      Author: kevindy
 */
#include <jni.h>
#include <stdlib.h>
#include <android/Log.h>

#define LOG_TAG "onload"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

namespace l2a {
int register_com_l2a_main_action_NativeAction(JNIEnv* env);
};

using namespace l2a;

extern "C" jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    JNIEnv* env = NULL;
    jint result = -1;

    if (vm->GetEnv((void**) &env, JNI_VERSION_1_6) != JNI_OK) {
        LOGE("GetEnv failed!");
        return result;
    }

    register_com_l2a_main_action_NativeAction(env);

    return JNI_VERSION_1_6;
}



