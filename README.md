# sapr-app
* [Description](#description)
* [Features](#features)
* [Requirements](#requirements)
* [Build](#build)
* [Deploy](#deploy)
* [Run](#run)
## Description
Sapr-App is application for automating strength calculations of rod systems experiencing tension-compression.

## Features
Sapr-App creates the construction and draws it. In addition, it can calculate loads, draw diagrams and graphs.
### Preprocessor
* Input of data sets describing construction and external loads
* Visualization of construction and loads
* Generating project file
### Processor
`description in progress`
### Postprocessor
`description in progress`
## Requirements
* JDK 17 or higher
## Build
    $ git clone https://github.com/ksilisk/sapr-app.git
    $ cd sapr-app
For `Linux` or `MacOS` users
    
    $ bash mvnw clean install
Building on `Microsoft Windows` requires using `mvnw.cmd` instead of `mwnw` to run the `Maven Wrapper`
## Deploy
Change directory to `build`. This directory contains binary archives.
    
    $ cd build
Copy `sapr-app-VERSION-bin.zip` or `sapr-app-VERSION-bin.tar.gz` to a separate deployment directory. Extracting the distribution will create a new directory named for the version.<br>
In addition, the `build` folder will already contain a ready-to-run distribution.
## Run
Change directories to the deployment location or to the `build/sapr-app-VERSION-bin`<br>

    $ cd sapr-app-*
To run the application you need to running `sapr-app.sh` (for `Linux` and `MacOS` users) or `sapr-app.bat` (for `Microsoft Windows` users)

    $ bash sapr-app.sh