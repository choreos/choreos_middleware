CC=gcc
INC_JAVA=-I/usr/lib/jvm/default-java/include
INC_JAVA_LINUX=-I/usr/lib/jvm/default-java/include/linux
GNU_OPTS=-shared -std=gnu99 -fPIC

libMiddlewareSignalSender.so: sigsender.c
	$(CC) -o libMiddlewareSignalSender.so -Wl,-soname,libMiddlewareSignalSender.so $(INC_JAVA) $(INC_JAVA_LINUX) sigsender.c $(GNU_OPTS)

