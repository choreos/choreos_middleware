# JAVA_HOME should not be empty

JAVA_HOME=`readlink -f /usr/bin/java | sed "s:jre/bin/java::"`



CC=gcc
INC_JAVA=-I$(JAVA_HOME)/include
INC_JAVA_LINUX=$(INC_JAVA)/linux
GNU_OPTS=-shared -std=gnu99 -fPIC

Main.class: libSignalSender.so
	javac Main.java

libSignalSender.so: SignalSender.h
	$(CC) -o libSignalSender.so -Wl,-soname,libSignalSender.so $(INC_JAVA) $(INC_JAVA_LINUX) sigsender.c $(GNU_OPTS)
	
SignalSender.h: SignalSender.class
	javah -jni SignalSender

SignalSender.class:
	javac SignalSender.java

clean:
	rm -f ./*.so ./*~ ./*.class ./*.h.gch
