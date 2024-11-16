// Uses Node.js File system module
import fileSys from "fs";

let result;
const FILE = "equation.txt";

try {
     // Reads txt file and evaluates the equation
     let data = fileSys.readFileSync(FILE, "utf-8");
     result = eval(data);
}
catch (error) {
     // Gives error if it's invalid
     result = "Error";
}

result = result.toString();

// Overwrites the result back into the file
fileSys.writeFileSync(FILE, result, "utf-8");

