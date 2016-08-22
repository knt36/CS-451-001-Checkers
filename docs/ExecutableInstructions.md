#Instructions how to create the executable

####These Directions Were Tested on Intellij IDEA 2016.2.2 Community Edition.
    
If you don't want to install a new IDE just to build the project, that's okay! Building this project is basic, and can be done with any IDE. (However, Intellij is quite nice. You might like it.)
1. Build the artifact; select JAR. 
2. Your IDE should pick up on the project. Select "Module with Dependencies" or equivalent.
3a. Set the main class as `main.ClientMain` and extract the Library JAR files into the target JAR.
3b. Alternately, you can use our manifest file. Be sure to extract the Library JAR files if it is not done automatically.
4. Ensure that the following are listed as resources in your JAR:
- GSON Library
- MySql Connection Library
- Compiled Code
If all of these are not present, **the JAR will NOT function properly.**
5. Build the JAR. 
6. Copy the `client.jks` file from the project root and place it in the same directory as your JAR. **Do not move or rename this file, as it will lead to a connection error.**
7. Run the JAR!

####I don't want to build it.
That's okay, we did it for you. Just unzip `Checkers.zip` and you'll be ready to run.

#Instructions how to install the executable
1. Unzip `Checkers.zip`.
2. Open the folder. There should be a JAR and a .jks file.
3. Run the JAR.
- _If you have the .jar file association set as Java:_ double click the JAR.
- _If you prefer the command line:_ Execute `java -jar <name_of_jar_file>`

**NOTE:** If you move, rename, or delete the `client.jks` file, you will not be able to connect to the server! Do not move or rename this file!

