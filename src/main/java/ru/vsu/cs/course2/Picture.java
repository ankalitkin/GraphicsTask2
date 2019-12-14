package ru.vsu.cs.course2;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Picture {
    private List<FigureConfiguration> figures;
}
