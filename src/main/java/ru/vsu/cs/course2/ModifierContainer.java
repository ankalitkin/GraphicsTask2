package ru.vsu.cs.course2;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vsu.cs.course2.modifiers.Modifier;

@Data
@AllArgsConstructor
public class ModifierContainer {
    @NotNull
    private Modifier modifier;

    @Override
    public String toString() {
        return modifier.toString();
    }
}
