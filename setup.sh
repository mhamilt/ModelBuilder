#!/bin/bash
# Gather all resources from Release links or external locations
# Put them in the right place
#
# Clearly this does nothing for the moment
#
# Get the ant zip and unzip to resources/code/
# Then bin the zip
#
# Download dependant .zips and add to libs/


curl https://archive.apache.org/dist/commons/math/binaries/commons-math3-3.6.1-bin.zip -o libs/commons-math3-3.6.1-bin.zip
unzip commons-math3-3.6.1-bin.zip
curl https://github.com/mhamilt/ModelBuilder/releases/download/0.9.0/apacheCommonsMathRotations.jar.zip -o libs/apacheCommonsMathRotations.jar.zip
curl https://github.com/mhamilt/ModelBuilder/releases/download/0.9.0/poly2tri.jar.zip -o libs/poly2tri.jar.zip
curl https://github.com/mhamilt/ModelBuilder/releases/download/0.9.0/ant-contrib-1.0b3.zip -o libs/ant-contrib-1.0b3.zip
curl https://github.com/mhamilt/ModelBuilder/releases/download/0.9.0/geomerative.zip -o libs/geomerative.zip