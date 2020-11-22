#!/bin/bash

exec java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar $JAR_FILE
