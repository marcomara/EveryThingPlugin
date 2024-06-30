package it.utils;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.Nullable;

public class MYColor{
    private final String mc_color_name;
    private final char mc_color_char;
    private final String HexCode;
    private final int[] RGBCode;
    private final String MC_Color_Code;
    @Nullable
    private NamedTextColor ntc = null;
    @Nullable
    private TextDecoration tdc = null;

    public MYColor(String code) {
        this.MC_Color_Code = code;
        this.mc_color_char = this.MC_Color_Code.toLowerCase().charAt(this.MC_Color_Code.length() - 1);
        switch (this.mc_color_char) {
            case '0':
                this.HexCode = "#000000";
                this.RGBCode = RGBValue(this.HexCode);
                this.mc_color_name = "BLACK";
                this.ntc = NamedTextColor.BLACK;
                break;
            case '1':
                this.HexCode = "#0000AA";
                this.RGBCode = RGBValue(this.HexCode);
                this.mc_color_name = "DARKBLUE";
                this.ntc = NamedTextColor.DARK_BLUE;
                break;
            case '2':
                this.HexCode = "#00AA00";
                this.RGBCode = RGBValue(this.HexCode);
                this.mc_color_name = "DARKGREEN";
                this.ntc = NamedTextColor.DARK_GREEN;
                break;
            case '3':
                this.HexCode = "#00AAAA";
                this.RGBCode = RGBValue(this.HexCode);
                this.mc_color_name = "DARKAQUA";
                this.ntc = NamedTextColor.DARK_AQUA;
                break;
            case '4':
                this.HexCode = "#AA0000";
                this.RGBCode = RGBValue(this.HexCode);
                this.mc_color_name = "DARKRED";
                this.ntc = NamedTextColor.DARK_RED;
                break;
            case '5':
                this.HexCode = "#AA00AA";
                this.RGBCode = RGBValue(this.HexCode);
                this.mc_color_name = "DARKPURPLE";
                this.ntc = NamedTextColor.DARK_PURPLE;
                break;
            case '6':
                this.HexCode = "#FFAA00";
                this.RGBCode = RGBValue(this.HexCode);
                this.mc_color_name = "GOLD";
                this.ntc = NamedTextColor.GOLD;
                break;
            case '7':
                this.HexCode = "#AAAAAA";
                this.RGBCode = RGBValue(this.HexCode);
                this.mc_color_name = "GRAY";
                this.ntc = NamedTextColor.GRAY;
                break;
            case '8':
                this.HexCode = "#555555";
                this.RGBCode = RGBValue(this.HexCode);
                this.mc_color_name = "DARKGRAY";
                this.ntc = NamedTextColor.DARK_GRAY;
                break;
            case '9':
                this.HexCode = "#5555FF";
                this.RGBCode = RGBValue(this.HexCode);
                this.mc_color_name = "BLUE";
                this.ntc = NamedTextColor.BLUE;
                break;
            case 'a':
                this.HexCode = "#55FF55";
                this.RGBCode = RGBValue(this.HexCode);
                this.mc_color_name = "GREEN";
                this.ntc = NamedTextColor.GREEN;
                break;
            case 'b':
                this.HexCode = "#55FFFF";
                this.RGBCode = RGBValue(this.HexCode);
                this.mc_color_name = "AQUA";
                this.ntc = NamedTextColor.AQUA;
                break;
            case 'c':
                this.HexCode = "#FF5555";
                this.RGBCode = RGBValue(this.HexCode);
                this.mc_color_name = "RED";
                this.ntc = NamedTextColor.RED;
                break;
            case 'd':
                this.HexCode = "#FF55FF";
                this.RGBCode = RGBValue(this.HexCode);
                this.mc_color_name = "LIGHTPURPLE";
                this.ntc = NamedTextColor.LIGHT_PURPLE;
                break;
            case 'e':
                this.HexCode = "#FFFF55";
                this.RGBCode = RGBValue(this.HexCode);
                this.mc_color_name = "YELLOW";
                this.ntc = NamedTextColor.YELLOW;
                break;
            case 'f':
                this.HexCode = "#FFFFFF";
                this.RGBCode = RGBValue(this.HexCode);
                this.mc_color_name = "WHITE";
                this.ntc = NamedTextColor.WHITE;
                break;
            case 'k':
                this.mc_color_name = "OBFUSCATED";
                this.HexCode = null;
                this.RGBCode = null;
                this.tdc = TextDecoration.OBFUSCATED;
                break;
            case 'l':
                this.mc_color_name = "BOLD";
                this.HexCode = null;
                this.RGBCode = null;
                this.tdc = TextDecoration.BOLD;
                break;
            case 'm':
                this.mc_color_name = "STRIKETHROUGH";
                this.HexCode = null;
                this.RGBCode = null;
                this.tdc = TextDecoration.STRIKETHROUGH;
                break;
            case 'n':
                this.mc_color_name = "UNDERLINE";
                this.HexCode = null;
                this.RGBCode = null;
                this.tdc = TextDecoration.UNDERLINED;
                break;
            case 'o':
                this.mc_color_name = "ITALIC";
                this.HexCode = null;
                this.RGBCode = null;
                this.tdc = TextDecoration.ITALIC;
                break;
            case 'r':
                this.mc_color_name = "RESET";
                this.HexCode = null;
                this.RGBCode = null;
                this.ntc = null;
                break;
            default:
                this.mc_color_name = null;
                this.HexCode = null;
                this.RGBCode = null;
                this.ntc = null;
        }
    }
    public @Nullable NamedTextColor getNtc(){
        return this.ntc;
    }
    public @Nullable TextDecoration getTdc(){return this.tdc;}
    private static int[] RGBValue(String HexCode) {
        return new int[]{Integer.valueOf(HexCode.substring(1, 3), 16), Integer.valueOf(HexCode.substring(3, 5), 16), Integer.valueOf(HexCode.substring(5, 7), 16)};
    }
    public int[] getRGBValues(){
        return this.RGBCode;
    }
    public String getMc_color_name(){
        return this.mc_color_name;
    }

