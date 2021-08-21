import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.Arrays.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

public class MothersDay extends JApplet implements MouseListener {
	
	static GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	BufferedImage screen, text;
	static Random random = new Random();
	
	BufferedImage heart;
	Font font = new Font("Courier", Font.PLAIN, 12);
	
	ArrayList<Double> xpos = new ArrayList<Double>();
	ArrayList<Double> ypos = new ArrayList<Double>();
	ArrayList<Double> angle = new ArrayList<Double>();
	ArrayList<Double> xvel = new ArrayList<Double>();
	ArrayList<Double> yvel = new ArrayList<Double>();
	ArrayList<Double> rvel = new ArrayList<Double>();
	ArrayList<Integer> size = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Happy Birthday! - Click me");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JApplet app = new MothersDay();
		app.setFocusable(true);
		frame.add("Center", app);
		frame.pack();
		app.init();
	}
	
	public void init() {
		setLayout(new BorderLayout());
		addMouseListener(this);
		
		screen = config.createCompatibleImage(getWidth(), getHeight(), Transparency.TRANSLUCENT);
		text = config.createCompatibleImage(getWidth(), getHeight(), Transparency.TRANSLUCENT);
		heart = loadImage("heart.png");
		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent ae) {
				for (int i=xpos.size()-1; i>-1; i--) {
					xpos.set(i, xpos.get(i) + xvel.get(i));
					ypos.set(i, ypos.get(i) + yvel.get(i));
					angle.set(i, angle.get(i) + rvel.get(i));
					if (xpos.get(i) < 0 || xpos.get(i) >= getWidth()) {
						xvel.set(i, -xvel.get(i));
					}
					if (ypos.get(i) < 0 || ypos.get(i) >= getHeight()) {
						yvel.set(i, -yvel.get(i));
					}
					xvel.set(i, xvel.get(i) * 0.999);
					yvel.set(i, yvel.get(i) + 1);
					if (ypos.get(i) > getHeight()+heart.getHeight()*size.get(i)+Math.abs(yvel.get(i))) {
						xpos.remove(i);
						ypos.remove(i);
						angle.remove(i);
						xvel.remove(i);
						yvel.remove(i);
						rvel.remove(i);
						size.remove(i);
					}
				}
				if (random.nextInt(20) == 0) {
					Graphics2D sdraw = text.createGraphics();
					sdraw.setColor(Color.BLACK);
					sdraw.fillRect(0, 0, getWidth(), getHeight());
					sdraw.setColor(new Color(0, 102, 0));
					sdraw.setFont(font);
					int iter1 = (int)(getHeight()/sdraw.getFontMetrics().getHeight());
					int iter2 = (int)(getWidth()/sdraw.getFontMetrics().charWidth('X'))+1;
					char[] letters = new char[iter1*iter2];
					for (int i=0; i<letters.length; i++) {
						if ((int)(i/iter2) == (double)i/iter2) {
							letters[i] = '\n';
						}
						else {
							if (random.nextInt(2500) != 0) {
								letters[i] = (char)(random.nextInt(2)+48);
							}
							else {
								letters[i] = (char)9829;
							}
						}
					}
					String background = new String(letters).substring(1);
					drawString(sdraw, background, 0, 0);
				}
				repaint();
			}
		};
		javax.swing.Timer timer = new javax.swing.Timer(20, action);
		timer.start();
	}
	
	public void paint(Graphics g) {
		if (screen == null) return;
		Graphics2D sdraw = screen.createGraphics();
		sdraw.setColor(Color.BLACK);
		sdraw.fillRect(0, 0, getWidth(), getHeight());
		sdraw.drawImage(text,
			0, 0, getWidth(), getHeight(),
			0, 0, getWidth(), getHeight(),
			null);
		for (int i=0; i<xpos.size(); i++) {
			sdraw.drawImage(heart,
				(int)(double)xpos.get(i), (int)(double)ypos.get(i), (int)(xpos.get(i)+heart.getWidth()*size.get(i)), (int)(ypos.get(i)+heart.getHeight()*size.get(i)),
				0, 0, heart.getWidth(), heart.getHeight(),
				null);
		}
		
		g.drawImage(screen,
			0, 0, getWidth(), getHeight(),
			0, 0, getWidth(), getHeight(),
			null);
	}
	
	public void mousePressed(MouseEvent e) {
		xpos.add((double)e.getX());
		ypos.add((double)e.getY());
		angle.add(0.0);
		xvel.add(random.nextDouble()*16-8);
		yvel.add(-random.nextDouble()*8);
		rvel.add(random.nextDouble()*2-1);
		size.add(random.nextInt(3)+1);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(800, 600);
	}
	
	public static BufferedImage loadImage(String path) {
		InputStream url = MothersDay.class.getResourceAsStream(path);
		BufferedImage image = null;
		try {
			image = ImageIO.read(url);
		}
		catch (Exception e) {
			System.out.println("[WARNING] Failed to load image '" + path + "'.");
		}
		return image;
	}
	
	public void drawString(Graphics g, String text, int x, int y) {
		for (String line : text.split("\n")) {
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		//
	}
	
	public void mouseReleased(MouseEvent e) {
		//
	}
	
	public void mouseEntered(MouseEvent e) {
		//
	}
	
	public void mouseExited(MouseEvent e) {
		//
	}
}
