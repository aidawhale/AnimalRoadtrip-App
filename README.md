# AR! Animal Roadtrip

![](https://github.com/aidawhale/tfmarcore/blob/master/GitHubImages/logo_name.png)

In this project, a mobile application has been developed. 
This app can be used to motivate paediatric patients and thus enhance the physical activity performed by them using Augmented Reality technologies.
This application does not require any previous configuration by the user, since it uses ARCore's automatic surface detection technology.

In addition, this application is complemented by a system that allow for the collection and display of patient usage data.
These data will be of great interest to doctors, who will have a dynamic and indirect way of collecting data on the mobility and mood of their patients.
The web tool through which each patient's data can be consulted and modified is simple and intuitive, since the data are shown in a visual way through graphics, so that it is comfortable and easy to understand.

The application includes three games: 

 * **Marco Polo,** where one of the players is hiding and the others in the group have to find them by their voice. In this case, the hidden player is replaced by the device, which will emit sounds to make it easier for patients to find it.

 * **Jungle adventure.** This game consists of walking through a space (room or corridor) scanning the horizontal surfaces, where 3D animals will appear. The user will have to approach to the animals and click on them, so the next animal can appear.

 * **Map explorer.** This game consists of a collaborative activity in which a user visualizes a 3D map (placed on a real surface) and interacts with the other user, who has the clues. Between the two of them, they will have to interact and find the solution to the puzzle.

__________________________________________

Pictures of the different parts of the Android application are shown below:

 * [Login process](https://github.com/aidawhale/tfmarcore#login-process)
 * [Daily survey](https://github.com/aidawhale/tfmarcore#daily-survey)
 * [Select language](https://github.com/aidawhale/tfmarcore#select-language)
 * [Games](https://github.com/aidawhale/tfmarcore#games)
   * [Marco Polo](https://github.com/aidawhale/tfmarcore#marco-polo-game)
   * [Jungle adventure](https://github.com/aidawhale/tfmarcore#jungle-adventure-game)
   * [Map explorer: North Pole](https://github.com/aidawhale/tfmarcore#explorer-north-pole)
   * [Map explorer: Sabana](https://github.com/aidawhale/tfmarcore#explorer-sabana)
   * [Map assistant: clues](https://github.com/aidawhale/tfmarcore#assistant-clues)
   
Furthermore, the [Installing APK](https://github.com/aidawhale/tfmarcore#installing-apk) section details how to download and install the application on an Android device.

__________________________________________

## Login process

![](https://github.com/aidawhale/tfmarcore/blob/master/GitHubImages/login.png)

## Daily survey

![](https://github.com/aidawhale/tfmarcore/blob/master/GitHubImages/survey_new_login.png)

## Select language

![](https://github.com/aidawhale/tfmarcore/blob/master/GitHubImages/language.png)

## Games

### Marco Polo game

![](https://github.com/aidawhale/tfmarcore/blob/master/GitHubImages/marcopolo.png)

### Jungle adventure game

![](https://github.com/aidawhale/tfmarcore/blob/master/GitHubImages/jungle_adventure.png)

### Map explorer game

#### Explorer: North Pole

![](https://github.com/aidawhale/tfmarcore/blob/master/GitHubImages/map_explorer_pingu.png)

#### Explorer: Sabana

![](https://github.com/aidawhale/tfmarcore/blob/master/GitHubImages/map_explorer_sabana.png)

#### Assistant: clues

![](https://github.com/aidawhale/tfmarcore/blob/master/GitHubImages/map_assistant.png)

__________________________________________

## Installing APK

In the [Releases page](https://github.com/aidawhale/tfmarcore/releases) it is possible to find the APK compiled with the minimum viable product (MVP) version of the project. 

To be able to use all the application's functionalities you need an ARCore compatible device.

Keep in mind that it is still a beta version of the application, so it has only been tested on the following devices:

 * OnePlus 6T
 * Samsung Galaxy Tab S4
 * Redmi Note 7

Otherwise, if you want to compile your own version of the app, here we explain two ways to do it: 

**Debug mode:**
 1. *Build* > *Build Bundle/APK* > *Build APK*
 
 2. By default the generated file will be saved in the folder: "*your_proyect_path*/app/build/outputs/apk/debug"
 
 3. Send the APK to your Android device and follow the steps to install it
 
 4. You may need to activate [developer mode](https://developer.android.com/studio/debug/dev-options) on your device

![](https://github.com/aidawhale/tfmarcore/blob/master/GitHubImages/debug_apk.png)

**Signed APK and Release mode:**
 1. *Build* > *Generate signed bundle/APK*
 
 2. Select *APK* option
 
 3. Choose existing *KeyStore* or create a new one
 
 4. Select *release* build variant and specify destination folder
 
 5. Send the APK to your Android device and follow the steps to install it
 
 6. You may need to activate [developer mode](https://developer.android.com/studio/debug/dev-options) on your device

![](https://github.com/aidawhale/tfmarcore/blob/master/GitHubImages/signed_apk_1.png)

![](https://github.com/aidawhale/tfmarcore/blob/master/GitHubImages/signed_apk_2.png)
