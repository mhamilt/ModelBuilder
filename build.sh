#!/bin/bash


# 1. Download all files from the latest release on github

# 2. Unzip them in libs and organise them

# 3. compile .java files into .class files with javac

cd src/modelbuilderMk2
/Applications/Processing.app/Contents/PlugIns/jdk-17.0.8+7/Contents/Home/bin/javac -cp ":/Applications/Processing.app/Contents/Java/core.jar:../../libs/geomerative.jar:../../libs/commons-math3-3.6.1.jar:../../libs/commons-math3-3.6.1.jar:../../libs/commons-math3-3.6.1-tools.jar:../../libs/commons-math3-3.6.1-tests.jar:../../libs/commons-math3-3.6.1-test-sources.jar:../../libs/commons-math3-3.6.1-sources.jar:../../libs/commons-math3-3.6.1-javadoc.jar:../../libs/poly2tri.jar:../../libs/apacheCommonsMathRotations.jar:../../libs/modelbuilderMk2.jar" -d ../../lib $(find ./ -name "*.java")
cd -
cd lib

# 4. compile .class files into a jar with jar cvf modelbuilderMk2.jar 

jar cvf modelbuilderMk2.jar *
cp modelbuilderMk2.jar ~/Documents/Processing/libraries/modelbuilderMk2/library/
cd -
cp libs/poly2tri.jar ~/Documents/Processing/libraries/modelbuilderMk2/library/
cp libs/geomerative.jar ~/Documents/Processing/libraries/modelbuilderMk2/library/


# 5. move everything into the library structure

# ├── examples
# │   └── Hello
# │       └── Hello.pde
# ├── library
# │   └── ModelBuilder.jar
# ├── library.properties
# ├── reference
# └── src
#     └── template
#         └── library
#             └── HelloLibrary.java

# Move all of that into ~/Processing/libraries

# Alternatively, use ant and resources/build.properties