NOTE: This project requires Node.js and knowledge of Java-Script

The GUi Calculator uses Java along with Java-Script. Since Java doesn't have it's own `eval()` function, it executes a Java-Script file that using Node.js. It executes that file using Java's Process & ProcessBuilder
classes.

First, when the `=` button is pressed in the calculator window, Java writes the text of the output label into a .txt file, after this, an js file is executed that reades the content of that .txt file, evaluates it using the `eval()` function and writes it back into the .txt file, all of this using Node.js.

And finally, java again reads the evaluated content of that .txt file and outputs it in the result label, all of this is done using threads.