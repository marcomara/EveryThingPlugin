package it.commands.Warp;

import java.util.ArrayList;
import java.util.List;

public class HomePoint {
    public double x,y,z;
    public float p,yaw;
    public String w;
    public HomePoint(double x, double y, double z, String UUID, float p, float yaw){
        this.x=x;
        this.y=y;
        this.z=z;
        this.w= UUID;
        this.p=p;
        this.yaw=yaw;
    }
    public List<String> serialize(){
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(x));
        list.add(String.valueOf(y));
        list.add(String.valueOf(z));
        list.add(w);
        list.add(String.valueOf(p));
        list.add(String.valueOf(yaw));
        return list;
    }
    public static HomePoint deserialize(List<String> in){
        return new HomePoint(Double.parseDouble(in.get(0)),Double.parseDouble(in.get(1)),Double.parseDouble(in.get(2)),in.get(3),Float.parseFloat(in.get(4)),Float.parseFloat(in.get(5)));
    }
}
