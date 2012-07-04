#!/bin/bash
# Author: "Nelson Lago" <lago@ime.usp.br>
# Licence: GPL3 or later

# Find the interface that has the default route and
# return the reverse DNS for that interface's IP address.
# This name is used by chef to identify the node and
# this cannot be easily overriden.

# To avoid surprises caused by different formatting under
# different locales
unset LANG LANGUAGE LC_ALL

# This regex matches an IP address; it uses extended syntax,
# compatible with "egrep"
IP_ADDRESS_REGEX='([12]?[0-9]?[0-9]\.){3}[12]?[0-9]?[0-9]'

#### FUNCTIONS DEFINITION ####

check_valid_ip() {
    valid=0 # true
    if echo "$1" | egrep -q "^$IP_ADDRESS_REGEX\$"; then
        oldifs="$IFS"
        IFS="${IFS}."
        for number in $1; do
            if [ $number -gt 255 ]; then
                valid=1 # false
            fi
        done
        IFS="$oldifs"
    else
        valid=1 # false
    fi
    return $valid
}

find_defaultroute_interface() {

    defaultroute_line="`route -n | egrep '^0\.0\.0\.0'`"

    if ! echo "$defaultroute_line" | egrep -q '^0\.0\.0\.0'; then
    echo "Cant find default route on this machine" 1>&2
            exit 1
    fi

    echo $defaultroute_line | tr -s " "| cut -f8 -d" "
}

find_interface_ipaddress() {
    
    interface=$1
    # ifconfig usually gives us the IP address on the second line
    # of output, but lets try to be more forgiving of different
    # formats
    addressline="`ifconfig $interface | egrep "$IP_ADDRESS_REGEX"`"

    if [ "`echo "$addressline" | wc -l`" -ne 1 ]; then
        echo "Cannot parse ifconfig output (wrong number of matching lines)" 1>&2
        exit 1
    fi

    if ! echo "$addressline" | egrep -q "$IP_ADDRESS_REGEX"; then
        echo "Cannot parse ifconfig output (no IP found)" 1>&2
        exit 1
    fi

    # Remove unnecessary spaces in three steps:

    # 1. Two or more spaces in a row
    # 2. Leading and trailing spaces
    # 3. spurious spaces around the colon (these may appear "by surprise"
    #    because of the locale)
    addressline="`echo "$addressline" | sed -r -e 's/ +/ /g' -e 's/^ //' -e 's/ $//' -e 's/ ?: ?/:/g'`"

    # Now cut the field we want to get the ip address
    echo "`echo "$addressline" | cut -f2 -d: | cut -f1 -d" "`"
}

#### START OF SCRIPT ####

iface="`find_defaultroute_interface`"

ip="`find_interface_ipaddress "$iface"`"

if ! check_valid_ip "$ip"; then
echo "Could not determine IP address!" 1>&2
        exit 1
fi

# Find the reverse DNS address; dig informs this with the final dot,
# but we do not want that, so we remove it.
reverse_name=`dig +short -x "$ip" | sed -e s'/\.$//'`

if [ `echo "$reverse_name" | wc -w` -ne 1 ]; then
    echo "Could not find reverse DNS entry!" 1>&2
    exit 1
fi

echo $reverse_name
