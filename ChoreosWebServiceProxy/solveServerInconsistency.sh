#!/bin/bash

echo $1

FILE=$1"/*Server.java"

mv $FILE $FILE~

REGEXP="package hello;"
REPLACEMENT="package hello; \n import java.lang.reflect.Proxy;"

sed -r "s/$REGEXP/$REPLACEMENT/g" $FILE~ > $FILE

mv $FILE $FILE~

REGEXP="Object implementor = new (.+)Impl\(\);"
REPLACEMENT="Object implementor = Proxy.newProxyInstance(\1.class.getClassLoader(), new Class[] { \1.class }, new GenericImpl());"

sed -r "s/$REGEXP/$REPLACEMENT/g" $FILE~ > $FILE

echo "----FILE-----"
cat $FILE
