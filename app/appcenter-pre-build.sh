if [ -z "$VERSION_NAME" ]
then
    echo "You need define the VERSION_NAME variable in App Center"
    exit
fi
 
ANDROID_GRADLE_FILE=$APPCENTER_SOURCE_DIRECTORY/app/build.ANDROID_GRADLE_FILE
 
if [ -e "$ANDROID_GRADLE_FILE" ]
then
    echo "Updating version name to $VERSION_NAME ($APPCENTER_BUILD_ID) in build.gradle"
    sed -i '' 's/versionName "[0-9.]*"/versionName "'$VERSION_NAME'"/' $ANDROID_GRADLE_FILE
 
    echo "File content:"
    cat $ANDROID_GRADLE_FILE
fi