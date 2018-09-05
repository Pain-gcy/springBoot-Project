#!/bin/bash
export LANG=en_US.UTF-8
SCRIPT_HOME=$(dirname $(readlink -f $0))
bash -ex $SCRIPT_HOME/startup.sh 9006