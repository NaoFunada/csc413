
package interpreter;

import java.io.*;
import java.util.*;
import interpreter.bytecode.*;

public class ByteCodeLoader extends Object{
    BufferedReader source;
    String line;
    ArrayList bytecodelist;
    
    /**
     * Creates instances of ByteCode classes and passes variables to args
     * @param programFile
     * @throws IOException 
     */
    public ByteCodeLoader(String programFile)throws IOException{
        source = new BufferedReader(new FileReader(programFile));
        bytecodelist = new ArrayList();
        
        //read programFile by each line
        while ((line = source.readLine()) != null) {
            //tokenize the line
            StringTokenizer st = new StringTokenizer(line);
            String code = st.nextToken();
            String codeClass = CodeTable.get(code);
            
            //store arguments to pass in args
            String[] args = new String[2];
            for (int i = 0; st.hasMoreTokens(); i++) {
                args[i] = st.nextToken();
            }
                
            try{//make an instance of a class
                ByteCode bytecode;
                bytecode = (ByteCode)(Class.forName("interpreter.bytecode."+codeClass).newInstance());
                

                //pass args to init()
                bytecode.init(args);
                
                //store bytecode instances in arraylist
                bytecodelist.add(bytecode);
                
            }catch(Exception e){
                System.out.println("**** " + e);
            }
            
            
        }//end while
        
        
    }
    
    /**
     * Load all codes from file and request Program to resolve branch addresses
     * @return 
     */
    public Program loadCodes(){
        Program prog = new Program(bytecodelist);
        prog.resolveAddress();
        
        return prog;
    }
    
}
