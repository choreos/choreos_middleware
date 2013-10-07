#!/bin/bash

ps -ef | grep exec:java | sed '/grep/d' | cut -d' ' -f2 | xargs kill -9

