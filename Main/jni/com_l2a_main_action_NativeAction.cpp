#include <jni.h>

#include "zip.h"
#include <android/Log.h>

/* get #of elements in a static array */
#ifndef NELEM
# define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))
#endif

#define LOG_TAG "NativeAction"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__)

namespace l2a {

const char* getString(JNIEnv* env, jstring in) {
	jsize len = env->GetStringLength(in);
	char* buf = new char[len + 1];
	env->GetStringUTFRegion(in, 0, len, buf);
	return buf;
}

JNIEXPORT jint JNICALL com_l2a_main_action_NativeAction_addRecord(JNIEnv* env, jobject thiz,
		jint num,
		jstring p00,
		jstring p01,
		jstring p02,
		jstring p03,
		jstring p04,
		jstring p05,
		jstring p06,
		jstring p07,
		jstring p08,
		jstring p09,
		jlong timestamp,
		jstring p10,
		jstring p11,
		jstring p12,
		jstring p13,
		jstring p14,
		jstring p15,
		jstring p16,
		jstring p17,
		jstring p18,
		jstring p19,
		jstring p20,
		jstring p21,
		jstring p22,
		jstring p23,
		jstring p24,
		jstring p25,
		jstring p26,
		jstring p27,
		jstring p28,
		jstring p29,
		jstring p30,
		jstring p31,
		jstring p32,
		jstring p33,
		jstring p34,
		jstring p35,
		jstring p36,
		jstring p37,
		jstring p38,
		jstring p39) {
	const char* s00 = getString(env, p00);
	const char* s01 = getString(env, p01);
	const char* s02 = getString(env, p02);
	const char* s03 = getString(env, p03);
	const char* s04 = getString(env, p04);
	const char* s05 = getString(env, p05);
	const char* s06 = getString(env, p06);
	const char* s07 = getString(env, p07);
	const char* s08 = getString(env, p08);
	const char* s09 = getString(env, p09);
	const char* s10 = getString(env, p10);
	const char* s11 = getString(env, p11);
	const char* s12 = getString(env, p12);
	const char* s13 = getString(env, p13);
	const char* s14 = getString(env, p14);
	const char* s15 = getString(env, p15);
	const char* s16 = getString(env, p16);
	const char* s17 = getString(env, p17);
	const char* s18 = getString(env, p18);
	const char* s19 = getString(env, p19);
	const char* s20 = getString(env, p20);
	const char* s21 = getString(env, p21);
	const char* s22 = getString(env, p22);
	const char* s23 = getString(env, p23);
	const char* s24 = getString(env, p24);
	const char* s25 = getString(env, p25);
	const char* s26 = getString(env, p26);
	const char* s27 = getString(env, p27);
	const char* s28 = getString(env, p28);
	const char* s29 = getString(env, p29);
	const char* s30 = getString(env, p30);
	const char* s31 = getString(env, p31);
	const char* s32 = getString(env, p32);
	const char* s33 = getString(env, p33);
	const char* s34 = getString(env, p34);
	const char* s35 = getString(env, p35);
	const char* s36 = getString(env, p36);
	const char* s37 = getString(env, p37);
	const char* s38 = getString(env, p38);
	const char* s39 = getString(env, p39);
	LOGD("addRecord(%d,%lld)", num, timestamp);
	delete[] s00;
	delete[] s01;
	delete[] s02;
	delete[] s03;
	delete[] s04;
	delete[] s05;
	delete[] s06;
	delete[] s07;
	delete[] s08;
	delete[] s09;
	delete[] s10;
	delete[] s11;
	delete[] s12;
	delete[] s13;
	delete[] s14;
	delete[] s15;
	delete[] s16;
	delete[] s17;
	delete[] s18;
	delete[] s19;
	delete[] s20;
	delete[] s21;
	delete[] s22;
	delete[] s23;
	delete[] s24;
	delete[] s25;
	delete[] s26;
	delete[] s27;
	delete[] s28;
	delete[] s29;
	delete[] s30;
	delete[] s31;
	delete[] s32;
	delete[] s33;
	delete[] s34;
	delete[] s35;
	delete[] s36;
	delete[] s37;
	delete[] s38;
	delete[] s39;
	return num;
}

JNIEXPORT void JNICALL com_l2a_main_action_NativeAction_write(JNIEnv* env, jobject thiz, jstring filename) {
	const char* path = getString(env, filename);
	LOGD("write(%s)", path);
	delete[] path;
}

static JNINativeMethod sMethods[] = {
		{ "addRecord", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I", (void*) com_l2a_main_action_NativeAction_addRecord },
		{ "write", "(Ljava/lang/String;)V",	(void*) com_l2a_main_action_NativeAction_write }, };

int register_com_l2a_main_action_NativeAction(JNIEnv* env) {
	jclass clazz;

	LOGV("Registering %s natives\n", "com/l2a/main/action/NativeAction");
	clazz = env->FindClass("com/l2a/main/action/NativeAction");
	if (clazz == NULL) {
		LOGE("Native registration unable to find class '%s'\n", "com/l2a/main/action/NativeAction");
		return -1;
	}

	int result = 0;
	if (env->RegisterNatives(clazz, sMethods, NELEM(sMethods)) < 0) {
		LOGE("RegisterNatives failed for '%s'\n", "com/l2a/main/action/NativeAction");
		result = -1;
	}

	env->DeleteLocalRef(clazz);
	return result;
}

}
