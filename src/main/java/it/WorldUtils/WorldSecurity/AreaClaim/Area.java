package it.WorldUtils.WorldSecurity.AreaClaim;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Area {
    static int[] s = new int[3], e = new int[3];
    static List<String> owners;
    static List<String> permitted;
    static List<String> visitors;
    static boolean allowGreefing;
    static String name;
    public Area(int[] start, int[] end, String owner, boolean allowGreaf, String name){
        new Area(start,end,List.of(owner), List.of(owner),null, allowGreaf, name);
    }
    public Area(int[] start, int[] end, List<String> owners, @Nullable List<String> permitted,@Nullable List<String> visitors, boolean allowGreaf, String name){
        this.name=name;
        if (permitted==null){
            this.permitted=new ArrayList<>();
        }else {
            this.permitted=permitted;
        }
        if (visitors==null){
            this.visitors=new ArrayList<>();
        }else{
            this.visitors = visitors;
        }
        this.owners=owners;
        this.e=end;
        this.s=start;
        this.allowGreefing =allowGreaf;
    }
    public void addOwner(String name){
        this.owners.add(name);
    }
    public void addPermitted(String name){
        this.permitted.add(name);
    }
    public void addVisitor(String name){
        this.visitors.add(name);
    }
    public int[] getS(){
        return this.s;
    }

    public int[] getE() {
        return this.e;
    }
    public List<String> getPermitted(){
        return this.permitted;
    }
    public List<String> getVisitors(){
        return this.visitors;
    }
    public boolean allowsGreefing(){
        return allowGreefing;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder("{\n");
        sb.append("start:[").append(s[0]).append(",").append(s[1]).append(",").append(s[2]).append("]\n");
        sb.append("end:[").append(e[0]).append(",").append(e[1]).append(",").append(e[2]).append("]\n");
        sb.append("owners:[");
        for (String s : owners){
            if (owners.indexOf(s)!= owners.size()-1) sb.append(s).append(",");
            else sb.append(s);
        }
        sb.append("]\n");
        sb.append("permitted:[");
        for (String s : permitted){
            if (permitted.indexOf(s)!= permitted.size()-1) sb.append(s).append(",");
            else sb.append(s);
        }
        sb.append("]\n");
        sb.append("visitors:[");
        for (String s : visitors){
            if (visitors.indexOf(s)!= visitors.size()-1) sb.append(s).append(",");
            else sb.append(s);
        }
        sb.append("]\n");
        sb.append("allowGreefing:");
        if (allowGreefing){
            sb.append("true");
        }else sb.append("false");
        sb.append("\nname:").append(name);
        sb.append("\n}");
        return sb.toString();
    }
    public String getName(){
        return this.name;
    }
    public static Area fromString(String s){
        String[] sa = s.split("\n");
        String name = "";
        int[] start = new int[3],end = new int[3];
        List<String> owners = new ArrayList<>(), permitted = new ArrayList<>(), visitors =new ArrayList<>();
        boolean aG = true;
        for (String st : sa){
            if (st.equals("{")||st.equals("}")) continue;
            if (st.startsWith("start:[")){
                String u = st.substring(7,st.length()-1);
                String[] uu = u.split(",");
                for (int i = 0; i<3; i++){
                    start[i]=Integer.parseInt(uu[i]);
                }
            }
            if (st.startsWith("end:[")){
                String u = st.substring(5,st.length()-1);
                String[] uu = u.split(",");
                for (int i = 0; i<3; i++){
                    end[i]=Integer.parseInt(uu[i]);
                }
            }
            if (st.startsWith("owners:[")){
                String u = st.substring(8,st.length()-1);
                String[] uu = u.split(",");
                owners.addAll(List.of(uu));
            }
            if (st.startsWith("permitted:[")){
                String u = st.substring(11,st.length()-1);
                String[] uu = u.split(",");
                permitted.addAll(List.of(uu));
            }
            if (st.startsWith("visitors[")){
                String u = st.substring(9,st.length()-1);
                String[] uu = u.split(",");
                visitors.addAll(List.of(uu));
            }
            if (st.startsWith("allowGreefing:")){
                aG= st.substring(14).equals("true");
            }
            if (st.startsWith("name:")){
                name=st.substring(5);
            }
        }
        return new Area(start, end, owners, permitted, visitors, aG, name);
    }
}
