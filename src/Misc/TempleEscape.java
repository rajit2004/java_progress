package misc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TempleEscape extends JPanel implements ActionListener, KeyListener {

    // --- 3D Engine Config ---
    private final int SCREEN_W = 600;
    private final int SCREEN_H = 800;
    private final double FOV = 400.0;
    private final double CAMERA_HEIGHT = 200.0;
    private final double HORIZON_Y = 200.0;
    private final double LANE_WIDTH = 160.0;

    // --- Game Logic ---
    private double gameSpeed = 12.0;
    private double score = 0;
    private boolean isGameOver = false;
    private double worldCurve = 0; // Current curvature of the path
    private double targetCurve = 0; // Target curvature (for smooth transitions)

    // --- Player State ---
    private int lane = 0;
    private double playerX = 0;
    private double playerY = 0;
    private double playerZ = 100;
    private double yVelocity = 0;
    private boolean isJumping = false;
    private boolean isSliding = false;
    private int slideTimer = 0;

    // --- The Monster ---
    private int monkeyDistance = 0; // 0 = Far, 1 = Close (Danger), 2 = Dead
    private int stumbleTimer = 0;   // Time until monkey falls back

    // --- Objects ---
    private ArrayList<GameObject> objects = new ArrayList<>();
    private ArrayList<Particle> particles = new ArrayList<>();
    private Timer timer;
    private Random rand = new Random();
    private int frameCount = 0;

    // ==========================================================
    // OBJECTS
    // ==========================================================
    // 0=FireTrap(Jump), 1=TreeLog(Slide), 2=BrokenPath(Pit), 3=Idol(Coin), 4=Root(Stumble)
    private static class GameObject implements Comparable<GameObject> {
        double x, y, z, width, height;
        int type;
        boolean collected = false;

        public GameObject(double x, double y, double z, int type) {
            this.x = x; this.y = y; this.z = z; this.type = type;

            if (type == 0) { // FIRE TRAP
                width = 140; height = 40;
            } else if (type == 1) { // TREE LOG (High)
                width = 160; height = 100; y = 90;
            } else if (type == 2) { // BROKEN PATH (Pit)
                width = 180; height = 10; y = -5; // Draws on floor
            } else if (type == 3) { // IDOL
                width = 40; height = 60; y = 20;
            } else if (type == 4) { // TREE ROOT (Small Stumble)
                width = 140; height = 20;
            }
        }

        @Override
        public int compareTo(GameObject o) { return Double.compare(o.z, this.z); }
    }

    private static class Particle {
        double x, y, z, vx, vy, vz;
        int life;
        Color color;
        public Particle(double x, double y, double z, Color c) {
            this.x = x; this.y = y; this.z = z; this.color = c;
            this.vx = (Math.random()-0.5)*10;
            this.vy = (Math.random()*10) + 5;
            this.life = 40;
        }
    }

    public TempleEscape() {
        setPreferredSize(new Dimension(SCREEN_W, SCREEN_H));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        timer = new Timer(16, this);
        timer.start();
    }

    // ==========================================================
    // LOGIC
    // ==========================================================
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isGameOver) { repaint(); return; }

        frameCount++;
        score += 0.2;
        gameSpeed += 0.005; // Slowly accelerate

        // Update Curve (Winding Path Effect)
        if (frameCount % 200 == 0) targetCurve = (rand.nextDouble() - 0.5) * 200;
        worldCurve += (targetCurve - worldCurve) * 0.01;

        updatePlayer();
        updateObjects();
        updateParticles();

        // Monster Logic
        if (monkeyDistance == 1) {
            stumbleTimer--;
            if (stumbleTimer <= 0) monkeyDistance = 0; // Monkey falls back
        }

        repaint();
    }

    private void updatePlayer() {
        // Lane Smoothing
        double targetX = lane * LANE_WIDTH;
        playerX += (targetX - playerX) * 0.1;

        // Jump Physics
        if (isJumping) {
            playerY += yVelocity;
            yVelocity -= 1.5; // Gravity
            if (playerY <= 0) {
                playerY = 0;
                isJumping = false;
                yVelocity = 0;
                spawnDust(playerX, 0, playerZ);
            }
        } else if (playerY > 0) {
            playerY -= 10; // Snap down if sliding off air
        }

        // Slide Logic
        if (isSliding) {
            slideTimer--;
            if (slideTimer <= 0) isSliding = false;
        }
    }

    private void updateObjects() {
        // Spawning
        if (frameCount % 40 == 0) spawnPattern();

        for (int i = 0; i < objects.size(); i++) {
            GameObject obj = objects.get(i);
            obj.z -= gameSpeed;

            // Collision Check
            if (!obj.collected && Math.abs(obj.z - playerZ) < 50) {
                if (checkCollision(obj)) handleCollision(obj);
            }

            if (obj.z < -100) { objects.remove(i); i--; }
        }
        Collections.sort(objects);
    }

    private void spawnPattern() {
        int r = rand.nextInt(100);
        int zStart = 3500;

        if (r < 20) { // Row of Idols
            for(int k=0; k<5; k++) objects.add(new GameObject(lane * LANE_WIDTH, 0, zStart + (k*80), 3));
        } else {
            // Random Obstacle
            int l = rand.nextInt(3) - 1;
            int type = rand.nextInt(5); // 0 to 4
            // Don't spawn pit in all lanes (impossible)
            if (type == 2) l = lane; // Pit spawns in front of you mostly
            objects.add(new GameObject(l * LANE_WIDTH, 0, zStart, type));
        }
    }

    private boolean checkCollision(GameObject obj) {
        if (Math.abs(obj.x - playerX) > 60) return false;

        // Hitbox Logic
        double pHeight = isSliding ? 40 : 110;

        if (obj.type == 2) { // PIT (Must be on ground)
            return (playerY < 10);
        }

        double oBot = obj.y;
        double oTop = obj.y + obj.height;
        double pTop = playerY + pHeight;

        return (playerY < oTop && pTop > oBot);
    }

    private void handleCollision(GameObject obj) {
        if (obj.type == 3) { // IDOL
            score += 50;
            obj.collected = true;
        } else if (obj.type == 4) { // ROOT (Stumble)
            obj.collected = true;
            triggerStumble();
        } else { // FATAL (Fire, Log, Pit)
            isGameOver = true;
            monkeyDistance = 2; // Monkey got you
        }
    }

    private void triggerStumble() {
        if (monkeyDistance == 0) {
            monkeyDistance = 1; // Monkey gets close
            stumbleTimer = 200; // Survive 200 frames to reset
            // Screen Shake Effect (Visual only)
        } else {
            isGameOver = true; // Monkey eats you
        }
    }

    private void spawnDust(double x, double y, double z) {
        for(int i=0; i<5; i++) particles.add(new Particle(x,y,z, new Color(139, 69, 19)));
    }

    private void updateParticles() {
        for (int i = 0; i < particles.size(); i++) {
            Particle p = particles.get(i);
            p.x += p.vx; p.y += p.vy; p.z -= gameSpeed;
            p.life--;
            if (p.life <= 0) { particles.remove(i); i--; }
        }
    }

    // ==========================================================
    // RENDERING
    // ==========================================================
    private Point project(double x, double y, double z) {
        if (z < -FOV + 10) return null;

        // CURVATURE MATH: Shift X based on Z depth squared
        double curveOffset = (z * z) * 0.0001 * worldCurve;
        double finalX = x + curveOffset;

        double scale = FOV / (FOV + z);
        int screenX = (int) (SCREEN_W / 2 + (finalX * scale));
        int screenY = (int) (HORIZON_Y + CAMERA_HEIGHT * scale - (y * scale));
        return new Point(screenX, screenY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Sky & Jungle Horizon
        g2.setPaint(new GradientPaint(0, 0, new Color(10, 15, 20), 0, (int)HORIZON_Y, new Color(20, 40, 30)));
        g2.fillRect(0, 0, SCREEN_W, (int)HORIZON_Y);

        // Ground (Swamp)
        g2.setColor(new Color(25, 30, 20));
        g2.fillRect(0, (int)HORIZON_Y, SCREEN_W, SCREEN_H);

        // Draw Path (With Curve)
        g2.setColor(new Color(100, 100, 90)); // Stone color
        // Draw center strip (simulating the continuous path)
        for (double z = 0; z < 4000; z+=50) {
            Point p1 = project(-LANE_WIDTH*1.5, 0, z);
            Point p2 = project(LANE_WIDTH*1.5, 0, z);
            Point p3 = project(LANE_WIDTH*1.5, 0, z+50);
            Point p4 = project(-LANE_WIDTH*1.5, 0, z+50);

            if (p1!=null && p4!=null) {
                int[] xs = {p1.x, p2.x, p3.x, p4.x};
                int[] ys = {p1.y, p2.y, p3.y, p4.y};
                g2.fillPolygon(xs, ys, 4);
            }
        }

        // Draw Objects
        for (GameObject obj : objects) drawObject(g2, obj);

        // Draw Player
        drawPlayer(g2);

        // Particles
        for(Particle p : particles) {
            Point pt = project(p.x, p.y, p.z);
            if(pt!=null) { g2.setColor(p.color); g2.fillRect(pt.x, pt.y, 4, 4); }
        }

        // HUD
        g2.setColor(Color.YELLOW);
        g2.setFont(new Font("Serif", Font.BOLD, 30));
        g2.drawString(String.valueOf((int)score), SCREEN_W - 100, 50);

        if (isGameOver) {
            g2.setColor(new Color(0,0,0,200));
            g2.fillRect(0,0,SCREEN_W, SCREEN_H);
            g2.setColor(Color.RED);
            g2.drawString("THE MONKEY GOT YOU!", SCREEN_W/2 - 150, SCREEN_H/2);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.drawString("[ENTER] to Run Again", SCREEN_W/2 - 100, SCREEN_H/2 + 50);
        }
    }

    private void drawObject(Graphics2D g, GameObject obj) {
        Point p = project(obj.x, obj.y, obj.z);
        if (p == null) return;
        double scale = FOV / (FOV + obj.z);
        int w = (int)(obj.width * scale);
        int h = (int)(obj.height * scale);
        int x = p.x - w/2;
        int y = p.y - h;

        if (obj.type == 0) { // FIRE TRAP
            g.setColor(Color.ORANGE);
            g.fillRect(x, y + h/2, w, h/2);
            // Flames
            int[] fx = {x, x+w/3, x+w/2, x+2*w/3, x+w};
            int[] fy = {y+h/2, y, y+h/4, y, y+h/2};
            g.setColor(Color.RED);
            g.fillPolygon(fx, fy, 5);
            drawLabel(g, "JUMP!", x, y, scale);

        } else if (obj.type == 1) { // TREE LOG
            g.setColor(new Color(101, 67, 33)); // Wood
            g.fillRoundRect(x, y, w, h/2, 10, 10);
            // Legs
            g.fillRect(x, y, w/4, h);
            g.fillRect(x + 3*w/4, y, w/4, h);
            drawLabel(g, "SLIDE!", x, y, scale);

        } else if (obj.type == 2) { // PIT
            g.setColor(Color.BLACK);
            g.fillRect(x, p.y, w, (int)(100*scale)); // Draw into ground

        } else if (obj.type == 3) { // IDOL
            g.setColor(Color.YELLOW);
            int[] px = {x+w/2, x, x+w};
            int[] py = {y, y+h, y+h};
            g.fillPolygon(px, py, 3);

        } else if (obj.type == 4) { // ROOT
            g.setColor(new Color(80, 50, 20));
            g.fillArc(x, p.y - h, w, h*2, 0, 180); // Rounded bump
        }
    }

    private void drawLabel(Graphics2D g, String txt, int x, int y, double scale) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, (int)(20*scale)));
        g.drawString(txt, x, y);
    }

    private void drawPlayer(Graphics2D g) {
        Point p = project(playerX, playerY, playerZ);
        if (p == null) return;
        double scale = FOV / (FOV + playerZ);
        int w = (int)(60 * scale);
        int h = (int)((isSliding ? 60 : 120) * scale);

        // Explorer Body
        g.setColor(new Color(210, 180, 140)); // Tan Shirt
        g.fillRect(p.x - w/2, p.y - h, w, h/2);
        g.setColor(new Color(100, 100, 80)); // Green Pants
        g.fillRect(p.x - w/2, p.y - h/2, w, h/2);

        // Hat
        g.setColor(new Color(100, 50, 0)); // Brown Fedora
        g.fillRect(p.x - w/2 - 5, p.y - h - 10, w + 10, 10);
        g.fillRect(p.x - w/4, p.y - h - 25, w/2, 15);

        // THE MONKEY (Draw behind player if close)
        if (monkeyDistance > 0) {
            int mw = (int)(140 * scale);
            int mh = (int)(160 * scale);
            int mx = p.x - mw/2;
            int my = p.y - h - 20; // Hovering behind

            g.setColor(new Color(20, 20, 20, 220)); // Shadow Monster
            g.fillOval(mx, my, mw, mh);

            // Eyes
            g.setColor(Color.RED);
            g.fillOval(mx + mw/4, my + mh/3, 15, 15);
            g.fillOval(mx + 3*mw/4 - 15, my + mh/3, 15, 15);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (isGameOver && k == KeyEvent.VK_ENTER) resetGame();

        if (k == KeyEvent.VK_LEFT && lane > -1) lane--;
        if (k == KeyEvent.VK_RIGHT && lane < 1) lane++;
        if (k == KeyEvent.VK_SPACE || k == KeyEvent.VK_UP) {
            if (!isJumping && !isSliding) { isJumping = true; yVelocity = 22; }
        }
        if (k == KeyEvent.VK_DOWN && !isSliding && !isJumping) {
            isSliding = true; slideTimer = 35;
        }
    }

    private void resetGame() {
        isGameOver = false; score = 0; gameSpeed = 12.0; monkeyDistance = 0;
        objects.clear(); lane = 0; playerY = 0;
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Temple Escape 3D");
        TempleEscape game = new TempleEscape();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}