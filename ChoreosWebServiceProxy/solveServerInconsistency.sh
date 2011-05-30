#!/bin/bash

mv $1 $1~

REGEXP="package hello;"
REPLACEMENT="package hello; \n import java.lang.reflect.Proxy;"

sed -r "s/$REGEXP/$REPLACEMENT/g" $1~ >> $1

mv $1 $1~

REGEXP="Object implementor = new (.+)Impl\(\);"
REPLACEMENT="Object implementor = Proxy.newProxyInstance(\1.class.getClassLoader(), new Class[] { \1.class }, new GenericImpl());"

sed -r "s/$REGEXP/$REPLACEMENT/g" $1~ >> $1
