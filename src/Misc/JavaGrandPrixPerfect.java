package misc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.sound.sampled.*;

public class JavaGrandPrixPerfect extends JPanel implements ActionListener, KeyListener {

    // --- Screen Settings ---
    private final int SCREEN_W = 1024;
    private final int SCREEN_H = 768;
    private final int CENTER_X = SCREEN_W / 2;
    private final int CENTER_Y = SCREEN_H / 2;

    // --- 3D Settings ---
    private final int ROAD_WIDTH = 2000;
    private final int SEGMENT_LENGTH = 200;
    private final int DRAW_DISTANCE = 300;
    private final double CAMERA_DEPTH = 0.84;
    private final double CAMERA_HEIGHT = 1000;

    // --- Physics ---
    private double playerX = 0;
    private double playerZ = 0;
    private double speed = 0;
    private double maxSpeed = 12000;

    // --- Objects ---
    private ArrayList<Line> lines;
    private int trackLength;
    private double totalTrackLen;
    private ArrayList<Car> cars;

    // --- State ---
    private boolean keyLeft, keyRight, keyUp, keyDown, keySpace;
    private Timer gameLoop;

    // ==========================================================
    // 3D LINE
    // ==========================================================
    private static class Line {
        double x, y, z;
        double X, Y, W;
        double curve, scale;
        Color roadColor, grassColor, rumbleColor;

        public Line(int i) {
            this.z = i * 200;
            this.curve = 0;
            this.y = 0;

            boolean light = (i / 3) % 2 == 0;
            this.roadColor = light ? new Color(105, 105, 105) : new Color(100, 100, 100);
            this.grassColor = light ? new Color(16, 200, 16) : new Color(0, 160, 0);
            this.rumbleColor = light ? new Color(255, 255, 255) : new Color(200, 0, 0);
        }

        public void project(double playerX, double camH, double camZ, double camDepth, int width, int height, double roadWidth) {
            this.scale = camDepth / (this.z - camZ);
            this.X = (1 + this.scale * (this.x - playerX)) * width / 2;
            this.Y = (1 - this.scale * (this.y - camH)) * height / 2;
            this.W = this.scale * roadWidth * width / 2;
        }
    }

    private static class Car implements Comparable<Car> {
        double z, x, speed;
        Color color;
        public Car(double z) {
            this.z = z;
            this.x = (Math.random() > 0.5 ? 0.5 : -0.5);
            this.speed = 8000 + Math.random() * 3000;
            this.color = new Color(50 + (int)(Math.random()*200), 50 + (int)(Math.random()*200), 50);
        }
        @Override public int compareTo(Car o) { return Double.compare(o.z, this.z); }
    }

    public JavaGrandPrixPerfect() {
        setPreferredSize(new Dimension(SCREEN_W, SCREEN_H));
        setFocusable(true);
        addKeyListener(this);

        resetGame();

        Sound.init();

        gameLoop = new Timer(16, this);
        gameLoop.start();
    }

