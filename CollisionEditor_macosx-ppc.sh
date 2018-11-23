#!/bin/sh

java -classpath 'dist:dist/lib:dist/CollisionEditor.jar' -Djava.library.path='dist:dist/lib/macosx-ppc:dist/CollisionEditor.jar' com.bianisoft.tools.collisioneditor.CollisionEditor
