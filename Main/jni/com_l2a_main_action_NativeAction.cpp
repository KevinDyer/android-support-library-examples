#include <jni.h>

#include "zip.h"

namespace l2a
{


const char* getString(JNIEnv* env, jstring in)
{
	jsize len = env->GetStringLength(in);
	char* buf = new char[len + 1];
	env->GetStringUTFRegion(in, 0, len, buf);
	return buf;
}

int register_com_l2a_main_action_NativeAction(JNIEnv *env)
{

}

}
