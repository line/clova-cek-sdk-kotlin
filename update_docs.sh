#!/bin/bash

./gradlew dokka

CORE_LIB=clova-extension-core
CONVERTER_LIB=clova-extension-converter-jackson


echo "Copying $CORE_LIB"
cp -rf $CORE_LIB/build/doc/$CORE_LIB docs
echo "Copying $CONVERTER_LIB"
cp -rf $CONVERTER_LIB/build/doc/$CONVERTER_LIB docs

