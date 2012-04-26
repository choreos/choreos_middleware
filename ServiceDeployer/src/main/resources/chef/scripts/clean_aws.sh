#!/bin/bash
#Remove the AWS nodes and clients created on chef-server
#Author: Leonardo Leite

for node in `knife node list`
do
  if [ ${node:0:3} = 'ip-' -o ${node:0:5} = 'domU-' ]  
  then
    knife node delete $node -y -c $knifeFile
  fi
done

for client in `knife client list`
do
  if [ ${client:0:3} = 'ip-' -o ${client:0:5} = 'domU-' ]  
  then
    knife client delete $client -y -c $knifeFile
  fi
done
