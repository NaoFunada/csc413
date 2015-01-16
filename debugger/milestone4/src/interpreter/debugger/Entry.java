
package interpreter.debugger;

public class Entry{
    private String sourceline;
    private Boolean isBreakptSet;
    
    Entry(String line){
        sourceline = line;
        isBreakptSet = false;
    }
    
    public void setBpt(Boolean isBptSet){
        isBreakptSet = isBptSet;
    }
    
    public Boolean isBptSet(){
        return isBreakptSet;
    }
    
    
    public void setSourceline(String line){
        sourceline = line;
    }
    
    @Override
    public String toString(){
        return sourceline;
    }
}//end entry