#!/bin/bash
ec2-describe-instances --filter instance-state-name=running | grep INSTANCE | tr -s "\t" | cut -f 2
