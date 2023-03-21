package me.voidxwalker.autoreset.atum;


        import net.minecraft.client.option.KeyBinding;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

        import static org.spongepowered.include.com.google.common.collect.Lists.newArrayList;


public final class KeyBindingHelper {
    private static final List<KeyBinding> moddedKeyBindings = newArrayList();


    public static KeyBinding registerKeyBinding(KeyBinding binding) {
        moddedKeyBindings.add(binding);
        return binding;
    }

    public static KeyBinding[] process(KeyBinding[] allKeys) {
        List<KeyBinding> newAllKeys = new ArrayList<>(Arrays.asList(allKeys));
        newAllKeys.removeAll(moddedKeyBindings);
        newAllKeys.addAll(moddedKeyBindings);
        return newAllKeys.toArray(new KeyBinding[0]);
    }
}