
package interpreter;

import java.util.*;

public class RunTimeStack {
    /**
     * frame segments of stack
     */
    private Stack framePointers;
    /**
     * data stored in stack
     */
    private Vector runStack;
    
    //add initial value 0 to framePointers
    public RunTimeStack(){
        framePointers = new Stack();
        runStack = new Vector();
        framePointers.add(0);
        
        
    }
    
    
    /**
     * The dumper
     * @return  void
     */
    public void dump(){
        //print contents of runStack separated by frames
        for(int j =0; j < framePointers.size() - 1; j++){
            System.out.print("[");
            for(int i = (Integer)framePointers.get(j); 
                    i < (Integer)framePointers.get(j + 1) - 1; i++){
                System.out.print(runStack.get(i) + ",");
            }
            System.out.print(runStack.get((Integer)framePointers.get(j + 1) - 1) + "]");
        }
        System.out.print("[");
        for(int i = (Integer)framePointers.lastElement();
                i < runStack.size() - 1; i++){
                System.out.print(runStack.get(i) + ",");
            }
            System.out.print(runStack.lastElement() + "]" + "\n");
    }
    
    public int peek(){
        return (Integer)runStack.lastElement();
    }
    
    public int pop(){
        return (Integer)runStack.remove(runStack.size()-1);
    }
    
    public int push(int i){
        runStack.add(i);
        return i;
    }

    public void newFrameAt(int offset){
        if(runStack.size() - offset != framePointers.lastElement()){
            framePointers.add(runStack.size() - offset);
        }
    }
    
    public void popFrame(){
        //save top value
        int returnVar = (Integer)runStack.lastElement();
        //pop frame
        runStack.subList((Integer)framePointers.lastElement(), runStack.size()).clear();
        if((Integer)framePointers.peek() != 0){
            framePointers.pop();
        }
        runStack.add(returnVar);
    }
    
    public int store(int offset){
        int i = (Integer)framePointers.peek(); 
        int j = pop(); 
        runStack.setElementAt(j ,i + offset);
        
        return j; 
    }
    
    public int load(int offset){ 
        int i = (Integer)framePointers.peek(); 
        int j = (Integer)runStack.elementAt(i + offset); 
        runStack.add(j); 
        return j;
    }
    
    public Integer push(Integer offset){
        runStack.add(new Integer(offset)); 
        return offset; 
    }
    
    public List topStack(){
        List top = runStack.subList((Integer)framePointers.lastElement(), runStack.size());
        return top;
    }
    
    
    
}
