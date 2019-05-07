# last.fm-player
Music player with Last.fm integration

About this program:

This application is a music player with last.fm built-in integration. It synchronize user actions to a given last.fm account
(test account information will be given below) with drag-and-drop capabilities (i.e. dragging multiple music files into the application window)

How to run this program:

This is compiled in Eclipse (IDE) and programmed in Java. Download or clone the github through eclipse, complile it and run it.
Currently, it is already logged in with the account information below.

Account Information:
- Username: TestAccount3461
- password: meowmix

Compatible Music Files formats:
- mp3
- acc
- pcm
- m4a

External Jar File (Dependencies):
- ../lib/commons-codec-1.10.jar
- ../lib/lastfm-java.0.1.2.jar

NOTE:

Project runs using JavaFX libraries from Java 1.8, therefore, Java 1.8 is required. The Metadata version that this program can
process is version: ID3v1; meaning it cannot retrieve ID3v2 and over.
