package sample;

import flockbase.*;
import java.util.*;
import java.lang.*;
import java.io.*;

public class BirdX extends Bird{

    private double speed=20.0;
    private boolean lead=false;

    @Override
    protected Position getTarget() {
        return super.getTarget();
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    @Override
    public String getName() {
        
        if(lead){
            return "L";
        }
        return "017";
    }
    
    @Override
    public void becomeLeader() {
        lead=true;
    }

    @Override
    public void retireLead() {
        lead=false;
    }

    @Override
    protected void updatePos() {

        Position currenPosition=this.getPos();
        int curr_x=currenPosition.getX();
        int curr_y=currenPosition.getY();
        if(!this.lead){
            Position leaderPosition=this.getFlock().getLeader().getPos();
            setTarget(leaderPosition.getX(), leaderPosition.getY());
        }
        
        double dx=0.0;
        double dy=0.0;
        
        int target_x=this.getTarget().getX();
        int target_y=this.getTarget().getY();

        double distance=Math.sqrt(Math.pow((target_x-curr_x), 2))+ Math.pow((target_y-curr_y), 2);

        if(distance<15.0)//bird gets close to the target
        {
            dx=0.0;
            dy=0.0;
        }
        else if(target_x==curr_x){ //Vertical movement only slope=infinity
  
            if(target_y>curr_y){
                dy=1.0;
            }
            else{
                dy=-1.0;
            }
        }
        else if(target_y==curr_y){//Horizontal movement only slope=0

            if(target_x>curr_y){
                dx=1.0;
            }
            else{
                dx=-1.0;
            }
        }

        else{
            double sx=(double)(target_y-curr_y)/(target_x-curr_x);
            double sy=(double)(target_x-curr_x)/(target_y-curr_y); //used if sx too big i.e slope is high
            if(target_x>curr_x){
                dx=1.0;
            }
            else{
                dx=-1.0;
            }
            
            if(target_y>curr_y){
                dy=1.0;
            }
            else{
                dy=-1.0;
            }

            if(sx>=3 || sx<=-3){
                dy*=this.getSpeed();
                dx=sy*dy;
            }
            else{
                dx*=this.getSpeed();
                dy=sx*dx;
            }
        }


        int Dx = curr_x + (int)dx + checkDanger().getX();
        int Dy = curr_y + (int)dy + checkDanger().getY();
        Flock f = this.getFlock();
        ArrayList<flockbase.Bird> birds = f.getBirds();
        for(Bird b : birds)
        {
            int xtemp = b.getPos().getX();
            int ytemp = b.getPos().getY();
            if(xtemp - Dx > -15.0 && xtemp - Dx < 15.0)
            {
                if(ytemp - Dy > -15.0 && ytemp - Dy < 15.0)
                {
                    if(this.lead)
                        b.setPos(xtemp + 20, ytemp + 20);
                    else
                    {
                        dx = 0; dy = 0;
                    }
                }
            }          
        }

        Dx = curr_x + (int)dx + checkDanger().getX();
        Dy = curr_y + (int)dy + checkDanger().getY();
        
        this.setPos(Dx, Dy);


    }

    protected Position checkDanger(){
        Flock flock=this.getFlock();

        int x=0,y=0;
        for(Bird bird:flock.getBirds()){
            if(bird!=this){
                Position pos=bird.getPos();
                Position this_pos=this.getPos();
                double distance=Math.sqrt(Math.pow(pos.getX()-this_pos.getX(),2)+ Math.pow(pos.getY()-this_pos.getY(),2));
                
                if(distance<20){
                    x-=(pos.getX()-this_pos.getX());
                    y-=(pos.getY()-this_pos.getY());
                }
            }
        }

        Position updatePos=new Position(x, y);
        return updatePos;
    }
}