package Heap;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.IntStream;

import static Heap.Heap.getHeightOfRoot;
import static Heap.Heap.getParentIndex;

public class Gui extends JFrame {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;

    private ArrayList<Line> lines = new ArrayList<Line>();
    private Heap<Integer> heap = new Heap<Integer>();

    JButton pollButton;
    JButton addButton;
    JButton removeButton;
    JButton directionButton;

    JTextField poll;
    JTextField add;
    JTextField remove;
    JTextField direction;

    JPanel panel;
    JLabel jlabel;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        lines.forEach(l -> {
            g.setColor(Color.BLACK);
            g.drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
        });
    }

    public Gui() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(WIDTH + 37, HEIGHT);
        setLocation((screenSize.width - WIDTH - 37) / 2, (screenSize.height - HEIGHT) / 2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Binary Heap");
        panel = new JPanel();
        buttonCreate(panel);
        panel.setLayout(null);
        add(panel);
        setResizable(false);
        setVisible(true);

        addButton.addActionListener(e -> {
            if (add.getText().equals("")) {
                javax.swing.JOptionPane
                        .showMessageDialog(null, "Введите значение в поле", "Error",
                                JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (heap.size() == 63) {
                javax.swing.JOptionPane
                        .showMessageDialog(null, "В куче 63 элемента!!!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (Integer.parseInt(add.getText()) >= 1000 || Integer.parseInt(add.getText()) <= -1000) {
                javax.swing.JOptionPane
                        .showMessageDialog(null, "Число должно быть в диапозоне от -999 до 999", "Error",
                                JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (heap.contains(Integer.parseInt(add.getText()))) {
                javax.swing.JOptionPane
                        .showMessageDialog(null, "Число уже есть в куче", "Error",
                                JOptionPane.ERROR_MESSAGE);
                return;
            }

            heap.add(Integer.parseInt(add.getText()));
            add.setText("");
            updateHeap();
        });

        removeButton.addActionListener(e -> {
            if (remove.getText().equals("")) {
                javax.swing.JOptionPane
                        .showMessageDialog(null, "Введите значение в поле", "Error",
                                JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (heap.size() == 0) {
                javax.swing.JOptionPane
                        .showMessageDialog(null, "Куча пуста!!!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (Integer.parseInt(remove.getText()) >= 1000 || Integer.parseInt(remove.getText()) <= -1000) {
                javax.swing.JOptionPane
                        .showMessageDialog(null, "Число должно быть в диапозоне от -999 до 999", "Error",
                                JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!heap.contains(Integer.parseInt(remove.getText()))) {
                javax.swing.JOptionPane
                        .showMessageDialog(null, "Числа нет в куче", "Error",
                                JOptionPane.ERROR_MESSAGE);
                return;
            }

            heap.remove(Integer.parseInt(remove.getText()));
            remove.setText("");
            updateHeap();
        });

        pollButton.addActionListener(e -> {
            if (heap.size() == 0) {
                javax.swing.JOptionPane
                        .showMessageDialog(null, "Куча пуста!!!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                return;
            }

            poll.setText(String.valueOf(heap.peek()));
            heap.poll();
            updateHeap();
        });

        directionButton.addActionListener(e -> {
            heap.changeHeapDirection();
            direction.setText(direction.getText().equals("Max") ? "Min" : "Max");
            updateHeap();
        });
    }

    private void buttonCreate(JPanel panel){
        addButtonCreate(panel);
        pollButtonCreate(panel);
        removeButtonCreate(panel);
        directionButtonCreate(panel);

    }

    private void addButtonCreate(@NotNull JPanel panel){
        addButton = new JButton("Add");
        addButton.setBounds(480, 500, 70, 25);
        panel.add(addButton);

        add = new JTextField(5);
        add.setBounds(560, 500, 50, 25);
        panel.add(add);
    }

    private void removeButtonCreate(@NotNull JPanel panel){
        removeButton = new JButton("Remove");
        removeButton.setBounds(680, 500, 100, 25);
        panel.add(removeButton);

        remove = new JTextField(5);
        remove.setBounds(790, 500, 50, 25);
        panel.add(remove);
    }

    private void pollButtonCreate(@NotNull JPanel panel){
        pollButton = new JButton("Poll");
        pollButton.setBounds(280, 500, 70, 25);
        panel.add(pollButton);

        poll = new JTextField(5);
        poll.setBounds(360, 500, 30, 25);
        poll.setEditable(false);
        panel.add(poll);
    }

    private void directionButtonCreate(@NotNull JPanel panel){
        directionButton = new JButton("Direction");
        directionButton.setBounds(60, 500, 90, 25);
        panel.add(directionButton);

        direction = new JTextField(3);
        direction.setText("Max");
        direction.setBounds(160, 500, 30, 25);
        direction.setEditable(false);
        panel.add(direction);
    }

    private void updateHeap() {
        clear();

        IntStream.range(0, heap.size()).forEach(i -> {
            jlabel = new JLabel();
            panel.add(jlabel);
            jlabel.setText(String.valueOf(heap.get(i)));
            jlabel.setHorizontalAlignment(SwingConstants.CENTER);
            jlabel.setVerticalAlignment(SwingConstants.CENTER);
            jlabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            jlabel.setBounds((int) getPositionX(i), getPositionY(i), 30, 25);

            if (i > 0) {
                Line line = getLine(i);
                lines.add(new Line(line.getX1(), line.getY1(), line.getX2(), line.getY2()));
            }
        });
    }

    private void clear(){
        setVisible(false);
        panel.removeAll();
        panel.add(addButton);
        panel.add(add);
        panel.add(pollButton);
        panel.add(poll);
        panel.add(removeButton);
        panel.add(remove);
        panel.add(direction);
        panel.add(directionButton);
        lines.removeAll(lines);
        setVisible(true);
    }

    private double getPositionX(int currentIndex){
        if (currentIndex == 0) return WIDTH >> 1;
        if (currentIndex % 2 == 0)
            return getPositionX((getParentIndex(currentIndex))) + (WIDTH >> 1) / Math.pow(2, getHeightOfRoot(currentIndex));
        return getPositionX((getParentIndex(currentIndex))) - (WIDTH >> 1) / Math.pow(2, getHeightOfRoot(currentIndex)) ;
    }

    private int getPositionY(int currentIndex){
        if (currentIndex == 0) return 10;
        if (currentIndex % 2 == 0)  return 40 + 80 * getHeightOfRoot(currentIndex);
        return 40 + 80 * getHeightOfRoot(currentIndex);
    }

    private Line getLine(int currentIndex){
        int x1 = (int) getPositionX(currentIndex) + 16;
        int x2 = (int) getPositionX(getParentIndex(currentIndex)) + 16;
        int y1 = getPositionY(currentIndex) + 32;
        int y2 = getPositionY(getParentIndex(currentIndex)) + 57;
        return new Line(x1, y1, x2, y2);
    }
}

