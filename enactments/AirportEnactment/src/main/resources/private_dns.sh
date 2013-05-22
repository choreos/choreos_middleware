#!/bin/bash
# Author: Nelson

# Find the interface that has the default route and
# return the reverse DNS for that interface's IP address.
# This name is used by chef to identify the node and
# this cannot be easily overriden.

defaultroute_line=`route -n | grep '^0\.0\.0\.0'`

if ! echo $defaultroute_line | grep -q '^0\.0\.0\.0'; then
        echo "No default route on this machine!" 1>&2
        exit 1
fi

defaultroute_interface=`echo $defaultroute_line | tr -s " "| cut -f8 -d" "`

ip=`ifconfig $defaultroute_interface | sed -n 2p | tr -s " " | cut -f2 -d: | cut -f1 -d" "`

if ! echo $ip | egrep -q '[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}'; then
        echo "Could not determine IP address!" 1>&2
        exit 1
fi

# Find the reverse DNS address; dig informs this with hte final dot,
# but we do not want that, so we remove it.
reverse_name=`dig +short -x $ip | sed -e s'/\.$//'`

if [ x$reverse_name == x ]; then
        echo "Could not find reverse DNS entry!" 1>&2
        exit 1
fi

echo $reverse_name
