#!/bin/sh

java -classpath 'dist:dist/lib:dist/CollisionEditor.jar' -Djava.library.path='dist:dist/lib/solaris-sparcv9:dist/CollisionEditor.jar' com.bianisoft.tools.collisioneditor.CollisionEditor
