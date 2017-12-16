package cz.uhk.pgrf1.c03.madr.uloha3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import cz.uhk.pgrf1.c03.madr.uloha3.model.Axis;
import cz.uhk.pgrf1.c03.madr.uloha3.model.Cube;
import cz.uhk.pgrf1.c03.madr.uloha3.model.TetraHedron;
import cz.uhk.pgrf1.c03.madr.uloha3.raster.LineRasterizer;
import cz.uhk.pgrf1.c03.madr.uloha3.raster.TriangleRasterizer;
import cz.uhk.pgrf1.c03.madr.uloha3.render.Renderer;
import transforms.Camera;
import transforms.Mat4Identity;
import transforms.Mat4PerspRH;
import transforms.Mat4ViewRH;
import transforms.Vec3D;

/**
 * trida pro kresleni na platno: zobrazeni pixelu, ovladani mysi
 * @author PGRF FIM UHK
 * @version 2017
 */
public class Canvas {

	private JPanel panel;
	private BufferedImage img;
	Camera cam = new Camera(new Vec3D(12,1.5, 1),  Math.toRadians(0),  Math.toRadians(0), 1, true);
	Mat4PerspRH projectionMat  = new Mat4PerspRH(Math.PI/4,1,0.01,45);
	Mat4Identity model = new Mat4Identity();
	

	public Canvas(int width, int height) {
		JFrame frame = new JFrame();

		frame.setLayout(new BorderLayout());
		frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				present(g);
			}
		};
		panel.setPreferredSize(new Dimension(width, height));

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);

		
		panel.requestFocus();
		panel.requestFocusInWindow();
		
		//--------------------------------------
		
		
	
		//================================
		
		
		
		
		panel.addKeyListener(new KeyAdapter() {
		
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("press");
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					cam=cam.up(0.5);
					break;

				case KeyEvent.VK_DOWN:
					cam=cam.down(0.5);
					break;
				case KeyEvent.VK_RIGHT:
					cam=cam.right(0.5);
					break;
					
				case KeyEvent.VK_LEFT:
					cam=cam.left(0.5);
					break;
					
				}
				renderTetraHedron();
			}
		
		});
		
		
		
		
		
		
		
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

			      clear();
				
				
				if (e.getButton() == MouseEvent.BUTTON1)

	
			      
					renderTetraHedron();
					
				panel.repaint();
					
				if (e.getButton() == MouseEvent.BUTTON2)
					img.setRGB(e.getX(), e.getY(), 0xff00ff);
				if (e.getButton() == MouseEvent.BUTTON3)
					img.setRGB(e.getX(), e.getY(), 0xffffff);
				panel.repaint();
			}
		});
	}

	public void clear() {
		Graphics gr = img.getGraphics();
		gr.setColor(new Color(0x2f2f2f));
		gr.fillRect(0, 0, img.getWidth(), img.getHeight());
	}

	public void present(Graphics graphics) {
		graphics.drawImage(img, 0, 0, null);
	}

	public void start() {
		clear();
		img.getGraphics().drawString("Use mouse buttons", 5, img.getHeight() - 5);
		renderTetraHedron();
		panel.repaint();
		
		
	}
	public void renderTetraHedron()
	{	clear();
		TetraHedron th = new TetraHedron();
		LineRasterizer lren = new LineRasterizer(img);
		TriangleRasterizer tren = new TriangleRasterizer(img);
		Renderer ren = new Renderer(tren,lren, img);
		ren.setModel(model);
		ren.setProjection(projectionMat);
		ren.setView(cam.getViewMatrix());
		ren.render(cb);
		panel.repaint();
	}
	public void renderCube()
	{
		clear();
		Cube cb = new Cube();
		LineRasterizer lren = new LineRasterizer(img);
		TriangleRasterizer tren = new TriangleRasterizer(img);
		Renderer ren = new Renderer(tren,lren, img);
		ren.setModel(model);
		ren.setProjection(projectionMat);
		ren.setView(cam.getViewMatrix());
		ren.render(cb);
		panel.repaint();
	}
	
	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
	}


}