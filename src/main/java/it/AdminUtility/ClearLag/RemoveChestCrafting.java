package it.AdminUtility.ClearLag;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static it.plugin.Plugin.lgg;
import static it.plugin.Plugin.plugin;

public class RemoveChestCrafting implements Listener {
    private static List<NamespacedKey> recipesList = new ArrayList<>();
    public static void a(){
        Iterator<Recipe> it = Bukkit.recipeIterator();
        while(it.hasNext()){
            Recipe r = it.next();
            ItemStack is = r.getResult();
            if (is.getType() == Material.CHEST||is.getType()==Material.BARREL || is.getType() == Material.SHULKER_BOX ||
                    is.getType() == Material.HOPPER || is.getType() == Material.CHEST_MINECART || is.getType() == Material.TRAPPED_CHEST
            ){
                it.remove();
                lgg.info("Removed crafting for " + is.getType().getKey().getKey());
            }
        }
        BarrelRecipe();
        HopperRecipe();
        ShulkerBoxRecipe();
        ChestMinecartRecipe();
        TrappedChestRecipe();
    }
    private static void BarrelRecipe(){
        ItemStack is = new ItemStack(Material.BARREL);
        NamespacedKey nsk = new NamespacedKey(plugin, "barrel");
        ShapedRecipe r = new ShapedRecipe(nsk, is);
        r.shape("ppp", "p p", "ppp");
        r.setIngredient('p', new RecipeChoice.MaterialChoice(Tag.PLANKS));
        Bukkit.addRecipe(r, true);
        recipesList.add(nsk);
    }
    private static void HopperRecipe(){
        ItemStack is = new ItemStack(Material.HOPPER);
        NamespacedKey nsk = new NamespacedKey(plugin, "hopper");
        ShapedRecipe r = new ShapedRecipe(nsk, is);
        r.shape("i i", "ibi", " i ");
        r.setIngredient('b', Material.BARREL);
        r.setIngredient('i',  Material.IRON_INGOT);
        Bukkit.addRecipe(r, true);
        recipesList.add(nsk);
    }
    private static void ShulkerBoxRecipe(){
        ItemStack is = new ItemStack(Material.SHULKER_BOX);
        NamespacedKey nsk = new NamespacedKey(plugin, "shulker_box");
        ShapedRecipe r = new ShapedRecipe(nsk, is);
        r.shape(" s ", " b ", " s ");
        r.setIngredient('s', Material.SHULKER_SHELL);
        r.setIngredient('b', Material.BARREL);
        Bukkit.addRecipe(r, true);
        recipesList.add(nsk);
    }
    private static void ChestMinecartRecipe(){
        ItemStack is = new ItemStack(Material.CHEST_MINECART);
        NamespacedKey nsk = new NamespacedKey(plugin, "chest_minecart");
        ShapedRecipe r = new ShapedRecipe(nsk, is);
        r.shape("b", "m");
        r.setIngredient('b', Material.BARREL);
        r.setIngredient('m', Material.MINECART);
        Bukkit.addRecipe(r, true);
        recipesList.add(nsk);
    }
    private static void TrappedChestRecipe(){
        ItemStack is = new ItemStack(Material.TRAPPED_CHEST);
        NamespacedKey nsk = new NamespacedKey(plugin, "trapped_chest");
        ShapedRecipe r = new ShapedRecipe(nsk, is);
        r.shape("bt");
        r.setIngredient('b', Material.BARREL);
        r.setIngredient('t', Material.TRIPWIRE_HOOK);
        Bukkit.addRecipe(r, true);
        recipesList.add(nsk);
    }
    public static void GenReplacer(Player p){
        p.discoverRecipes(recipesList);
    }
}
