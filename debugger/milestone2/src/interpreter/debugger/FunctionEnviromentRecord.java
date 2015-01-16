
package interpreter.debugger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.StringTokenizer;



public class FunctionEnviromentRecord {
    private SymbolTable symTable = new SymbolTable();
    private int startline = -1;
    private int endline = -1;
    private int currentline = -1;
    private String name;
    
    
    public static void main(String args[]) throws IOException{
        //create an instance of FER
        FunctionEnviromentRecord fctEnvRecord = new FunctionEnviromentRecord();
        
        //set file reader
        String filename = args[0];
        BufferedReader source ;
        try{
            source = new BufferedReader(new FileReader(filename));
        }catch(Exception e){
            System.out.println(e.toString());
            return;
        }
        
        //read each line
        String line;
        while((line = source.readLine()) != null){
            //tokenize the line
            StringTokenizer st = new StringTokenizer(line);
            String command = st.nextToken();
            //run commands
            if(command.equals("BS")){
                fctEnvRecord.getSymbolTable().beginScope();
            }else if(command.equals("Function")){
                fctEnvRecord.setName(st.nextToken());
                fctEnvRecord.setStartline(Integer.parseInt(st.nextToken()));
                fctEnvRecord.setEndline(Integer.parseInt(st.nextToken()));
            }else if(command.equals("Line")){
                fctEnvRecord.setCurrentline(Integer.parseInt(st.nextToken()));
            }else if(command.equals("Enter")){
                fctEnvRecord.pushSymbolTable(st.nextToken(), Integer.parseInt(st.nextToken()));
            }else if(command.equals("Pop")){
                fctEnvRecord.popSymbolTable(Integer.parseInt(st.nextToken()));
            }else{
                System.out.println("Error:Invalid command");
                return;
            }//end if else
            
            System.out.println(fctEnvRecord.DumpFER());
        }//end while
        
    }//end main
    
    
    public String getName(){
        return name;
    }
    
    public void setName(String newName){
        name = newName;
    }
    
    public int getStartline(){
        return startline;
    }
    
    public void setStartline(int newStartline){
        startline = newStartline;
    }
    
    public int getEndline(){
        return endline;
    }
    
    public void setEndline(int newEndline){
        endline = newEndline;
    }
    
    public int getCurrentline(){
        return currentline;
    }
   
    public void setCurrentline(int newCurrentline){
        currentline = newCurrentline;
    }
    
    public SymbolTable getSymbolTable(){
        return symTable;
    }
    
    
    public void popSymbolTable(int n){
        for(int i = 0; i < n; i++){
            symTable.endScope();
        }
    }

    public void pushSymbolTable(String a, int n){
        symTable.beginScope();
        symTable.put(a, n);
    }
    
    public String DumpFER(){
        String str = "(<";
        str += symTable.toString();
        str += ">,";
        if(name == null){
            str += "-,";
        }else{
            str += (name + ",");
        }
        if(startline == -1){
            str += "-,";
        }else{
            str += (startline + ",");
        }
        if(endline == -1){
            str += "-,";
        }else{
            str += (endline + ",");
        }
        if(currentline == -1){
            str += "-)";
        }else{
            str += (currentline + ")");
        }
        return str;
    }
    
    
}