    private void resetGame() {
        lines = new ArrayList<>();
        cars = new ArrayList<>();
        playerZ = 0;
        speed = 0;

        // Build Track
        trackLength = 2000;
        totalTrackLen = trackLength * SEGMENT_LENGTH;

        for (int i = 0; i < trackLength; i++) {
            Line line = new Line(i);
            if (i > 100 && i < 300) line.curve = 0.5;
            if (i > 400 && i < 600) line.curve = -0.5;
            if (i > 800 && i < 1200) line.curve = 0.8;
            if (i > 200 && i < 600) line.y = Math.sin(i / 20.0) * 1500;
            lines.add(line);
        }

        // Spawn Cars in a visible pack
        for(int i=0; i<19; i++) {
            cars.add(new Car(1000 + i * 500));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // --- 1. Physics ---
        if (keyUp) speed += 100; else if (keyDown) speed -= 200; else speed -= 40;
        if (Math.abs(playerX) > 1.1) speed -= 100;

        maxSpeed = keySpace ? 16000 : 12000;
        if (speed > maxSpeed) speed = maxSpeed;
        if (speed < 0) speed = 0;

        playerZ += speed;

        // --- FIX: LOOP THE TRACK ---
        // This prevents the numbers from getting too big and breaking the graphics
        if (playerZ >= totalTrackLen) playerZ -= totalTrackLen;
        if (playerZ < 0) playerZ += totalTrackLen;

        // Steering
        int segIdx = (int)(playerZ / SEGMENT_LENGTH) % lines.size();
        Line currentLine = lines.get(segIdx);
        playerX -= (currentLine.curve * (speed/maxSpeed)) * 0.03;
        if (keyLeft) playerX -= 0.04;
        if (keyRight) playerX += 0.04;

        // Move Cars
        for(Car c : cars) {
            c.z += c.speed * 0.016;
            if (c.z >= totalTrackLen) c.z -= totalTrackLen;
            if (c.z < 0) c.z += totalTrackLen;
        }

        // Sound
        Sound.targetPitch = (float)(speed / 6000.0);

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Sky & Ground
        g2.setColor(new Color(100, 160, 255));
        g2.fillRect(0,0, SCREEN_W, SCREEN_H);
        g2.setColor(new Color(20, 120, 20));
        g2.fillRect(0, CENTER_Y, SCREEN_W, CENTER_Y);

        // --- RENDER ROAD (Painter's Algo: Back to Front) ---
        // This is slower but guarantees no visual glitches
        double x = 0, dx = 0;
        int startPos = (int)(playerZ / SEGMENT_LENGTH);

        // First, calculate curve offsets
        ArrayList<Line> drawBuffer = new ArrayList<>();
        double camH = 1500 + lines.get(startPos).y;

        for (int n = startPos; n < startPos + DRAW_DISTANCE; n++) {
            Line l = lines.get(n % lines.size());

            // Handle Looping Logic
            double loopZ = (n >= lines.size()) ? totalTrackLen : 0;
            if (n >= lines.size() * 2) loopZ += totalTrackLen; // Double wrap safety

            double realZ = l.z + loopZ;

            l.project(playerX * ROAD_WIDTH - x, camH, playerZ, CAMERA_DEPTH, SCREEN_W, SCREEN_H, ROAD_WIDTH);
            x += dx;
            dx += l.curve;

            // Only add to buffer if visible
            if (l.Y > 0 && l.Y < SCREEN_H && l.scale > 0) {
                drawBuffer.add(l);
            }
        }

        // Draw Buffer from BACK (Horizon) to FRONT (Player)
        // This overwrites the far road with the near road. Solid.
        for (int i = drawBuffer.size() - 1; i > 0; i--) {
            Line l = drawBuffer.get(i);
            Line p = drawBuffer.get(i-1); // The line closer to camera

            // Note: In Painter's, 'p' is actually physically further in the array list
            // because we iterated forward.
            // Let's stick to the standard Draw Quad

            drawQuad(g2, l.grassColor, 0, p.Y, SCREEN_W, 0, l.Y, SCREEN_W);
            drawQuad(g2, l.rumbleColor, p.X, p.Y, p.W*1.2, l.X, l.Y, l.W*1.2);
            drawQuad(g2, l.roadColor, p.X, p.Y, p.W, l.X, l.Y, l.W);

            if (((startPos + i)/3)%2==0) drawQuad(g2, Color.WHITE, p.X, p.Y, p.W*0.02, l.X, l.Y, l.W*0.02);
        }

        // --- DRAW CARS ---
        ArrayList<Car> visibleCars = new ArrayList<>();
        for(Car c : cars) {
            double relZ = c.z - playerZ;
            if (relZ < -500) relZ += totalTrackLen;
            if (relZ > 0 && relZ < DRAW_DISTANCE * SEGMENT_LENGTH) visibleCars.add(c);
        }
        Collections.sort(visibleCars); // Sort so far cars draw basics.basics.basics.first

        for(Car c : visibleCars) {
            // Find which road segment this car is on
            int offset = (int)((c.z - playerZ) / SEGMENT_LENGTH);
            if (offset < 0 || offset >= drawBuffer.size()) continue;

            Line l = drawBuffer.get(offset);

            double scale = l.scale;
            double spriteX = l.X + (scale * c.x * ROAD_WIDTH * SCREEN_W/2);
            double spriteY = l.Y;

            int w = (int)(2000 * scale);
            int h = (int)(1000 * scale);

            g2.setColor(c.color);
            g2.fillRect((int)spriteX - w/2, (int)spriteY - h, w, h);
            g2.setColor(Color.BLACK);
            g2.fillRect((int)spriteX - w/2 - w/10, (int)spriteY - h/2, w/5, h/2);
            g2.fillRect((int)spriteX + w/2 - w/10, (int)spriteY - h/2, w/5, h/2);
        }

        drawPlayerCar(g2);

        // HUD
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.drawString((int)(speed/30) + " KMH", 40, 60);
        g2.setFont(new Font("Arial", Font.PLAIN, 15));
        g2.drawString("[Q] Quit", 40, 90);
    }

    private void drawQuad(Graphics2D g, Color c, double x1, double y1, double w1, double x2, double y2, double w2) {
        int[] x = {(int)(x1-w1), (int)(x2-w2), (int)(x2+w2), (int)(x1+w1)};
        int[] y = {(int)y1, (int)y2, (int)y2, (int)y1};
        g.setColor(c);
        g.fillPolygon(x, y, 4);
    }

    private void drawPlayerCar(Graphics2D g) {
        int cx = CENTER_X, cy = SCREEN_H - 20;
        if (Math.abs(playerX)>1.1 && speed > 500) cx += (Math.random()-0.5)*5;

        g.setColor(new Color(220, 20, 20));
        g.fillPolygon(new int[]{cx-40, cx+40, cx+20, cx-20}, new int[]{cy-30, cy-30, cy-90, cy-90}, 4);
        g.fillRect(cx-60, cy-30, 120, 20);
        g.setColor(Color.BLACK);
        g.fillRect(cx-90, cy-30, 40, 60);
        g.fillRect(cx+50, cy-30, 40, 60);
        g.setColor(Color.YELLOW);
        g.fillOval(cx-15, cy-70, 30, 30);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if(k==KeyEvent.VK_LEFT) keyLeft=true;
        if(k==KeyEvent.VK_RIGHT) keyRight=true;
        if(k==KeyEvent.VK_UP) keyUp=true;
        if(k==KeyEvent.VK_DOWN) keyDown=true;
        if(k==KeyEvent.VK_SPACE) keySpace=true;
        if(k==KeyEvent.VK_Q) System.exit(0);
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();
        if(k==KeyEvent.VK_LEFT) keyLeft=false;
        if(k==KeyEvent.VK_RIGHT) keyRight=false;
        if(k==KeyEvent.VK_UP) keyUp=false;
        if(k==KeyEvent.VK_DOWN) keyDown=false;
        if(k==KeyEvent.VK_SPACE) keySpace=false;
    }
    @Override public void keyTyped(KeyEvent e) {}

    // ==========================================================
    // SOUND ENGINE
    // ==========================================================
    public static class Sound {
        private static SourceDataLine line;
        public static float currentPitch = 0;
        public static float targetPitch = 0;
        public static void init() {
            new Thread(() -> {
                try {
                    AudioFormat fmt = new AudioFormat(44100, 8, 1, true, false);
                    line = AudioSystem.getSourceDataLine(fmt);
                    line.open(fmt, 4096);
                    line.start();
                    byte[] buf = new byte[4096];
                    while(true) {
                        currentPitch += (targetPitch - currentPitch) * 0.1;
                        for(int i=0; i<buf.length; i++) {
                            // Brown Noise (Rumble)
                            double noise = (Math.random() - 0.5) * 2;
                            // Pitch Shift
                            int skip = Math.max(1, (int)(10 - currentPitch * 5));
                            buf[i] = (i % skip == 0) ? (byte)(noise * 40) : 0;
                        }
                        line.write(buf, 0, buf.length);
                    }
                } catch(Exception e){}
            }).start();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Java Grand Prix (Perfect)");
        JavaGrandPrixPerfect game = new JavaGrandPrixPerfect();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}