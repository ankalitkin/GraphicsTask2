package ru.vsu.cs.course2;

import ru.vsu.cs.course2.figures.Drawable;
import ru.vsu.cs.course2.modifiers.Modifier;
import ru.vsu.cs.course2.modifiers.OutlineModifier;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class CurveDrawerForm {
    private static final String CHOOSE_OPEN_FILE_MESSAGE = "Open file";
    private static final String CHOOSE_SAVE_FILE_MESSAGE = "Save file";
    private final ScreenConverter screenConverter;
    private final Canvas canvas;
    private JPanel drawPanel;
    private JPanel rootPanel;
    private JList<FCContainer> figures;
    private JButton addButton;
    private JButton removeButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton upButton;
    private JButton downButton;
    private JButton undoButton;
    private JButton redoButton;
    private JPanel propPanel;
    private JButton addModButton;
    private JButton removeModButton;
    private JButton upModButton;
    private JButton downModButton;
    private JList<ModifierContainer> modifiers;
    private DefaultListModel<FCContainer> figuresListModel = new DefaultListModel<>();
    private DefaultListModel<ModifierContainer> modifiersListModel = new DefaultListModel<>();
    private List<Drawable> figuresList = new LinkedList<>();
    private boolean updating;
    private Stack<Runnable> undoStack = new Stack<>();
    private Stack<Runnable> redoStack = new Stack<>();
    private Timer stateSaveTimer;
    private FigureConfiguration lastState;

    {
        System.out.println(" привет андрей ))))");
    }

    private CurveDrawerForm() {
        canvas = new Canvas();
        canvas.setReversed(true);
        screenConverter = new ScreenConverter(canvas);
        canvas.setEditor(createEditor());
        canvas.setScreenConverter(screenConverter);
        drawPanel.setLayout(new GridLayout());
        drawPanel.add(canvas);
        figures.setModel(figuresListModel);
        figures.addListSelectionListener(e -> {
            onSelectedFigureChange();
        });
        propPanel.setLayout(new GridLayout());
        modifiers.addListSelectionListener(e -> {
            FigureConfiguration fc = getFigureConfiguration();
            if (fc != null)
                fc.setSelected(modifiers.getSelectedIndex());
            propPanel.removeAll();
            Modifier modifier = getModifier();
            if (modifier == null)
                return;
            propPanel.add(modifier.getConfigPanel());
            propPanel.revalidate();
            propPanel.repaint();
        });

        FigureConfiguration sfc = FigureConfiguration.getSampleFigureConfiguration();
        for (Modifier modifier : sfc.getModifiers())
            modifier.setOnChangedCallback(this::updateFigures);
        figuresListModel.add(0, new FCContainer(sfc));

        figures.setSelectedIndex(0);
        modifiers.setModel(modifiersListModel);

        addModButton.addActionListener(e -> {
            OutlineModifier modifier = new OutlineModifier();
            modifier.setOnChangedCallback(this::updateFigures);
            ModifierContainer element = new ModifierContainer(modifier);
            modifiersListModel.addElement(element);
            fcChanged();
        });

        removeModButton.addActionListener(e -> {
            int index = modifiers.getSelectedIndex();
            if (index < 0)
                return;
            modifiersListModel.remove(index);
            modifiers.repaint();
        });

        upModButton.addActionListener(e -> {
            int index = modifiers.getSelectedIndex();
            if (index < 1)
                return;
            ModifierContainer mc = modifiersListModel.get(index);
            modifiersListModel.remove(index);
            modifiersListModel.add(index - 1, mc);
            modifiers.setSelectedIndex(index - 1);
        });

        downModButton.addActionListener(e -> {
            int index = modifiers.getSelectedIndex();
            if (index < 0 || index > modifiersListModel.size() - 2)
                return;
            ModifierContainer mc = modifiersListModel.get(index);
            modifiersListModel.remove(index);
            modifiersListModel.add(index + 1, mc);
            modifiers.setSelectedIndex(index + 1);
        });

        addButton.addActionListener(e -> {
            figuresListModel.add(0, new FCContainer(new FigureConfiguration()));
            figures.setSelectedIndex(0);
        });

        removeButton.addActionListener(e -> {
            int index = figures.getSelectedIndex();
            if (index < 0)
                return;
            figuresListModel.remove(index);
            figures.repaint();
        });

        upButton.addActionListener(e -> {
            int index = figures.getSelectedIndex();
            if (index < 1)
                return;
            FCContainer fc = figuresListModel.get(index);
            figuresListModel.remove(index);
            figuresListModel.add(index - 1, fc);
            figures.setSelectedIndex(index - 1);
        });

        downButton.addActionListener(e -> {
            int index = figures.getSelectedIndex();
            if (index < 0 || index > figuresListModel.size() - 2)
                return;
            FCContainer fc = figuresListModel.get(index);
            figuresListModel.remove(index);
            figuresListModel.add(index + 1, fc);
            figures.setSelectedIndex(index + 1);
        });

        saveButton.addActionListener(e -> {
            Picture picture = getPicture();
            String filename = browseSaveFile();
            if (filename == null)
                return;
            Utils.saveToFile(filename, picture);
        });

        loadButton.addActionListener(e -> {
            String filename = browseOpenFile();
            if (filename == null)
                return;
            Picture picture = Utils.loadFromFile(filename);
            if (picture == null)
                return;
            setPicture(picture);
            onSelectedFigureChange();
        });

        undoButton.addActionListener(e -> undo());
        redoButton.addActionListener(e -> redo());

        canvas.setOperationCallback(this::saveState);

        stateSaveTimer = new Timer(100, e -> saveState());
        stateSaveTimer.setRepeats(false);
    }

    private void updateFigures() {
        figuresList = getFiguresList();
        canvas.setFigures(figuresList);
        canvas.setEditor(createEditor());
        canvas.repaint();
    }

    private Picture getPicture() {
        List<FigureConfiguration> list = new ArrayList<>();
        for (int i = 0; i < figuresListModel.size(); i++) {
            list.add(figuresListModel.get(i).getFc());
        }
        return new Picture(list, figures.getSelectedIndex());
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        JFrame frame = new JFrame("Curved drawer by @kalitkin_a_v");
        frame.setContentPane(new CurveDrawerForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.setVisible(true);
    }

    private List<Drawable> getFiguresList() {
        return Collections.list(figuresListModel.elements()).stream()
                .map(FigureBuilder::createFigure).collect(Collectors.toList());
    }

    private List<Modifier> getModifiersList() {
        return Collections.list(modifiersListModel.elements()).stream()
                .map(ModifierContainer::getModifier).collect(Collectors.toList());
    }

    private void setModifiersList(FigureConfiguration fc) {
        updating = true;
        modifiersListModel.clear();
        if (fc == null)
            return;
        List<Modifier> modifiers = fc.getModifiers();
        if (modifiers == null)
            return;
        for (Modifier modifier : modifiers) {
            modifiersListModel.addElement(new ModifierContainer(modifier));
        }
        this.modifiers.setSelectedIndex(fc.getSelected());
        updating = false;
    }

    private void setPicture(Picture picture) {
        updating = true;
        figuresListModel.clear();
        if (picture == null)
            return;
        List<FigureConfiguration> list = picture.getFigures();
        for (FigureConfiguration fc : list) {
            figuresListModel.addElement(new FCContainer(fc));
        }
        figures.setSelectedIndex(picture.getSelected());
        updating = false;
    }

    private FCContainer getCurrentFc() {
        int index = figures.getSelectedIndex();
        if (index < 0)
            return null;
        return figuresListModel.get(index);
    }

    private ModifierContainer getCurrentMc() {
        int index = modifiers.getSelectedIndex();
        if (index < 0)
            return null;
        return modifiersListModel.get(index);
    }

    private FigureConfiguration getFigureConfiguration() {
        FCContainer fcContainer = getCurrentFc();
        if (fcContainer == null)
            return null;
        return fcContainer.getFc();
    }

    private Modifier getModifier() {
        ModifierContainer container = getCurrentMc();
        if (container == null)
            return null;
        return container.getModifier();
    }

    private void saveStateWithDelay() {
        if (stateSaveTimer.isRunning())
            stateSaveTimer.stop();
        stateSaveTimer.start();
    }

    private void fcChanged() {
        FigureConfiguration fc = getFigureConfiguration();
        if (fc == null)
            return;
        figuresList.set(figures.getSelectedIndex(), FigureBuilder.createFigure(fc));

        canvas.repaint();
        figures.repaint();
    }


    private Editor createEditor() {
        FigureConfiguration fc = getFigureConfiguration();
        if (fc == null)
            return null;
        return new Editor(fc.getPlane(), screenConverter, canvas::repaint);
    }

    private void onSelectedFigureChange() {
        loadFC();
        updateFigures();
        propPanel.revalidate();
        propPanel.repaint();
    }

    private String browseSaveFile() {
        FileDialog fileDialog = new FileDialog((Frame) null, CHOOSE_SAVE_FILE_MESSAGE);
        fileDialog.setMode(FileDialog.SAVE);
        fileDialog.setFile("figure.json");
        fileDialog.setVisible(true);

        String dir = fileDialog.getDirectory();
        String file = fileDialog.getFile();
        if (dir != null && file != null) {
            if (file.contains("."))
                return dir + file;
            else
                return String.format("%s%s.json", dir, file);
        }
        return null;
    }

    private String browseOpenFile() {
        FileDialog fileDialog = new FileDialog((Frame) null, CHOOSE_OPEN_FILE_MESSAGE);
        fileDialog.setMode(FileDialog.LOAD);
        fileDialog.setVisible(true);

        String dir = fileDialog.getDirectory();
        String file = fileDialog.getFile();
        if (dir != null && file != null) {
            return dir + file;
        }
        return null;
    }

    private void loadFC() {
        setModifiersList(getFigureConfiguration());
    }

    private void saveState() {
        if (!saveCurrentStateToUndo())
            return;
        redoStack.clear();
    }

    private boolean saveCurrentStateToUndo() {
        FCContainer currentFc = getCurrentFc();
        if (currentFc == null) {
            return false;
        }
        FigureConfiguration current = currentFc.getFc();
        if (current.equals(lastState)) {
            return false;
        }
        FigureConfiguration revertTo = current.clone();
        undoStack.add(() -> {
            saveCurrentStateToRedo();
            currentFc.setFc(revertTo);
            onSelectedFigureChange();
        });
        lastState = revertTo;
        return true;
    }

    private void saveCurrentStateToRedo() {
        FCContainer currentFc = getCurrentFc();
        if (currentFc == null) {
            return;
        }
        FigureConfiguration revertTo = currentFc.getFc().clone();
        redoStack.add(() -> {
            saveCurrentStateToUndo();
            currentFc.setFc(revertTo);
            onSelectedFigureChange();
        });
    }

    private void undo() {
        if (undoStack.isEmpty())
            return;
        undoStack.pop().run();
    }

    private void redo() {
        if (redoStack.isEmpty())
            return;
        redoStack.pop().run();
    }
}