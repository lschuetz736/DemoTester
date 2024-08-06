# WebForJDemoTester

## What does this project do?

This is a tool to test the demos for WebForJ. It navigates into the project which contains the demo files and automatically starts each demo by opening it in the Browser. It then either takes a screenshot of every site when given the argument "takeScreenshots" or compares every saved screenshot to the site and declares wether it was successfull (if the screenshot matches the site) or not (when it does not) and saves the results in the data.txt when given the argument "compareScreenshots".  

### Getting started

1. Download this project and place it at any location of your folder system
2. Edit the paths in the "DemoTester.java"  file so they match the paths of the project which contains the demos you want to test and edit the pom String if needed (more detailed instructions in the java file)
3. Make sure the preparations for the project which contains the demos are done (Docker, Maven, ...)
4. Open a terminal and navigate to the project
5. After that the following commands need to be executed in this particular order:
    - mvn install
    - mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install --with-deps"
6. Start the program with this command: mvn compile exec:java -D exec.mainClass="org.tester.DemoTester" -D exec.args="your arguments"<br>
    Arguments: takeScreenshots + time<br>
    Or:        compareScreenshots + time<br>
    (time = Time the Program should wait between opening a site in the browser and taking a screenshot in ms. Adding a time is optional. When not given a time it sets the time to 10000 ms (10 seconds) by default)
