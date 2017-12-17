package cz.uhk.pgrf1.c03.madr.uloha3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import cz.uhk.pgrf1.c03.madr.uloha3.model.Axis;
import cz.uhk.pgrf1.c03.madr.uloha3.model.Cube;
import cz.uhk.pgrf1.c03.madr.uloha3.model.Curve;
import cz.uhk.pgrf1.c03.madr.uloha3.model.Point;
import cz.uhk.pgrf1.c03.madr.uloha3.model.TetraHedron;
import cz.uhk.pgrf1.c03.madr.uloha3.raster.LineRasterizer;
import cz.uhk.pgrf1.c03.madr.uloha3.raster.TriangleRasterizer;
import cz.uhk.pgrf1.c03.madr.uloha3.render.Renderer;
import transforms.Camera;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Mat4OrthoRH;
import transforms.Mat4PerspRH;
import transforms.Mat4RotX;
import transforms.Mat4RotY;
import transforms.Mat4Scale;
import transforms.Mat4Transl;
import transforms.Vec3D;

/**
 * trida pro kresleni na platno: zobrazeni pixelu, ovladani mysi 
 * Ovladani kamery: AWSD, Mys(Kolecko - zoom, Leve tlacitko - rozhlizeni ) 
 * Ovladaci panel: V prvni casti vybirame, co se ma zobrazovat a menime projekci 
 * V druhe casti transformujeme objekt 
 * 1.rotace podle osy X o Pi/6 
 * 2.rotace podle osy X o -Pi/6 
 * 3.rotace podle osy Y o Pi/6 
 * 4.rotace podle osy Y p -Pi/6 
 * 5. a 6. zmena meritka a posun
 * 
 * @author PGRF FIM UHK
 * @version 2017
 */
public class Canvas {

	private JPanel panel;
	private BufferedImage img;

	// jake teleso vykresluju
	int mode = 0;

	// nastaveni kamery
	private Camera cam = new Camera(new Vec3D(-12, 1.5, 1), Math.toRadians(1.1), Math.toRadians(0), 1, true);
	private double CAMERA_SPEED = 0.5;
	private Point cameraPrevPoint;
	private Point cameraPoint;

	// projekce
	private Mat4PerspRH projectionPerMat = new Mat4PerspRH(Math.PI / 4, 1, 0.01, 45);
	private Mat4OrthoRH projectionOrRHmat = new Mat4OrthoRH(10, 10, 0.1, 230);
	private Mat4 projectionMat = projectionPerMat;

	// transformace
	private Mat4RotX rotateMatLeft = new Mat4RotX(Math.PI / 6);
	private Mat4RotX rotateMatRight = new Mat4RotX(-Math.PI / 6);
	private Mat4RotY rotateMatUP = new Mat4RotY(Math.PI / 6);
	private Mat4RotY rotateMatDown = new Mat4RotY(-Math.PI / 6);
	private Mat4Scale scaleMat = new Mat4Scale(0.5);
	private Mat4Scale rscaleMat = new Mat4Scale(1.5);
	private Mat4Transl translMat = new Mat4Transl(2, 0.5, 1);
	private Mat4Transl rTranslMat = new Mat4Transl(-2, -0.5, -1);
	private Mat4 model = new Mat4Identity();

	// telesa
	private Curve curve = new Curve();
	TetraHedron th = new TetraHedron();
	Cube cb = new Cube();
	Axis x = new Axis();

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

		// -----------------------------------------------------
		// nastaveni ovladaciho panelu
		JPanel pnl = new JPanel();
		frame.add(pnl, BorderLayout.NORTH);

		JLabel lbl = new JLabel("Draw: ");
		JButton cubeButton = new JButton("Cube");
		cubeButton.addActionListener(e -> setMode(0));

		JButton tetraHedronButton = new JButton("Tetrahedron");
		tetraHedronButton.addActionListener(e -> setMode(1));

		JButton curveButton = new JButton("Curve");
		curveButton.addActionListener(e -> setMode(2));

		JLabel lbl1 = new JLabel("||");

		JButton projectionButton = new JButton("Projection");
		projectionButton.addActionListener(e -> setMode(3));

		JLabel lbl2 = new JLabel("Trans: ");

		JButton rotateLeftButton = new JButton("\u21B6");

		rotateLeftButton.addActionListener(e -> setMode(4));
		JButton rotateRightButton = new JButton("\u21B7");
		rotateRightButton.addActionListener(e -> setMode(5));

		JButton rotateUpButton = new JButton("\u2191");
		rotateUpButton.addActionListener(e -> setMode(6));

		JButton rotateDownButton = new JButton("\u2193");
		rotateDownButton.addActionListener(e -> setMode(7));

		JButton transformButton = new JButton("\u21A1");
		transformButton.addActionListener(e -> setMode(8));

		JButton transformBackButton = new JButton("\u219F");
		transformBackButton.addActionListener(e -> setMode(9));

		JButton resetButton = new JButton("R");
		resetButton.addActionListener(e -> setMode(10));

