import com.sun.source.doctree.TextTree;

import javax.management.JMException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;

public class TetrisMain extends Canvas implements Runnable, KeyListener {

    public static final int WIDTH = 400, HEIGHT = 565;
    private Image[] tetrisBlocks;

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Tetris");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);

        KeyGetter.loadKeys();
        try{
            Config.loadConfig();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        JMenuBar bar = new JMenuBar();
        bar.setBounds(0, 0, WIDTH, 25);

        JMenu file = new JMenu("File");
        file.setBounds(0, 0, 45, 24);

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //code for new game
                System.out.println("Starting new game.......");
            }
        });

        JMenuItem highScore = new JMenuItem("High Score");
        highScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //code for new game
                int highscore = 0;
                final JFrame alert = new JFrame("High Score");
                alert.setSize(200, 150);
                alert.setLayout(null);
                alert.setLocationRelativeTo(null);
                alert.setAlwaysOnTop(true);

                JLabel score = new JLabel("The High Score is: " + highscore);
                score.setBounds(0, 0, 200, 50);

                JButton okayButton = new JButton("Okay");
                okayButton.setBounds(50, 50, 100, 30);

                alert.add(score);
                alert.add(okayButton);
                okayButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        alert.dispose();
                    }
                });
                alert.setResizable(false);
                alert.setVisible(true);
            }
        });


        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //code for new game
                System.out.println("closing");
                System.exit(0);
            }
        });

        JMenuItem options = new JMenuItem("Options");
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Config.openConfig(frame);

            }
        });

        TetrisMain tm = new TetrisMain();
        tm.setBounds(0, 25, WIDTH, HEIGHT - 25);

        frame.add(tm);
        file.add(newGame);
        file.add(highScore);
        file.add(options);
        file.add(exit);
        bar.add(file);
        frame.add(bar);
        frame.setVisible(true);
        tm.start();
    }

    public void start() {
        Thread t = new Thread(this);
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }

    public void run() {
        init();
        boolean running = true;
        while (running) {
            update();
            BufferStrategy buf = getBufferStrategy();
            if (buf == null) {
                createBufferStrategy(3);
                continue;
            }
            Graphics2D g = (Graphics2D) buf.getDrawGraphics();
            render(g);
            buf.show();
        }
    }
    public void init(){
        requestFocus();
        try {
            tetrisBlocks = ImageLoader.loadImage("/res/tetris.png",25);
        }
        catch ( IOException e){
            System.out.println("Error loading in tetris.png");
            System.exit(0);
        }
    }

    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Calibri", Font.PLAIN, 20));
        g.drawString("Tetris", 170, 50);
        g.drawImage(tetrisBlocks[0],100,100,25,25,null);
    }

    public void update() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }
}
