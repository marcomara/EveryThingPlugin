package it.utils;
import org.jetbrains.annotations.Nullable;

public class Colors {
    public static String BLACK="\u00A70";
    public static String DARKBLUE="\u00A71";

    public static String DARKGREEN="\u00A72";

    public static String DARKAQUA="\u00A73";

    public static String DARKRED="\u00A74";

    public static String DARKPURPLE="\u00A75";

    public static String GOLD="\u00A76";

    public static String GRAY="\u00A77";

    public static String DARKGRAY="\u00A78";

    public static String BLUE="\u00A79";

    public static String GREEN="\u00A7a";

    public static String AQUA="\u00A7b";

    public static String RED="\u00A7c";

    public static String LIGHTPURPLE="\u00A7d";

    public static String YELLOW="\u00A7e";

    public static String WHITE="\u00A7f";

    public static String OBFUSCATED="\u00A7k";

    public static String BOLD="\u00A7l";

    public static String STRIKETHROUGH="\u00A7m";

    public static String UNDERLINE="\u00A7n";

    public static String ITALIC="\u00A7o";

    public static String RESET="\u00A7r";

    public static String AlternateColorCodes(@Nullable Character c, String str){
        String[] stra = str.split("\s");
        StringBuilder f = new StringBuilder();
        for(String s : stra){
            char ch = s.charAt(0);
            if(c==null){
                if(ch=='&'){
                    f.append(MYColor.fromChar(s) + "\s");
                    continue;
                }
                if(ch=='#'){
                    f.append(MYColor.fromHex(s)+ "\s");
                    continue;
                }
                f.append(s);
                continue;
            }
            if(ch==c&&c=='&'){
                f.append(MYColor.fromChar(s)+ "\s");
                continue;
            }
            if(ch==c&&c=='#'){
                f.append(MYColor.fromHex(s)+ "\s");
                continue;
            }
            f.append(s+ "\s");
        }
        return f.toString();
    }
    public static String StripColor(String str){
        String[] stra = str.split("\s");
        StringBuilder f = new StringBuilder();
        for(String s :stra){
            char a = s.charAt(0);
            if(a=='&'){
                f.append(s.substring(2)+ "\s");
                continue;
            }
            if(a=='#'){
                f.append(s.substring(7)+ "\s");
                continue;
            }
            f.append(s+ "\s");
        }
        return f.toString();
    }
    public static String getColorCode(String str){
        char a = str.charAt(0);
        if(a=='&'){
            return str.substring(0,2);
        }
        if(a=='#'){
            return str.substring(0,6);
        }
        return null;
    }
}
