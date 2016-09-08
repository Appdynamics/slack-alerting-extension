#!/bin/sh

java -Dlog4j.configuration=file:conf/log4j.xml -jar slack-alert.jar "$@"
