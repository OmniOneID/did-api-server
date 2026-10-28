---
puppeteer:
    pdf:
        format: A4
        displayHeaderFooter: true
        landscape: false
        scale: 0.8
        margin:
            top: 1.2cm
            right: 1cm
            bottom: 1cm
            left: 1cm
    image:
        quality: 100
        fullPage: false
---

Open DID API Gateway Server Installation And Operation Guide
==

- Date: 2025-05-30
- Version: v2.0.0

Table of Contents
==

- [1. Introduction](#1-introduction)
  - [1.1. Overview](#11-overview)
  - [1.2. API Gateway Server Definition](#12-api-gateway-server-definition)
  - [1.3. System Requirements](#13-system-requirements)
- [2. Preparation](#2-preparation)
  - [2.1. Git Installation](#21-git-installation)
- [3. Cloning the Source Code from GitHub](#3-cloning-the-source-code-from-github)
  - [3.1. Cloning the Source Code](#31-cloning-the-source-code)
  - [3.2. Directory Structure](#32-directory-structure)
- [4. How to Run the Server](#4-how-to-run-the-server)
  - [4.1. Running with IntelliJ IDEA (Gradle Support)](#41-running-with-intellij-idea-gradle-support)
    - [4.1.1. Installing and Setting Up IntelliJ IDEA](#411-installing-and-setting-up-intellij-idea)
    - [4.1.2. Opening the Project in IntelliJ](#412-opening-the-project-in-intellij)
    - [4.1.3. Gradle Build](#413-gradle-build)
    - [4.1.4. Running the Server](#414-running-the-server)
    - [4.1.5. Server Configuration](#415-server-configuration)
  - [4.2. Running the Server Using Console Commands](#42-running-the-server-using-console-commands)
    - [4.2.1. Gradle Build Command](#421-gradle-build-command)
    - [4.2.2. Running the Server](#422-running-the-server)
    - [4.2.4. Server Configuration](#424-server-configuration)
  - [4.3. Running with Docker](#43-running-with-docker)
- [5. Configuration Guide.](#5-configuration-guide)
  - [5.1. application.yml](#51-applicationyml)
    - [5.1.1.  Spring Basic Settings](#511-spring-basic-settings)
    - [5.1.2. Jackson Basic Settings](#512-jackson-basic-settings)
    - [5.1.3. Server Settings](#513-server-settings)
  - [5.2. application-logging.yml](#52-application-loggingyml)
    - [5.2.1. Logging Configuration](#521-logging-configuration)
  - [5.3. blockchain.properties](#53-blockchainproperties)
    - [5.3.1. Blockchain Integration Settings](#531-blockchain-integration-settings)
- [6. Profile Configuration and Usage](#6-profile-configuration-and-usage)
  - [6.1. Profile Overview (`sample`, `dev`)](#61-profile-overview-sample-dev)
    - [6.1.1. `sample` Profile](#611-sample-profile)
    - [6.1.2. `dev` Profile](#612-dev-profile)
  - [6.2. How to Configure Profiles](#62-how-to-configure-profiles)
    - [6.2.1. Running the Server Using an IDE](#621-running-the-server-using-an-ide)
    - [6.2.2. Running the Server Using Console Commands](#622-running-the-server-using-console-commands)
    - [6.2.3. Running the Server Using Docker](#623-running-the-server-using-docker)
- [7. Running After Building with Docker](#7-running-after-building-with-docker)
  - [7.1. How to Build a Docker Image (Based on `Dockerfile`)](#71-how-to-build-a-docker-image-based-on-dockerfile)
  - [7.2. Running the Docker Image](#72-running-the-docker-image)
  - [7.3. Running with Docker Compose](#73-running-with-docker-compose)
    - [7.3.1. `docker-compose.yml` File Explanation](#731-docker-composeyml-file-explanation)
    - [7.3.2. Running and Managing Containers](#732-running-and-managing-containers)
    - [7.3.3. How to Configure the Server](#733-how-to-configure-the-server)
    

# 1. Introduction

## 1.1. Overview

This document provides a guide for the installation and operation of the API Gateway server. It explains the API Gateway installation process, configuration methods, and operation procedures step-by-step, helping users install and manage the server efficiently.

For the complete installation guide of OpenDID, please refer to the [Open DID Installation Guide].

<br/>

## 1.2. API Gateway Server Definition

The API Gateway Server acts as an intermediary, allowing the app to retrieve specific data from the blockchain server.

<br/>

## 1.3. System Requirements
- **Java 21** or higher
- **Gradle 7.0** or higher
- **Docker** and **Docker Compose** (when using Docker)
- At least **2GB RAM** and **10GB of disk space**

<br/>

# 2. Preparation

This chapter provides the necessary preparatory steps before installing the components of the Open DID project.

## 2.1. Git Installation

`Git` is a distributed version control system that tracks changes in the source code and supports collaboration among multiple developers. Git is essential for managing the source code and version control of the Open DID project.

After a successful installation, you can check the version of Git with the following command:
```bash
git --version
```

> **Reference Links**
> - [Git Installation Guide](https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository)

<br/>


# 3. Cloning the Source Code from GitHub

## 3.1. Cloning the Source Code

The `git clone` command allows you to copy the source code from a remote repository hosted on GitHub to your local computer. By using this command, you can work on the entire source code and related files locally. After cloning, you can proceed with the necessary tasks within the repository and push any changes back to the remote repository.

Open the terminal and run the following commands to copy the API Gateway server repository to your local computer:

```bash
# Clone the repository from the Git repository
git clone https://github.com/OmniOneID/did-api-server.git

# Navigate to the cloned repository
cd did-api-server
```

> **Reference Links**
> - [Git Clone Guide](https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository)

<br/>

## 3.2. Directory Structure
The main directory structure of the cloned project is as follows:

```
did-api-server
├── CHANGELOG.md
├── CLA.md
├── CODE_OF_CONDUCT.md
├── CONTRIBUTING.md
├── dependencies-license.md
├── MAINTAINERS.md
├── README.md
├── RELEASE-PROCESS.md
├── SECURITY.md
├── docs
│   ├── api
│       └── APIGateway_Reference_Implementation_API.md
│   ├── errorCode
│       └── APIGateway_ErrorCode.md
│   └── installation
│       └── OpenDID_APIGatewayServer_InstallationAndOperation_Guide.md
└── source
    └── apigateway
        ├── gradle
        ├── libs
            └── did-sdk-common-2.0.0.jar
            └── did-blockchain-sdk-server-2.0.0.jar
            └── did-core-sdk-server-2.0.0.jar
            └── did-crypto-sdk-server-2.0.0.jar
            └── did-datamodel-server-2.0.0.jar
            └── did-wallet-sdk-server-2.0.0.jar
            └── did-zkp-sdk-server-2.0.0.jar
        └── src
        └── build.gradle
        └── README.md
```

| Name                    | Description                                    |
| ----------------------- | ---------------------------------------------- |
| CHANGELOG.md            | Changes by version for the project             |
| CODE_OF_CONDUCT.md      | Code of conduct for contributors               |
| CONTRIBUTING.md         | Contribution guidelines and procedures         |
| dependencies-license.md | License information for project dependencies   |
| MAINTAINERS.md          | Guidelines for project maintainers             |
| RELEASE-PROCESS.md      | Procedure for releasing a new version          |
| SECURITY.md             | Security policy and vulnerability reporting    |
| docs                    | Documentation                                  |
| ┖ api                   | API guide documents                            |
| ┖ errorCode             | Error codes and troubleshooting guide          |
| ┖ installation          | Installation and configuration guide           |
| source                  | Source code                                    |
| ┖ did-api-server        | API Gateway server source code and build files |
| ┖ gradle                | Gradle build configuration and scripts         |
| ┖ libs                  | External libraries and dependencies            |
| ┖ sample                | Sample files                                   |
| ┖ src                   | Main source code directory                     |
| ┖ build.gradle          | Gradle build configuration file                |
| ┖ README.md             | Source code overview and instructions          |
<br/>


# 4. How to Run the Server
This chapter explains three methods to run the server.

The project source is located under the `source` directory, and depending on the method, you need to load and configure the source from that directory.

1. **Using an IDE**: You can open the project in an Integrated Development Environment (IDE), set up the run configuration, and directly run the server. This method is useful when quickly testing code changes during development.

2. **Using console commands after building**: After building the project, you can run the server by executing the generated JAR file with a console command (`java -jar`). This method is mainly used when deploying or running the server in a production environment.

3. **Building with Docker**: You can build the server as a Docker image and run it as a Docker container. This method ensures consistency across environments and makes deployment and scaling easier.
   
## 4.1. Running with IntelliJ IDEA (Gradle Support)

IntelliJ IDEA is a widely used Integrated Development Environment (IDE) for Java development. It supports build tools like Gradle, making project setup and dependency management very convenient. Since the Open DID server is built using Gradle, you can easily configure and run the server within IntelliJ IDEA.

### 4.1.1. Installing and Setting Up IntelliJ IDEA
1. Install IntelliJ. (Refer to the link below for installation instructions)

> **Reference Links**
> - [Download IntelliJ IDEA](https://www.jetbrains.com/idea/download/)

### 4.1.2. Opening the Project in IntelliJ
- Launch IntelliJ and select `File -> New -> Project` from Existing Sources. When the file selection window appears, choose the 'source/did-api-server' folder from the repository cloned in [3.1. Cloning the Source Code](#31-cloning-the-source-code)
- When the project opens, the `build.gradle` file will be automatically recognized.
- Gradle will automatically download the necessary dependency files. Please wait until this process is completed.

### 4.1.3. Gradle Build
- In the Gradle tab in IntelliJ IDEA, run `Tasks -> build -> build`.
- Once the build is successfully completed, the project will be ready for execution.

### 4.1.4. Running the Server
- In the Gradle tab of IntelliJ IDEA, select `Tasks -> application -> bootRun` and run it.
- Gradle will automatically build and run the server.
- Check the console log for the message "Started [ApplicationName] in [time] seconds" to confirm that the server has started successfully.
- Once the server is running properly, open your browser and navigate to http://localhost:8093/swagger-ui/index.html to verify that the API documentation is displayed correctly through Swagger UI.

> **Note**
> - The API Gateway server is initially configured to use the `sample` profile.
> - When the sample profile is set, the server will run without required configurations (e.g., database). For more details, refer to [6. Profile Configuration and Usage](#6-profile-configuration-and-usage)


<br/>

### 4.1.5. Server Configuration
- The server settings must be modified according to the deployment environment to ensure stable operation. For example, you need to adjust configuration elements such as database connection information, port numbers, email integration details, etc., based on the specific environment.
- The server configuration files are located at `src/main/resource/config.`
- For detailed configuration instructions, please refer to [5. Configuration Guide.](#5-configuration-guide)

<br/>

## 4.2. Running the Server Using Console Commands

This section provides instructions on how to run the Open DID server using console commands. It explains the process of building the project with Gradle and running the server using the generated JAR file.

### 4.2.1. Gradle Build Command

- Build the source using `gradlew`:
  ```shell
    # Navigate to the source folder of the cloned repository
    cd source/did-api-server

    # Grant execute permission to the Gradle Wrapper
    chmod 755 ./gradlew

    # Clean build the project (delete previous build files and rebuild)
    ./gradlew clean build
  ```
  > Note
  > - `gradlew` is short for Gradle Wrapper, a script used to run Gradle in a project. Even if Gradle is not installed locally, it automatically downloads and runs the version of Gradle specified by the project, allowing developers to build the project in a consistent environment, regardless of whether Gradle is installed.

- Navigate to the build folder and confirm that the JAR file has been generated:
    ```shell
      cd build/libs
      ls
    ```
- This command generates the file `did-api-server-2.0.0.jar`

<br/>

### 4.2.2. Running the Server
Run the server using the built JAR file:

```bash
java -jar did-api-server-2.0.0.jar
```

> **Note**
> - The API Gateway server is initially configured to use the `sample` profile.
> - When the sample profile is set, the server will run without required configurations (e.g., database). For more details, refer to [6. Profile Configuration and Usage](#6-profile-configuration-and-usage)

<br/>

<br/>

### 4.2.4. Server Configuration
- The server settings must be modified according to the deployment environment to ensure stable operation. For example, you need to adjust configuration elements such as database connection information, port numbers, email integration details, etc., based on the specific environment.
- The server configuration files are located at `src/main/resource/config.`
- For detailed configuration instructions, please refer to [5. Configuration Guide.](#5-configuration-guide)

<br/>

## 4.3. Running with Docker
- For the process of building, configuring, and running a Docker image, please refer to [7. Running After Building with Docker](#7-running-after-building-with-docker)

<br/>

# 5. Configuration Guide.

This chapter provides guidance on each configuration value included in all of the server's configuration files. Each setting is a crucial element that controls the server's behavior and environment, so proper configuration is necessary for stable server operation. Refer to the descriptions and examples for each item to apply the appropriate settings for your environment.

Please note that settings with the 🔒 icon are fixed by default or generally do not require modification.

## 5.1. application.yml

- Role: The application.yml file defines the basic configuration for the Spring Boot application. Through this file, you can specify various environment variables such as the application's name, database settings, and profile configurations, all of which have a significant impact on the application's behavior.

- Location: `src/main/resources/config`

### 5.1.1. Spring Basic Settings
* `spring.application.name`: 
    - Specifies the name of the application.
    - Purpose: Primarily used for identifying the application in log messages, monitoring tools, or within Spring Cloud services.
    - Example: `ApiGateway`

* `spring.profiles.active`:  
  - Defines the active profile.
  - urpose: Selects either the sample or development environment and loads the corresponding settings. For more details on profiles, refer to [6. Profile Configuration and Usage](#6-profile-configuration-and-usage)
  - Supported profiles: sample, dev
  - Example: `sample`, `dev`

* `spring.profiles.group.dev`: 🔒
  - Defines individual profiles included in the `dev` profile group.
  - Purpose: Manages configurations for the development environment.
  - Profile file naming convention: The configuration files corresponding to each profile must use the exact name as defined in the group. For example, the auth profile is written as application-auth.yml, and the databases profile as application-databases.yml. The file names must match the names listed under group.dev.

* `spring.profiles.group.sample`: 🔒
  - Defines individual profiles included in the `sample` profile group.
  - Purpose: Manages configurations for the sample environment.
  - Profile file naming convention: The configuration files corresponding to each profile must use the exact name as defined in the group. For example, the auth profile is written as application-auth.yml, and the databases profile as application-databases.yml. The file names must match the names listed under group.dev.

<br/>

### 5.1.2. Jackson Basic Settings

Jackson is the default JSON serialization/deserialization library used in Spring Boot. By configuring Jackson, you can adjust the serialization format and methods for JSON data, which can improve performance and efficiency during data transmission.

* `spring.jackson.default-property-inclusion`: 🔒 
    - Configures the application to exclude properties from serialization when their value is null.
    - Example: non_null

* `spring.jackson.default-property-inclusion`: 🔒 
    - Prevents errors from being thrown when serializing empty objects.
    - Example: false

<br/>
      
### 5.1.3. Server Settings
The server settings define the port number on which the application will listen for requests.

* `server.port`:  
    - The port number on which the application will run. The default port for the ApiGateway server is 8093.
    - Example : 8093

<br/>

## 5.2. application-logging.yml
- Role: Configures log groups and log levels. This configuration file allows you to define log groups for specific packages or modules and specify log levels individually for each group.

- Location: `src/main/resources/config`

### 5.2.1. Logging Configuration
* `logging.level`: 
    - Specifies the log level.
    - By setting the level to debug, you will be able to see all log messages at the DEBUG level or higher (INFO, WARN, ERROR, FATAL) for the specified packages.

<br/>

## 5.3. blockchain.properties

- Role: Configures blockchain server information for integration with the API Gateway server. When you install the Hyperledger Besu network according to '5.3. Step 3: Blockchain Installation' in [Open DID Installation Guide], private keys, certificates, and server connection information configuration files are automatically generated. In blockchain.properties, you set the paths where these files are located and the network name entered during Hyperledger Besu installation.

- Location: `src/main/resources/properties`

### 5.3.1. Blockchain Integration Configuration

#### EVM Network Configuration

- `evm.network.url`:
  - EVM Network address. Use this fixed value when running Besu on the same local as the client. (Default Port: 8545)
  - Example: http://localhost:8545

- `evm.chainId`:
  - Chain ID identifier. Currently uses a fixed value of 1337. (Default Value: 1337)
  - Example: 1337

- `evm.gas.limit`:
  - Maximum gas limit allowed for Hyperledger Besu EVM transactions. Currently uses a fixed value as Free Gas. (Default Value: 100000000)
  - Example: 100000000

- `evm.gas.price`:
  - Gas price per unit. Currently uses a fixed value of 0 as Free Gas. (Default Value: 0)
  - Example: 0

- `evm.connection.timeout`: 
  - Network connection timeout value (milliseconds). Currently uses the recommended fixed value of 10000. (Default Value: 10000)
  - Example: 10000

#### EVM Contract Configuration

- `evm.connection.address`: 
  - Address value of the OpenDID Contract returned when deploying Smart Contract with Hardhat. For detailed guide, refer to [DID Besu Contract].
  - Example: 0xa0E49611FB410c00f425E83A4240e1681c51DDf4

- `evm.connection.privateKey`: 
  - k1 key used for API access control. Enter the key string defined in accounts inside hardhat.config.js (remove the 0x string at the beginning) to enable API calls with Owner privileges (Default setting). For detailed guide, refer to [DID Besu Contract].
  - Example: 0x8f2a55949038a9610f50fb23b5883af3b4ecb3c3bb792cbcefbd1542c692be63
<br/>


# 6. Profile Configuration and Usage

## 6.1. Profile Overview (`sample`, `dev`)
The API server supports two profiles, `dev` and `sample`, to allow operation in various environments.

Each profile is designed to apply the appropriate settings for its environment. By default, the API server is configured to use the `sample` profile, which is designed to run the server independently without connecting to external services such as a database or blockchain. The `sample` profile is ideal for testing API calls, allowing developers to quickly verify the basic functionality of the application. This profile returns fixed response data for all API calls, making it useful in early development environments.

Sample API calls are written as JUnit tests, which can be referenced when writing your own tests.

On the other hand, the `dev` profile is designed to perform actual operations. When using this profile, testing and validation with real data are possible. Activating the dev profile enables integration with external services such as databases and blockchain, allowing you to test the application's behavior in a real-world environment.


### 6.1.1. `sample` Profile
The `sample` profile is designed to run the server independently without connecting to external services (e.g., databases, blockchain). This profile is suitable for testing API calls, allowing developers to quickly verify the basic functionality of the application. It returns fixed response data for all API calls, making it useful in the early development stage or for functional testing. Since no integration with external systems is required, it provides an environment where the server can be run and tested standalone.

### 6.1.2. `dev` Profile
The `dev` profile includes settings suited for the development environment and is used on development servers. To use this profile, you will need configuration for the development environment's database and blockchain node.


## 6.2. How to Configure Profiles
This section explains how to switch profiles for each server operation method.

### 6.2.1. Running the Server Using an IDE
- **Select Configuration File:**  In the `src/main/resources path`, select the `application.yml` file.
- **Specify Profile:** In the IDE's Run/Debug Configurations, add the option `--spring.profiles.active={profile}` to activate the desired profile.
- **Apply Configuration:** The configuration file corresponding to the activated profile will be applied.

### 6.2.2. Running the Server Using Console Commands
- **Select Configuration File:** Prepare the profile-specific configuration files in the same directory as the built JAR file or in the directory where the configuration files are located.
- **Specify Profile:** Add the option `--spring.profiles.active={profile}` to the server startup command to activate the desired profile.
  
  ```bash
  java -jar build/libs/did-api-server-2.0.0.jar --spring.profiles.active={profile}
  ```

- **Apply Configuration:** The configuration file corresponding to the activated profile will be applied.

### 6.2.3. Running the Server Using Docker
- **Select Configuration File:** When creating a Docker image, specify the configuration file path in the Dockerfile, or mount external configuration files to the Docker container.
- **Specify Profile:** Set the `SPRING_PROFILES_ACTIVE` environment variable in the Docker Compose file or the Docker run command to specify the profile.
  
  ```yaml
  environment:
    - SPRING_PROFILES_ACTIVE={profile}
  ```

- **Apply Configuration:** The specified profile's configuration will be applied when the Docker container is executed.

By following these methods, you can flexibly switch profile-specific settings and easily apply the appropriate configuration for your project environment.

<br/>

# 7. Running After Building with Docker

## 7.1. How to Build a Docker Image (Based on `Dockerfile`)
Build the Docker image using the following command:

```bash
docker build -t did-api-server .
```

## 7.2. Running the Docker Image
Run the built image with the following command:

```bash
docker run -d -p 8093:8093 did-api-server
```

## 7.3. Running with Docker Compose

### 7.3.1. `docker-compose.yml` File Explanation
The `docker-compose.yml` file allows you to easily manage multiple containers.

```yaml
version: '3'
services:
  app:
    image: did-api-server
    ports:
      - "8093:8093"
    volumes:
      - ${your-config-dir}:/app/config
    environment:
      - SPRING_PROFILES_ACTIVE=sample
```

### 7.3.2. Running and Managing Containers
Run the container using Docker Compose with the following command:

```bash
docker-compose up -d
```

### 7.3.3. How to Configure the Server
In the example above, the `${your-config-dir}` directory is mounted to `/app/config` inside the container to share configuration files.

- If additional configuration is required, you can modify settings by adding separate property files to the mounted folder.
For example, add an application.yml file to `${your-config-dir}` and write the necessary configuration changes in this file.
The `application.yml` file located in `${your-config-dir}` will take precedence over the default configuration files.
- For detailed configuration instructions, please refer to [5. Configuration Guide.](#5-configuration-guide)

[Open DID Installation Guide]: https://github.com/OmniOneID/did-release/blob/main/release-V2.0.0.0/OpenDID_Installation_Guide-V2.0.0.0.md
[DID Besu Contract]: https://github.com/OmniOneID/did-besu-contract