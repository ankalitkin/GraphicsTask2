package ru.vsu.cs.course2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Picture {
    private List<FigureConfiguration> figures;
    private int selected = 0;

    public Picture copy() {
        return new Picture(new ArrayList<>(figures), selected);
    }
}
