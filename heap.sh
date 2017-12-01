#!/usr/bin/env bash

DATE_WITH_TIME=`date "+%Y%m%d-%H%M%S"`
jmap -dump:format=b,file="ratpack-infinity_${DATE_WITH_TIME}.bin" $1
