LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := Main
LOCAL_SRC_FILES := \
	zlib/adler32.c \
	zlib/crc32.c \
	zlib/deflate.c \
	zlib/infback.c \
	zlib/inffast.c \
	zlib/inflate.c \
	zlib/inftrees.c \
	zlib/trees.c \
	zlib/zutil.c \
	minizip/ioapi.c \
	minizip/mztools.c \
	minizip/unzip.c \
	minizip/zip.c \
	com_l2a_main_action_NativeAction.cpp \
	onload.cpp

LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/zlib \
	$(LOCAL_PATH)/minizip \

LOCAL_CFLAGS := -DIOAPI_NO_64 -DJNI
LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)