    public String getHexCode(){
        return this.HexCode;
    }
    public String getMC_Color_Code() {
        return this.MC_Color_Code;
    }
    public static String fromChar(String str) {
        return switch (str.substring(0,2).toLowerCase()) {
            case "&0" -> Colors.BLACK+str.substring(2);
            case "&1" -> Colors.DARKBLUE+str.substring(2);
            case "&2" -> Colors.DARKGREEN+str.substring(2);
            case "&3" -> Colors.DARKAQUA+str.substring(2);
            case "&4" -> Colors.DARKRED+str.substring(2);
            case "&5" -> Colors.DARKPURPLE+str.substring(2);
            case "&6" -> Colors.GOLD+str.substring(2);
            case "&7" -> Colors.GRAY+str.substring(2);
            case "&8" -> Colors.DARKGRAY+str.substring(2);
            case "&9" -> Colors.BLUE+str.substring(2);
            case "&a" -> Colors.GREEN+str.substring(2);
            case "&b" -> Colors.AQUA+str.substring(2);
            case "&c" -> Colors.RED+str.substring(2);
            case "&d" -> Colors.LIGHTPURPLE+str.substring(2);
            case "&e" -> Colors.YELLOW+str.substring(2);
            case "&f" -> Colors.WHITE+str.substring(2);
            case "&k" -> Colors.OBFUSCATED+str.substring(2);
            case "&l" -> Colors.BOLD+str.substring(2);
            case "&m" -> Colors.STRIKETHROUGH+str.substring(2);
            case "&n" -> Colors.UNDERLINE+str.substring(2);
            case "&o" -> Colors.ITALIC+str.substring(2);
            case "&r" -> Colors.RESET+str.substring(2);
            default -> str.substring(2);
        };
    }
    public static String fromHex(String str) {
        return switch (str.substring(0,7).toUpperCase()) {
            case "#000000" -> Colors.BLACK+str.substring(7);
            case "#0000AA" -> Colors.DARKBLUE+str.substring(7);
            case "#00AA00" -> Colors.DARKGREEN+str.substring(7);
            case "#00AAAA" -> Colors.DARKAQUA+str.substring(7);
            case "#AA0000" -> Colors.DARKRED+str.substring(7);
            case "#AA00AA" -> Colors.DARKPURPLE+str.substring(7);
            case "#FFAA00" -> Colors.GOLD+str.substring(7);
            case "#AAAAAA" -> Colors.GRAY+str.substring(7);
            case "#555555" -> Colors.DARKGRAY+str.substring(7);
            case "#5555FF" -> Colors.BLUE+str.substring(7);
            case "#55FF55" -> Colors.GREEN+str.substring(7);
            case "#55FFFF" -> Colors.AQUA+str.substring(7);
            case "#FF5555" -> Colors.RED+str.substring(7);
            case "#FF55FF" -> Colors.LIGHTPURPLE+str.substring(7);
            case "#FFFF55" -> Colors.YELLOW+str.substring(7);
            case "#FFFFFF" -> Colors.WHITE+str.substring(7);
            default -> str.substring(7);
        };
    }
    public static MYColor fromName(String name){
        return switch (name){
            case "BLACK" -> new MYColor("&0");
            case "DARKBLUE" -> new MYColor("&1");
            case "DARKGREN" -> new MYColor("&2");
            case "DARKAQUA" -> new MYColor("&3");
            case "DARKRED" -> new MYColor("&4");
            case "DARKPURPLE" -> new MYColor("&5");
            case "GOLD" -> new MYColor("&6");
            case "GRAY" -> new MYColor("&7");
            case "DARKGRAY" -> new MYColor("&8");
            case "BLUE" -> new MYColor("&9");
            case "GREEN" -> new MYColor("&a");
            case "AQUA" -> new MYColor("&b");
            case "RED" -> new MYColor("&c");
            case "LIGHTPURPLE" -> new MYColor("&d");
            case "YELLOW" -> new MYColor("&e");
            default -> new MYColor("&f");
        };
    }
}