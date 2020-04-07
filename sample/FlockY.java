package sample;

import flockbase.*;
import java.util.*;
import java.lang.*;
import java.io.*;

public class FlockY extends Flock{

    public FlockY(){
        super();
    }

    private ArrayList<Bird> flock=new ArrayList<Bird>();
    private Bird leader;

    @Override
    public void addBird(Bird b) {
        flock.add(b);
        b.setFlock(this);
    }

    @Override
    public Bird getLeader() {
        return leader;
    }

    @Override
    public void setLeader(Bird leader) {
        if(leader!=null){
            leader.retireLead();
        }
        this.leader=leader;
        leader.becomeLeader();
    }

    @Override
    public ArrayList<Bird> getBirds() {
        return flock;
    }
    
    @Override
    public void joinFlock(Flock f) {
        getLeader().retireLead();
        for(Bird b:this.getBirds()){
            f.addBird(b);
            flock.remove(b);
        }
    }

    @Override
    public Flock split(int pos) {
        Flock newFlock=new FlockY();
        Bird nextlead= flock.get(pos);
        nextlead.becomeLeader();
        newFlock.addBird(nextlead);
        newFlock.setLeader(nextlead);
        nextlead.setTarget(10,500); //Hard coded
        flock.remove(pos);

        int l=flock.size();
        for(int i=0;i<pos;i++){
                newFlock.addBird(flock.get(i));
                flock.get(i).setFlock(newFlock);
        }

        for(int i=0;i<pos-1;i++){
            flock.remove(i);
        }   
        
        return newFlock;
    }
}