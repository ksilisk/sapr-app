# sapr-bar
* [Description](#description)
* [Features](#features)
* [Requirements](#requirements)
* [Build](#build)
* [Deploy](#deploy)
* [Run](#run)
## Description
Sapr-bar is application for automating strength calculations of rod systems experiencing tension-compression.

## Features
Sapr-bar creates the construction and draws it. In addition, it can calculate loads, draw diagrams and graphs.
### Preprocessor
* Input of data sets describing construction and external loads
* Visualization of construction and loads
* Generating project file
### Processor
`in progress`
### Postprocessor
`in progress`
## Requirements
* JDK 17 or higher
## Build
    $ git clone https://github.com/ksilisk/sapr-bar.git
    $ cd sapr-bar
For `Linux` or `MacOS` users
    
    $ bash mvnw clean install
Building on `Microsoft Windows` requires using `mvnw.cmd` instead of `mwnw` to run the `Maven Wrapper`
## Deploy
Change directory to `build`. This directory contains binary archives.
    
    $ cd build
Copy `sapr-bar-VERSION-bin.zip` or `sapr-bar-VERSION-bin.tar.gz` to a separate deployment directory. Extracting the distribution will create a new directory named for the version.<br>
In addition, the `build` folder will already contain a ready-to-run distribution.
## Run
Change directories to the deployment location or to the `build/sapr-bar-VERSION-bin`<br>

    $ cd sapr-bar-*
To run the application you need to running `sapr-bar.sh` (for `Linux` and `MacOS` users) or `sapr-bar.bat` (for `Microsoft Windows` users)

    $ bash sapr-bar.sh