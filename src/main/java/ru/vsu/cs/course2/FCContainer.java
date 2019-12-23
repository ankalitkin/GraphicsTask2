package ru.vsu.cs.course2;

import lombok.Data;
import lombok.NonNull;

@Data
public class FCContainer {
    @NonNull
    FigureConfiguration fc;

    @Override
    public String toString() {
        return fc.toString();
    }
}
