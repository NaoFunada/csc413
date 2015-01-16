package interpreter;

import interpreter.debugger.Debugger;
import java.io.*;

/**
 * <pre>
 * 
 *  
 *   
 *     Interpreter class runs the interpreter:
 *     1. Perform all initializations
 *     2. Load the bytecodes from file
 *     3. Run the virtual machine
 *     
 *   
 *  
 * </pre>
 */
public class Interpreter {

	protected ByteCodeLoader bcl;

	public Interpreter(String codeFile, boolean debugMode) {
		try {
                    CodeTable.init();
                    if(debugMode){
                        CodeTable.debugInit();
                    }                            
                       
                    bcl = new ByteCodeLoader(codeFile);
		} catch (IOException e) {
			System.out.println("**** " + e);
		}
	}
        
        
        
	void run() {
		Program program = bcl.loadCodes();
		VirtualMachine vm = new VirtualMachine(program);
		vm.executeProgram();
	}

	public static void main(String args[]) {
		if (args.length == 0) {
			System.out.println("***Incorrect usage, try: java interpreter.Interpreter <file>");
			System.exit(1);
		}
                
                //branch for debugger
                if ("-d".equals(args[0])){
                    //code for debugger mode
                    (new Debugger(args[1])).run();
                    
                }else{
                    (new Interpreter(args[0], false)).run();
                }
	}
}