		pnl.add(lbl);
		pnl.add(cubeButton);
		pnl.add(tetraHedronButton);
		pnl.add(curveButton);
		pnl.add(lbl1);
		pnl.add(projectionButton);
		pnl.add(lbl2);
		pnl.add(rotateLeftButton);
		pnl.add(rotateRightButton);
		pnl.add(rotateUpButton);
		pnl.add(rotateDownButton);
		pnl.add(transformButton);
		pnl.add(transformBackButton);
		pnl.add(resetButton);
		// -----------------------------------------------------
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.add(panel);

		panel.requestFocus();
		panel.requestFocusInWindow();

		// -----------------------------------------------------
		// ovladani kamery pomoci klaves
		panel.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
					cam = cam.up(CAMERA_SPEED);
					break;

				case KeyEvent.VK_S:
					cam = cam.down(CAMERA_SPEED);
					break;
				case KeyEvent.VK_D:
					cam = cam.right(CAMERA_SPEED);
					break;

				case KeyEvent.VK_A:
					cam = cam.left(CAMERA_SPEED);
					break;

				}

				render();
			}

		});

		// -----------------------------------------------------
		// ovladani kamery pomoci mysi
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON1)
					cameraPrevPoint = new Point((double) e.getX(), (double) e.getY());
				panel.repaint();
			}

		});

		panel.addMouseMotionListener(new MouseAdapter() {

			public void mouseDragged(MouseEvent e) {
				cameraPoint = new Point(e.getX(), e.getY());
				double dx = (cameraPoint.getY() - cameraPrevPoint.getY());
				double dy = (cameraPoint.getX() - cameraPrevPoint.getX());

				cameraPrevPoint = new Point(cameraPoint);
				cam = cam.addAzimuth((Math.PI / 360) * dx);
				cam = cam.addZenith((Math.PI / 360) * dy);
				render();

			}

		});

		panel.addMouseWheelListener(new MouseAdapter() {
			public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
				//
				if (e.getPreciseWheelRotation() > 0) {
					cam = cam.backward(CAMERA_SPEED);
				} else {
					cam = cam.forward(CAMERA_SPEED);
				}
				render();
			};
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
		render();
		panel.repaint();

	}

	// funkce pro nastaveni toho, co se ma vykreslovat
	private void setMode(int number) {

		switch (number) {
		case 0:
			mode = 0;
			render();
			break;
		case 1:
			mode = 1;
			render();
			break;
		case 2:
			mode = 2;
			render();
			break;
		case 3:

			if (projectionMat == projectionPerMat) {
				// prohozeni ukazatele
				projectionMat = projectionOrRHmat;

			} else {
				projectionMat = projectionPerMat;
			}
			render();
			break;

		case 4:
			model = model.mul(rotateMatRight);
			render();

			break;
		case 5:
			model = model.mul(rotateMatLeft);
			render();

			break;
		case 6:
			model = model.mul(rotateMatUP);
			render();
			break;
		case 7:
			model = model.mul(rotateMatDown);
			render();
			break;
		case 8:

			model = model.mul(scaleMat).mul(translMat);
			render();
			break;
		case 9:
			model = model.mul(rscaleMat).mul(rTranslMat);
			render();
			break;

		case 10:
			// reset Kamery, Projekce, Modelu
			model = new Mat4Identity();
			cam = new Camera(new Vec3D(-12, 1.5, 1), Math.toRadians(1.1), Math.toRadians(0), 1, true);
			projectionMat = projectionPerMat;
			render();

			break;
		}

		// aby znovu fungoval KeyAdapter
		panel.requestFocus();
		panel.requestFocusInWindow();

	}

	private void render() {
		clear();
		switch (mode) {
		case 0:
			renderAxis();
			renderCube();

			break;
		case 1:
			renderAxis();
			renderTetraHedron();
			break;
		case 2:
			renderAxis();
			renderCube();
			renderCurve();
			break;
		}
		panel.repaint();

	}

	public void renderTetraHedron() {

		LineRasterizer lren = new LineRasterizer(img);
		TriangleRasterizer tren = new TriangleRasterizer(img);
		Renderer ren = new Renderer(tren, lren, img);
		ren.setModel(model);
		ren.setProjection(projectionMat);
		ren.setView(cam.getViewMatrix());
		ren.render(th);
	}

	public void renderCube() {

		LineRasterizer lren = new LineRasterizer(img);
		TriangleRasterizer tren = new TriangleRasterizer(img);
		Renderer ren = new Renderer(tren, lren, img);
		ren.setModel(model);
		ren.setProjection(projectionMat);
		ren.setView(cam.getViewMatrix());
		ren.render(cb);

	}

	public void renderAxis() {
		LineRasterizer lren = new LineRasterizer(img);
		TriangleRasterizer tren = new TriangleRasterizer(img);
		Renderer ren = new Renderer(tren, lren, img);
		ren.setModel(new Mat4Identity());
		ren.setProjection(projectionMat);
		ren.setView(cam.getViewMatrix());
		ren.render(x);

	}

	public void renderCurve() {

		LineRasterizer lren = new LineRasterizer(img);
		TriangleRasterizer tren = new TriangleRasterizer(img);
		Renderer ren = new Renderer(tren, lren, img);
		ren.setModel(model);
		ren.setProjection(projectionMat);
		ren.setView(cam.getViewMatrix());
		ren.render(curve);

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
	}

}