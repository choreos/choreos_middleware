#!/bin/bash
echo Cleaning Chef Server...
./clean_chef.sh
echo ...done!
echo
echo Terminating Amazon instances...
./clean_aws.sh
echo ...done!
