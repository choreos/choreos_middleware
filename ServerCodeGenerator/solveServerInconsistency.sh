#!/bin/bash

REGEXP="Object implementor = new (.+)Impl\(\);"
REPLACEMENT="Object implementor = Proxy.newProxyInstance(\1.class.getClassLoader(), new Class[] { \1.class }, new GenericImpl());"

sed -r "s/$REGEXP/$REPLACEMENT/g" $1
