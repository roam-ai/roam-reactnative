# Roam React Native Example
Roam Location SDK example

### Install dependencies
```bash
yarn
# or
npm install
```

### Configuration
- Edit `MainApplication.java` and replace `"publishable_key"` with your API key.
- The **bundleIdentifier** should match the registered on Roam. Use `npx react-native-rename <newName> -b <bundleIdentifier>` to rename it.

  Example usage is as follows:
  ```bash
  # If you registered com.roamexample:
  npx react-native-rename "RoamExample" -b com.roamexample
  ```
  
### Run the app
```bash
# To start Metro server
yarn start
# To install and launch your app on the android device 
yarn android
```
