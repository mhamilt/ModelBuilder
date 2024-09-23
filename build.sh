#!/bin/bash
cd src/modelbuilderMk2
/Applications/Processing.app/Contents/PlugIns/jdk-17.0.8+7/Contents/Home/bin/javac @./options $(find ./ -name "*.java")
cd -

