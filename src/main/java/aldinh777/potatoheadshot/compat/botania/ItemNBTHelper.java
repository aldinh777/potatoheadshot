package aldinh777.potatoheadshot.compat.botania;

import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public final class ItemNBTHelper {

    public ItemNBTHelper() {
    }

    public static NBTTagCompound getNBT(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        return stack.getTagCompound();
    }

    public static void setInt(ItemStack stack, String tag, int i) {
        getNBT(stack).setInteger(tag, i);
    }

    public static void setCompound(ItemStack stack, String tag, NBTTagCompound cmp) {
        if (!tag.equalsIgnoreCase("ench")) {
            getNBT(stack).setTag(tag, cmp);
        }

    }

    public static boolean matchTag(@Nullable NBTBase template, @Nullable NBTBase target) {
        if (template instanceof NBTTagCompound && target instanceof NBTTagCompound) {
            return matchTagCompound((NBTTagCompound)template, (NBTTagCompound)target);
        } else if (template instanceof NBTTagList && target instanceof NBTTagList) {
            return matchTagList((NBTTagList)template, (NBTTagList)target);
        } else {
            return template == null || target != null && target.equals(template);
        }
    }

    private static boolean matchTagCompound(NBTTagCompound template, NBTTagCompound target) {
        if (template.getSize() > target.getSize()) {
            return false;
        } else {
            Iterator var2 = template.getKeySet().iterator();

            String key;
            do {
                if (!var2.hasNext()) {
                    return true;
                }

                key = (String)var2.next();
            } while(matchTag(template.getTag(key), target.getTag(key)));

            return false;
        }
    }

    private static boolean matchTagList(NBTTagList template, NBTTagList target) {
        if (template.tagCount() > target.tagCount()) {
            return false;
        } else {
            for(int i = 0; i < template.tagCount(); ++i) {
                if (!matchTag(template.get(i), target.get(i))) {
                    return false;
                }
            }

            return true;
        }
    }
}
