package com.verification.components;

import com.verification.ConfictedImplicationException;
import com.verification.global;
import com.verification.wire;

import java.util.ArrayList;

public abstract class component {
    public int inputs, outputs;
    public Integer [] input_wires, output_wires;
    public Integer hashID;

    /**
     * Propogates controllability
     * calculates output based on current assigned values on input nets
     * no output, the value is set to the wires
     */
    public abstract void propogate_controllability();
    protected boolean d_frontier = false;


    //Implies the output with current input wires and returns the list of implied outputs

    /**
     * Implies the current input net values to output nets
     * @return a arraylist of wires which were implied
     * @throws ConfictedImplicationException
     */
    public abstract ArrayList<wire> imply() throws ConfictedImplicationException;

    //Returns true if xpath exists
    public Integer x_path_check(){
        if(this.getClass().getSimpleName().equals("PI"))
            return hashID;
        ArrayList<wire> outs = imply();
        for (wire out:outs) {
            if(out.assignment == global.FvLogic.X){
                ((component)global.all_components.get(out.outputgate_id)).x_path_check();
            }
        }
        return false;
    }
}
