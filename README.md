# Getpuzzles

Getpuzzles is a simple command-line application to download crossword puzzles from a variety of sources on the internet, and convert them to Across Lite (.puz) format on your local computer.

To date `getpuzzles` can download puzzles from the following sources:

- Newsday (daily)
- USA Today (Monday-Saturday, not holidays)
- Universal (daily)

## How to use

Assuming you have a Java SDK and Apache Ant installed, just clone the repository and build it with the included `build.xml`. The `/bin` directory has a script `getpuzzles` that invokes the application; run it with no arguments for a help message.

## Credit and license

Nearly all of the code here is adapted from Robert Cooper's excellent [Shortyz](https://github.com/kebernet/shortyz) crossword app for Android. All credit goes to him.

Licensed under the GNU General Public License, Version 3
