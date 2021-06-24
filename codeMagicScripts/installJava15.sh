#!/bin/bash
# Download Java 15.0.2 from URL
curl -O https://download.java.net/java/GA/jdk15.0.2/0d1cfde4252546c6931946de8db48ee2/7/GPL/openjdk-15.0.2_osx-x64_bin.tar.gz
# Remove existing JVMs
sudo rm -R /Library/Java/JavaVirtualMachines/*
# Move downloaded tar file into the JVM folder
sudo mv openjdk-15.0.2_osx-x64_bin.tar.gz /Library/Java/JavaVirtualMachines/
# Navigate into the JVM folder
cd /Library/Java/JavaVirtualMachines
# Unzip the tar file
sudo tar -xzf openjdk-15.0.2_osx-x64_bin.tar.gz
# Remove the tar file
sudo rm openjdk-15.0.2_osx-x64_bin.tar.gz
# Print location of Java 15
/usr/libexec/java_home -v15
# Remove current JAVA_HOME import from bash_profile
sed -i.old -E '/JAVA_HOME=*/d' ~/.bash_profile
# Set Java 15 location as JAVA_HOME
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-15.0.2.jdk/Contents/Home
# Add Java 15 location to bash_profile as JAVA_HOME
echo "JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-15.0.2.jdk/Contents/Home" >> ~/.bash_profile
# Update current instance to use the updated bash profile
source ~/.bash_profile