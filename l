#!/bin/sh

ant debug
adb -e install -r bin/HelloWorld1-debug.apk
