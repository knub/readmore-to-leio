#!/bin/bash

/opt/android-sdk/platform-tools/adb push $1 /data/data/knub.readmore_to_leio/files
basename $1
