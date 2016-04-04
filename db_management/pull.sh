#!/bin/bash

rm *.lbf 2> /dev/null
/opt/android-sdk/platform-tools/adb pull /data/data/knub.readmore_to_leio/files/Leio.realm
zip -r leio_timestamp.lbf Leio.realm
rm Leio.realm
