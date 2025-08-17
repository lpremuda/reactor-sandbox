#!/bin/bash

# Script to set Java 17 for Gradle builds
# This helps avoid Java version compatibility issues

echo "Setting Java 17 for Gradle build..."

# Check if Java 17 is available
if [ -d "/Library/Java/JavaVirtualMachines/amazon-corretto-17.jdk/Contents/Home" ]; then
    export JAVA_HOME="/Library/Java/JavaVirtualMachines/amazon-corretto-17.jdk/Contents/Home"
    echo "✅ JAVA_HOME set to: $JAVA_HOME"
elif [ -d "/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home" ]; then
    export JAVA_HOME="/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home"
    echo "✅ JAVA_HOME set to: $JAVA_HOME"
else
    echo "❌ Java 17 not found in standard locations."
    echo "Please install Java 17 or set JAVA_HOME manually."
    echo ""
    echo "Available Java versions:"
    ls /Library/Java/JavaVirtualMachines/ 2>/dev/null || echo "No Java VMs found"
    exit 1
fi

# Verify Java version
echo ""
echo "Java version:"
"$JAVA_HOME/bin/java" -version

echo ""
echo "You can now run Gradle commands:"
echo "  ./gradlew build"
echo "  ./gradlew test"
echo ""
echo "To make this permanent, add the following to your shell profile:"
echo "  export JAVA_HOME=\"$JAVA_HOME\""
