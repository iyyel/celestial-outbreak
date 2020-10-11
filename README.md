```
   _____     _           _   _       _    ____        _   _                    _    
  / ____|   | |         | | (_)     | |  / __ \      | | | |                  | |   
 | |     ___| | ___  ___| |_ _  __ _| | | |  | |_   _| |_| |__  _ __ ___  __ _| | __
 | |    / _ \ |/ _ \/ __| __| |/ _` | | | |  | | | | | __| '_ \| '__/ _ \/ _` | |/ /
 | |___|  __/ |  __/\__ \ |_| | (_| | | | |__| | |_| | |_| |_) | | |  __/ (_| |   < 
  \_____\___|_|\___||___/\__|_|\__,_|_|  \____/ \__,_|\__|_.__/|_|  \___|\__,_|_|\_\
  
  ▓▓▓▓▓▓▓▓▓▓
 ░▓ about  ▓ Breakout-like 2D video game
 ░▓ author ▓ iyyel <i@iyyel.io>
 ░▓ code   ▓ https://github.com/iyyel/celestialoutbreak
 ░▓ mirror ▓ https://git.iyyel.io/iyyel/celestialoutbreak
 ░▓▓▓▓▓▓▓▓▓▓
 ░░░░░░░░░░
```


## Table of Contents
 - [Introduction](#Introduction)
 - [Status](#Status)
 - [Release](#Release)
 - [Installing](#Installing)
 - [Previews](#Previews)
 - [License](#License)


# Introduction
Celestial Outbreak is a space-themed, Breakout-like 2D video game written in Java. It features the original Breakout experience in a colorful user interface with a few extra traits. The game is built with customization in mind, meaning the user is able to create their own levels by specifying properties such as dimension, size, speed and color of the blocks, paddle and ball, as well as the effects of power ups.

The goal with this project was to create a simple 2D game in Java with minimal usage of third-party libraries. The game solely relies on the Graphics2D Java library for drawing shapes to the screen. The flow of the game is structured as a finite-state machine such that the program always know which state to render and update.


# Status
In development.


# Release
Version v1.00 is in development currently. Expecting a final release *soon*.


# Installing
To install Celestial Outbreak, you can either download a [release](https://github.com/iyyel/celestialoutbreak/releases) version or clone this repository and compile it into a jar file.


# Previews
### Main Menu
![Main Menu](img/welcome_screen.png)

### Game Options
![GameOptions](img/game_options.png)

### Level Selection
![LevelSelect](img/select_level.png)

### Gameplay
![Gameplay](img/gameplay.gif)


# License
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE.md)
