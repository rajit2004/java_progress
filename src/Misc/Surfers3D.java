package misc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Surfers3D extends JPanel implements ActionListener, KeyListener {

    // --- 3D Engine Config ---
    private final int SCREEN_W = 600;
    private final int SCREEN_H = 800;
    private final double FOV = 400.0; // Field of View
    private final double CAMERA_HEIGHT = 250.0;
    private final double HORIZON_Y = 200.0;
    private final double LANE_WIDTH_3D = 180.0;

    // --- Physics ---
    private final double GRAVITY = 1.5;
    private final double JUMP_FORCE = 25.0;
    private final double SPEED_INITIAL = 15.0;

    // --- Game State ---
    private double gameSpeed = SPEED_INITIAL;
    private double score = 0;
    private boolean isGameOver = false;

    // --- Player State ---
    private int lane = 0; // -1 (Left), 0 (Center), 1 (Right)
    private double playerX = 0;
    private double playerY = 0;
    private double playerZ = 100; // Fixed depth
    private double yVelocity = 0;
    private boolean isJumping = false;
    private boolean isRolling = false;
    private int rollTimer = 0;
    private boolean hasJetpack = false;
    private int jetpackTimer = 0;

    // --- World Objects ---
    private ArrayList<GameObject> objects = new ArrayList<>();
    private ArrayList<Particle> particles = new ArrayList<>();

    // --- System ---
    private Timer timer;
    private Random rand = new Random();
    private int frameCount = 0;

    // ==========================================================
    // 3D OBJECT CLASS
    // ==========================================================
    // Types: 0=Wall(Dodge), 1=Barrier(Jump), 2=HighGate(Roll), 3=Coin, 4=Jetpack
    private static class GameObject implements Comparable<GameObject> {
        double x, y, z, width, height;
        int type;
        boolean collected = false;
        Color color;

        public GameObject(double x, double y, double z, int type) {
            this.x = x; this.y = y; this.z = z; this.type = type;

            if (type == 0) { // RED WALL -> DODGE
                width = 120; height = 150; color = new Color(220, 40, 40);
            } else if (type == 1) { // ORANGE BARRIER -> JUMP
                width = 140; height = 60; color = new Color(255, 140, 0);
            } else if (type == 2) { // BLUE GATE -> ROLL
                width = 140; height = 140; y = 90; // Floats in air
                color = new Color(50, 100, 255);
            } else if (type == 3) { // COIN
                width = 40; height = 40; y = 20; color = Color.YELLOW;
            } else if (type == 4) { // JETPACK
                width = 30; height = 50; y = 20; color = Color.MAGENTA;
            }
        }

        @Override
        public int compareTo(GameObject o) {
            // Sort by Z (Depth) for Painter's Algorithm
            return Double.compare(o.z, this.z);
        }
    }

    private static class Particle {
        double x, y, z, vx, vy, vz;
        int life;
        Color color;
        public Particle(double x, double y, double z, Color c) {
            this.x = x; this.y = y; this.z = z; this.color = c;
            this.vx = (Math.random()-0.5)*15;
            this.vy = (Math.random()-0.5)*15 + 15;
            this.vz = (Math.random()-0.5)*10;
            this.life = 30;
        }
    }

    public Surfers3D() {
        setPreferredSize(new Dimension(SCREEN_W, SCREEN_H));
        setBackground(new Color(20, 20, 40));
        setFocusable(true);
        addKeyListener(this);
        timer = new Timer(16, this); // ~60 FPS
        timer.start();
    }

    // ==========================================================
    // GAME LOGIC
    // ==========================================================
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isGameOver) { repaint(); return; }

        frameCount++;
        score += 0.5;

        updatePlayer();
        updateObjects();
        updateParticles();

        repaint();
    }

    private void updatePlayer() {
        // Smooth Lane Switching
        double targetX = lane * LANE_WIDTH_3D;
        playerX += (targetX - playerX) * 0.15;

        // Physics
        if (hasJetpack) {
            double targetY = 350; // Fly High
            playerY += (targetY - playerY) * 0.1;
            jetpackTimer--;
            if (jetpackTimer <= 0) hasJetpack = false;
            if (frameCount % 3 == 0) particles.add(new Particle(playerX, playerY, playerZ, Color.ORANGE));
        } else {
            if (isJumping) {
                playerY += yVelocity;
                yVelocity -= GRAVITY;
                if (playerY <= 0) {
                    playerY = 0;
                    isJumping = false;
                    yVelocity = 0;
                }
            } else {
                if (playerY > 0) playerY -= 15; // Quick fall if jetpack ends
            }
        }

        if (isRolling) {
            rollTimer--;
            if (rollTimer <= 0) isRolling = false;
        }
    }

    private void updateObjects() {
        if (frameCount % 600 == 0) gameSpeed += 1; // Increase speed

        // --- Spawner ---
        if (frameCount % 35 == 0) {
            spawnPattern();
        }

        // --- Mover ---
        for (int i = 0; i < objects.size(); i++) {
            GameObject obj = objects.get(i);
            obj.z -= gameSpeed;

            // Collision Check (Only when close)
            if (!obj.collected && Math.abs(obj.z - playerZ) < 40) {
                if (checkCollision(obj)) handleCollision(obj);
            }

            if (obj.z < -100) {
                objects.remove(i);
                i--;
            }
        }
        Collections.sort(objects); // Sort for 3D drawing
    }

    private void spawnPattern() {
        int r = rand.nextInt(100);
        int zStart = 3500;

        // 25% Row of Coins
        if (r < 25) {
            int l = rand.nextInt(3) - 1;
            for(int k=0; k<5; k++) objects.add(new GameObject(l * LANE_WIDTH_3D, 0, zStart + (k*80), 3));
            return;
        }

        // Obstacles
        boolean[] laneOccupied = new boolean[3];
        int obsCount = rand.nextInt(2) + 1;

        for (int i=0; i<obsCount; i++) {
            int l = rand.nextInt(3);
            if (laneOccupied[l]) continue;
            laneOccupied[l] = true;

            int realLane = l - 1;
            // 0=Dodge, 1=Jump, 2=Roll
            int type = rand.nextInt(3);

            // 5% Chance for Jetpack
            if (rand.nextInt(100) < 5) {
                objects.add(new GameObject(realLane * LANE_WIDTH_3D, 0, zStart, 4));
            } else {
                objects.add(new GameObject(realLane * LANE_WIDTH_3D, 0, zStart, type));
            }
        }
    }

    private boolean checkCollision(GameObject obj) {
        if (Math.abs(obj.x - playerX) > 60) return false; // Lane Check

        // Y-Axis Check
        double pHeight = isRolling ? 50 : 100;
        double pBot = playerY;
        double pTop = playerY + pHeight;

        double oBot = obj.y;
        double oTop = obj.y + obj.height;

        return (pBot < oTop && pTop > oBot); // Intersection
    }

    private void handleCollision(GameObject obj) {
        if (obj.type == 3) { // Coin
            score += 100;
            obj.collected = true;
        } else if (obj.type == 4) { // Jetpack
            hasJetpack = true;
            jetpackTimer = 300;
            obj.collected = true;
        } else { // Crash
            if (hasJetpack) return;
            isGameOver = true;
            spawnExplosion(playerX, playerY, Color.RED);
        }
    }

    private void spawnExplosion(double x, double y, Color c) {
        for(int i=0; i<30; i++) particles.add(new Particle(x, y, 100, c));
    }

    private void updateParticles() {
        for (int i = 0; i < particles.size(); i++) {
            Particle p = particles.get(i);
            p.x += p.vx; p.y += p.vy; p.z += p.vz;
            p.vy -= 1.0;
            p.life--;
            if (p.life <= 0) { particles.remove(i); i--; }
        }
        particles.sort((p1, p2) -> Double.compare(p2.z, p1.z));
    }

    // ==========================================================
    // RENDERING
    // ==========================================================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Sky & Floor
        g2.setPaint(new GradientPaint(0, 0, new Color(20, 20, 60), 0, (int)HORIZON_Y, new Color(150, 80, 200)));
        g2.fillRect(0, 0, SCREEN_W, (int)HORIZON_Y);
        g2.setColor(new Color(40, 40, 40));
        g2.fillRect(0, (int)HORIZON_Y, SCREEN_W, SCREEN_H);

        // Tracks
        g2.setColor(new Color(120, 120, 120));
        draw3DLine(g2, -LANE_WIDTH_3D * 1.5, 0, 0, -LANE_WIDTH_3D * 1.5, 0, 4000);
        draw3DLine(g2, -LANE_WIDTH_3D * 0.5, 0, 0, -LANE_WIDTH_3D * 0.5, 0, 4000);
        draw3DLine(g2, LANE_WIDTH_3D * 0.5, 0, 0, LANE_WIDTH_3D * 0.5, 0, 4000);
        draw3DLine(g2, LANE_WIDTH_3D * 1.5, 0, 0, LANE_WIDTH_3D * 1.5, 0, 4000);

        // Objects
        for (GameObject obj : objects) draw3DObject(g2, obj);

        // Player
        drawPlayer(g2);

        // Particles
        for (Particle p : particles) {
            Point pt = project(p.x, p.y, p.z);
            if (pt != null) {
                int s = (int)(10 * (FOV/(FOV+p.z)));
                g2.setColor(p.color);
                g2.fillOval(pt.x, pt.y, s, s);
            }
        }

        // HUD
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 22));
        g2.drawString("Score: " + (int)score, 20, 40);
        if (hasJetpack) {
            g2.setColor(Color.MAGENTA);
            g2.drawString("JETPACK MODE!", 20, 70);
        }
        if (isGameOver) {
            g2.setColor(new Color(0,0,0,180));
            g2.fillRect(0,0,SCREEN_W, SCREEN_H);
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            g2.drawString("CRASHED!", SCREEN_W/2-100, SCREEN_H/2);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.drawString("[ENTER] to Restart", SCREEN_W/2-90, SCREEN_H/2+50);
        }
    }

    private Point project(double x, double y, double z) {
        if (z < -FOV + 10) return null;
        double scale = FOV / (FOV + z);
        int screenX = (int) (SCREEN_W / 2 + (x * scale));
        int screenY = (int) (HORIZON_Y + CAMERA_HEIGHT * scale - (y * scale));
        return new Point(screenX, screenY);
    }

    private void draw3DLine(Graphics2D g, double x1, double y1, double z1, double x2, double y2, double z2) {
        Point p1 = project(x1, y1, z1);
        Point p2 = project(x2, y2, z2);
        if (p1 != null && p2 != null) g.drawLine(p1.x, p1.y, p2.x, p2.y);
    }

    // --- ENHANCED OBJECT DRAWING (With Text) ---
    private void draw3DObject(Graphics2D g, GameObject obj) {
        Point p = project(obj.x, obj.y, obj.z);
        if (p == null) return;

        double scale = FOV / (FOV + obj.z);
        int w = (int) (obj.width * scale);
        int h = (int) (obj.height * scale);
        int drawX = p.x - w / 2;
        int drawY = p.y - h;
        int fontSize = Math.max(10, (int)(25 * scale));

        if (obj.type == 2) { // --- HIGH GATE (ROLL) ---
            // Legs
            g.setColor(new Color(30, 30, 150));
            int legW = Math.max(2, w/8);
            g.fillRect(p.x - w/2, drawY + h, legW, (int)(90*scale));
            g.fillRect(p.x + w/2 - legW, drawY + h, legW, (int)(90*scale));
            // Box
            g.setColor(obj.color);
            g.fillRect(drawX, drawY, w, h);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, fontSize));
            g.drawString("ROLL", drawX + w/2 - (fontSize), drawY + h/2 + fontSize/3);
        }
        else if (obj.type == 3) { // --- COIN ---
            g.setColor(Color.YELLOW);
            g.fillOval(drawX, drawY, w, h);
            g.setColor(Color.ORANGE);
            g.drawOval(drawX, drawY, w, h);
        }
        else if (obj.type == 4) { // --- JETPACK ---
            g.setColor(Color.MAGENTA);
            g.fillOval(drawX, drawY, w, h);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, fontSize));
            g.drawString("FLY", drawX, drawY);
        }
        else { // --- WALL (DODGE) or BARRIER (JUMP) ---
            g.setColor(obj.color);
            g.fillRect(drawX, drawY, w, h);

            // Side 3D Effect
            g.setColor(obj.color.darker());
            int depth = (int)(20 * scale);
            g.fillPolygon(new int[]{drawX+w, drawX+w+depth, drawX+w+depth, drawX+w},
                    new int[]{drawY, drawY-depth/2, drawY+h-depth/2, drawY+h}, 4);

            // Text Label
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, fontSize));

            if (obj.type == 0) { // WALL
                g.drawString("DODGE", drawX + 5, drawY + h/2);
            } else { // BARRIER
                g.drawString("JUMP", drawX + w/2 - (fontSize), drawY + h/2 + fontSize/3);
            }
        }
    }

    private void drawPlayer(Graphics2D g) {
        Point p = project(playerX, playerY, playerZ);
        if (p == null) return;

        double scale = FOV / (FOV + playerZ);
        double height = isRolling ? 60 : 120;
        int w = (int) (60 * scale);
        int h = (int) (height * scale);

        // Shadow
        g.setColor(new Color(0,0,0,100));
        Point floorP = project(playerX, 0, playerZ);
        int sw = (int)(w * (1.0 - playerY/400.0));
        if(floorP != null) g.fillOval(floorP.x - sw/2, floorP.y - sw/4, sw, sw/2);

        // Body
        int drawX = p.x - w / 2;
        int drawY = p.y - h;

        if (hasJetpack) {
            g.setColor(Color.ORANGE);
            g.fillOval(drawX, drawY+h, w, 30); // Flame
        }

        g.setColor(Color.CYAN);
        g.fillRoundRect(drawX, drawY, w, h, 15, 15);
        g.setColor(Color.BLACK); // Eyes
        g.fillRect(drawX + w/4, drawY + h/5, w/2, h/6);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (isGameOver && k == KeyEvent.VK_ENTER) resetGame();

        if (k == KeyEvent.VK_J) { hasJetpack = true; jetpackTimer = 500; }
        if (k == KeyEvent.VK_LEFT && lane > -1) lane--;
        if (k == KeyEvent.VK_RIGHT && lane < 1) lane++;
        if ((k == KeyEvent.VK_SPACE || k == KeyEvent.VK_UP) && !isJumping && !isRolling) {
            isJumping = true; yVelocity = JUMP_FORCE;
        }
        if (k == KeyEvent.VK_DOWN && !isRolling && !isJumping) {
            isRolling = true; rollTimer = 40;
        }
    }

    private void resetGame() {
        isGameOver = false; score = 0; gameSpeed = SPEED_INITIAL;
        objects.clear(); particles.clear();
        lane = 0; playerX = 0; playerY = 0; hasJetpack = false;
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Surfers 3D Pro");
        Surfers3D game = new Surfers3D();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}