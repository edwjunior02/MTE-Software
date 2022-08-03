# MUSIC TUNE EDUCATION - GROUP P41 MTL
## First Overview and aim of the project.
Our project is based on an interactive and educational software that helps users to practise and improve their intonation skills through simple but practical exercises that provide some feedback. This software is based on *JavaFX* and *Python* as programming languages for the frontend and backend blocks respectively. We also used some libraries and packages of both languages for improve the functionalities such as *[Essentia](https://essentia.upf.edu/essentia_python_tutorial.html) in Python*, *[sound.sampled](https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/package-summary.html) library in JavaFX* and many more.

## Functionality
The software starts with a simple interface where the user has to choose a level of difficulty and a range of intonation to suit his or her tone of voice.
![Untitled](https://user-images.githubusercontent.com/91899380/176373127-a0441d4a-cf7c-45c2-ba7c-854708bc6d63.png)
In addition, the user can consult the help window at any time by pressing the top left button `?`.
Once the user has selected the difficulty level and intonation range, the next screen can be accessed by pressing the lower `START` button.

The next screen of the software is the Microphone Selection. Here the user can select the microphone device he or she wants to perform the exercise.
However, the software can detect if the device selected can work correctly for the exercise or not.
Here you can see the next screen that appears when user press the previous `START` button:

![secondSceneKO](https://user-images.githubusercontent.com/91899380/176376583-a791dd13-253d-4803-983a-27fbc30dc5d5.png)
![secondSceneOK](https://user-images.githubusercontent.com/91899380/176376591-6ae13fa1-d0ea-4bb5-855a-b8db77304028.png)

As you can see in the previous figures, if the software can not obtain the resources of this device, the software will print out a red circle and will not let the user continue to the next screen. On the other side, if the device selected works correctly, the software will print out a green cercle and will let the user go ahead with his or her exercise.

Next, there is the screen when the user intonates the interval of the image. This image differs taking into account what voice range have choose the user. When it presses the sing button, it sounds a reference note A and a metronome, the recording of the voice starts too.

![WhatsApp Image 2022-06-28 at 9 48 38 PM](https://user-images.githubusercontent.com/72511506/176489348-e3faf004-3a3a-4bd9-91f4-24051894e2f5.jpeg)

After 15 seconds, the recording stops and a new window rates with a phrase how it was the performance of the user being "WELL DONE" the best rate, and "MAYBE NEXT TIME" the worst rate. The user can also retry with the same settings, go to the home page, or exit the application.

![WhatsApp Image 2022-06-28 at 9 02 28 PM](https://user-images.githubusercontent.com/72511506/176493360-2598f5cc-65ef-4235-8b02-d02145cbb8f3.jpeg)

## How it rates

The rating is defined by a pitch algorithm implemented when the user sings the musical note. If the musical note is the same or quite near the one shown on the musical sheet shown on screen, then the "WELL DONE" message pops up as stated before. If the musical note sang is very different to the one on screen, then the pitch algorithm will compare them and, when noticing it is very different, the message "MAYBE NEXT TIME" will pop up.
