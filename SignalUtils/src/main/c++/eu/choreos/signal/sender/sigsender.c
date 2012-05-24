#include "eu_choreos_signal_sender_SignalSender.h"
#include <sys/param.h>
#include <sys/user.h>
#include <sys/sysctl.h>
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>

// to compile use: gcc -std=gnu99 sigsender.c ...

pid_t pid_from_process_name(const char*);

JNIEXPORT jint JNICALL 
Java_SignalSender_sendSignalByPID(
	JNIEnv * env, 
	jobject jobj, 
	jint pid, 
	jint signal) 
{

	return kill(pid, signal);

}


JNIEXPORT jint JNICALL 
Java_SignalSender_sendSignalByProcessName(
	JNIEnv *env, 
	jobject jobj, 
	jstring pname, 
	jint signal) 
{

	return Java_SignalSender_sendSignalByPID(env, jobj, pid_from_process_name((char*) pname), signal);

}

pid_t 
pid_from_process_name(
	const char * csProcessName) 
{

        const char* directory = "/proc";
	size_t      taskNameSize = 1024;
	char*       taskName = calloc(1, taskNameSize);

	DIR* dir = opendir(directory); // open /proc dir

	/* if dir is not null */
	if (dir)
	{
		struct dirent* de = 0; // struct to child dir of 'dir'

      		while ((de = readdir(dir)) != 0) // while can get next child directory
      		{
        		if (strcmp(de->d_name, ".") == 0 || strcmp(de->d_name, "..") == 0) // continues if dir is . or ..
          			continue;

         		int pid = -1; // return pid is -1 (if process is not running)
         		int res = sscanf(de->d_name, "%d", &pid); // set pid to name of dir. all running process have a dir with name=pid in /proc

         		if (res == 1)
         		{
            			char cmdline_file[1024] = {0};
            			sprintf(cmdline_file, "%s/%d/cmdline", directory, pid); // write /proc/<pid>/cmdline

            			FILE* cmdline = fopen(cmdline_file, "r"); // open /proc/<pid>/cmdline (this file contains a path to the service binary file)
	
            			if (getline(&taskName, &taskNameSize, cmdline) > 0) // if can get the first line
            			{
               				if (strstr(taskName, argv[1]) != 0) //verify if process name is substring of line
               				{
               					return pid; // return pid
					}
            			}

            			fclose(cmdline);
         		}
      		}

      		closedir(dir);
   	}

   	return -1;
}

