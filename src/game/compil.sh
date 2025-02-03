#!/bin/bash

# Compile all .java files recursively
# Finds all .java files starting from the current directory
# and passes them to the Java compiler in a single command.

echo "Searching for Java files..."
java_files=$(find . -name "*.java")

if [ -z "$java_files" ]; then
  echo "No .java files found."
  exit 1
fi

echo "Compiling the following files:"
echo "$java_files"
echo

# Compile all found files at once
javac $java_files

# Check if compilation was successful
if [ $? -eq 0 ]; then
  echo "Compilation successful."
else
  echo "Compilation failed."
  exit 1
fi

