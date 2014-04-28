#include <jni.h>

#include "zip.h"
#include <android/Log.h>
#include <time64.h>

/* get #of elements in a static array */
#ifndef NELEM
# define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))
#endif

#define LOG_TAG "NativeAction"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__)


const char* getString(JNIEnv* env, jstring in) {
	jsize len = env->GetStringLength(in);
	char* buf = new char[len + 1];
	env->GetStringUTFRegion(in, 0, len, buf);
	return buf;
}

JNIEXPORT void JNICALL Java_com_l2a_main_action_NativeAction_addRecord_JJ(JNIEnv* env, jobject thiz,
		jlong l1,
		jlong l2) {
	LOGI("addRecord(%016llX,%016llX)", l1, l2);

	time64_t time = l1;
	tm* muhaha = gmtime64(&time);

	LOGD("asctime64(muhaha)=%s", asctime64(muhaha));
	LOGD("ctime64(muhaha)=%s", ctime64(&time));

}

JNIEXPORT void JNICALL Java_com_l2a_main_action_NativeAction_write(JNIEnv* env, jobject thiz, jstring filename) {
	const char* path = getString(env, filename);
	LOGD("write(%s)", path);
	delete[] path;
}

static JNINativeMethod sMethods[] = {
	{"addRecord", "(JJ)V", (void*) Java_com_l2a_main_action_NativeAction_addRecord_JJ},
	{"write", "(Ljava/lang/String;)V", (void*) Java_com_l2a_main_action_NativeAction_write},
};

int register_com_l2a_main_action_NativeAction(JNIEnv* env)
{
	jclass cls = env->FindClass("com/l2a/main/action/NativeAction");
	if (NULL == cls)
	{
		LOGE("Failed to get NativeAction class.");
		return JNI_FALSE;
	}

	if (env->RegisterNatives(cls, sMethods, NELEM(sMethods)) < 0)
	{
		LOGE("Failed to register methods for NativeAction");
		env->DeleteLocalRef(cls);
		return JNI_FALSE;
	}
	LOGV("Registered native methods for NativeAction.");

	env->DeleteLocalRef(cls);
	return JNI_TRUE;
}

extern "C" jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    JNIEnv* env = NULL;
    jint result = -1;

    if (vm->GetEnv((void**) &env, JNI_VERSION_1_4) != JNI_OK) {
        LOGE("GetEnv failed!");
        return result;
    }

	result = env->EnsureLocalCapacity(256);
	LOGI("env->EnsureLocalCapacity(512)=%d", result);

	result = env->PushLocalFrame(256);
	LOGI("env->PushLocalFrame(512)=%d", result);

    register_com_l2a_main_action_NativeAction(env);

    return JNI_VERSION_1_4;
}
