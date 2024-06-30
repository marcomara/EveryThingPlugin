package it.commands.tpa;

public class Data {
    public int time;
    public String target;
    public String sender;

    public Data(int time, String target, String sender){
        this.time=time;
        this.target=target;
        this.sender=sender;
    }
    public int getTime(){
        return time;
    }
    public String getTarget(){
        return target;
    }
    public String getSender(){
        return sender;
    }
}
