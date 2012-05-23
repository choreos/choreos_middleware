#include "eu_choreos_signal_sender_SignalSender.h"
#include <signal.h>

pid_t pid_from_process_name(char*);

JNIEXPORT jint JNICALL 
Java_SignalSender_sendSignalByPID(
	JNIEnv * env, 
	jobject jobj, 
	jint pid, 
	jint signal) {

	return 0; //sigqueue(pid, signal, NULL);

}


JNIEXPORT jint JNICALL 
Java_SignalSender_sendSignalByProcessName(
	JNIEnv *env, 
	jobject jobj, 
	jstring pname, 
	jint signal) {

	return 0; //sigqueue(pid_from_process_name((char*) pname), signal, NULL);

}
