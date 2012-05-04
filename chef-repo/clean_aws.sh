#!/bin/bash
# Terminates all running Amazon instances. Requires ec2-api-tools (http://va.mu/U5L5)
# Author: Carlos Eduardo Moreira dos Santos

AMAZON_INSTANCES=$(ec2-describe-instances --filter instance-state-name=running | grep INSTANCE | tr -s "\t" | cut -f 2 | xargs)
if [ ! -z "$AMAZON_INSTANCES" ]; then
    echo "Terminating $AMAZON_INSTANCES"
    ec2-terminate-instances $AMAZON_INSTANCES
fi
