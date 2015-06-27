# simple-android-project-template

Simple Android Project Template using:
 - Android Annotations (3.3.1)
 - Parse (1.9.2)
  - Bolts (1.2.0)
 - Otto Event Bus (1.3.7)
 - Glide (3.6.0)
 - Support Libraries (22.2.0)
  - support-v4
  - appcompat-v7
  - recyclerview-v7
  - cardview-v7
  - support-annotations
  - design

# Usage:
 - Clone repository;
 - Delete .git folder;
 - Import project at Android Studio;
 - Change Project Name using refactoring tool;
 - On app/build.gradle
  - Update any dependency version if needed;
  - Update AAVersion version if needed;
  - Update APP_ID to your applicationId;
  - Update PARSE_APP_ID and PARSE_CLIENT_KEY based on your Parse Keys;
 - Rename package name on AndroidManifest.xml;
 - Create package based on AndroidManifest package;
 - Move if needed, current source files to new package.
