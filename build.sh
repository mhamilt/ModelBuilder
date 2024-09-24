#!/bin/bash


# 1. Download all files from the latest release on github

# 2. Unzip them in libs and organise them

# 3. compile .java files into .class files with javac

cd src/modelbuilderMk2
/Applications/Processing.app/Contents/PlugIns/jdk-17.0.8+7/Contents/Home/bin/javac @./options $(find ./ -name "*.java")
cd -

# 4. compile .class files into a jar with jar cvf modelbuilderMk2.jar 

jar cvf modelbuilderMk2.jar *

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