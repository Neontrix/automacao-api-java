# BackEnd Tests PSekBase

Example of usage Java 17 with Cucumber and allure report
## Installing Java JDK 17
Access the Java SE Downloads page and download the installer for your version of Windows.
### Step 1: Download JDK 17 Installer
Access the Java SE Downloads page and download the installer for your version of Windows.

### Step 2: Verify the Download
Ensure the successful completion of file download by comparing the file size or checksum with the one provided on the download page.

### Step 3: Install JDK
You must have administrator privilege to install the JDK on Microsoft Windows. Start the JDK 17 installer and follow the instructions provided by the installer.

## Configuring Java Environment Variables

### Step 1: Check if Java is Installed
First, you need to check if Java is installed on your system. You can do this by typing `java -version` in your command prompt.

### Step 2: Locate the Java Installation Directory
Find the directory where Java is installed. The typical installation directory is something like `C:\\Program Files\\Java\\jdk1.8.0_131`.

### Step 3: Open the System Properties Dialog
Right-click on 'Computer', click on 'Properties', then select 'Advanced System Settings' from the left pane.

### Step 4: Edit the Environment Variables
Click on 'Environment Variables'. Under the 'System Variables' section, click on 'New' for single user setting.

### Step 5: Set JAVA_HOME
Set 'JAVA_HOME' as the Variable name, and the path to the JDK installation as the Variable value (e.g., 'C:\\Program Files\\Java\\jdk1.8.0_131'). Note that it should not include `\\bin`.

### Step 6: Edit the PATH Variable
Select 'PATH' under the list of 'System Variables', click 'Edit', then click 'New' and add `%JAVA_HOME%\\bin` after a semicolon.

### Step 7: Apply the Changes
Click 'OK' to apply the changes. You may need to restart your command prompt for the changes to take effect.

**Note:** Make sure that the PATH does not contain any other references to another Java installation folder[^1^][1].

## Installing and Configuring Maven on Windows

### Installation Steps

1. **Download Maven**: Download the latest version of Maven from the official website.

2. **Extract the Archive**: Extract the downloaded archive to a directory of your choice. For example, `C:\\Program Files\\Apache\\maven`.

3. **Set Environment Variables**: You need to set the `MAVEN_HOME` and `PATH` environment variables.

    - **M2_HOME and MAVEN_HOME**: These variables point to the home directory of Maven. To set these variables, follow these steps:
        - Right-click on 'My Computer' and select 'Properties'.
        - Click on 'Advanced system settings'.
        - Click on 'Environment Variables'.
        - Click on 'New' under System variables.
        - Enter `MAVEN_HOME` as the variable name and the path to the Maven directory (e.g., `C:\\Program Files\\Apache\\maven`) as the variable value.

    - **PATH**: This variable is used by the system to find executables. You need to add the bin directory of Maven to the PATH variable. To do this, append `%MAVEN_HOME%\\bin` to the PATH variable.

4. **Verify the Installation**: Open a new command prompt and type `mvn -version`. If Maven is correctly installed, you should see information about the Maven version, the Java version and the operating system.

## Troubleshooting
If you encounter any issues, make sure that:
- The JDK is correctly installed and the `JAVA_HOME` environment variable is correctly set.
- The Maven archive is correctly extracted and the `MAVEN_HOME` and `PATH` variables are correctly set.

## Install and configure allure report

### Step 1: Install Node.js

1. **Download Node.js Installer**

   Visit the official Node.js website at `https://nodejs.org/`. Download the installer appropriate for your operating system.

2. **Run the Installer**

   Run the downloaded installer, which will guide you through the process of installing Node.js.

3. **Verify the Installation**

   Open a terminal or command prompt and type the following command:

    ```bash
    node -v
    ```

   This should display the installed version of Node.js.

### Step 2: Install Allure Report

1. **Install Allure Command Line**

   With Node.js installed, you can now install Allure Report. Open a terminal or command prompt and type the following command:

    ```bash
    npm install -g allure-commandline
    ```

   This command installs Allure globally on your system.

2. **Verify the Installation**

   Verify that Allure is installed correctly by running:

    ```bash
    allure --version
    ```

   This should display the installed version of Allure.

# Running project

This guide will help you run your Cucumber 7 tests with Maven and Java. The runner file is located at `src/test/java/br/com/sek/runner/CucumberTest.java`.

### Steps to Run the Project

Follow these steps to run the project:

1. **Install Project Dependencies**

   Run the following command to clean the project and install dependencies:

    ```bash 
    mvn clean install
    ```
Pay atention in proxy configuration. To set proxy can be setted in %MAVEN_HOME%/conf/settings.xml

2. **Run All Tests**

   Use the following command to run all tests:

    ```bash 
    mvn test
    ```

3. **Generate Allure Report**

   Navigate to the target directory and serve the Allure report:

```bash 
    cd target
    allure serve
```
if allure not running, you can execute this command and try to run again

```bash 
    Set-ExecutionPolicy RemoteSigned
```

### Running New Tests

If you want to clean old results and run new tests, use the following command:

```bash 
mvn clean test
```

### Running Specific Tests
To run tests with a specific tag, use the -D"cucumber.filter.tags=@tag" option. Replace @tag with your desired tag. For example, to run tests with the @regression tag, use:

```bash 
   mvn test -D"cucumber.filter.tags=@regression"
```