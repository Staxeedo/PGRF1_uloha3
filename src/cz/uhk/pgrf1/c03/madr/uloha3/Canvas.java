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
import java.awt.peer.PanelPeer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import cz.uhk.pgrf1.c03.madr.uloha3.model.Axis;
import cz.uhk.pgrf1.c03.madr.uloha3.model.Cube;
import cz.uhk.pgrf1.c03.madr.uloha3.model.Point;
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
 * Uzivatelske rozhrani
 * 
 * @author PGRF FIM UHK
 * @version 2017
 */
public class Canvas {

	private JPanel panel;
	private BufferedImage img;
	int mode = 0;
	Camera cam = new Camera(new Vec3D(-12, 1.5, 1), Math.toRadians(1.1), Math.toRadians(0), 1, true);
	Mat4PerspRH projectionMat = new Mat4PerspRH(Math.PI / 4, 1, 0.01, 45);
	Mat4Identity model = new Mat4Identity();
	Point cameraPrevPoint;
	Point cameraPoint;

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
		JPanel pnl = new JPanel();
		frame.add(pnl, BorderLayout.NORTH);
		JLabel lbl = new JLabel("Draw: ");
		JButton cubeButton = new JButton("Cube");
		cubeButton.addActionListener(e -> setMode(0));
		cubeButton.setSelected(true);
		JButton tetraHedronButton = new JButton("Tetrahedron");
		tetraHedronButton.addActionListener(e -> setMode(1));
		JButton curveButton = new JButton("Curve");
		curveButton.addActionListener(e -> setMode(2));

		pnl.add(lbl);
		pnl.add(cubeButton);
		pnl.add(curveButton);
		pnl.add(tetraHedronButton);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.add(panel);

		// --------------------------------------------------------
		// --------------------------------------------------------

		// --------------------------------------------------------

		panel.requestFocus();
		panel.requestFocusInWindow();

		panel.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
					cam = cam.up(0.5);
					break;

				case KeyEvent.VK_S:
					cam = cam.down(0.5);
					break;
				case KeyEvent.VK_D:
					cam = cam.right(0.5);
					break;

				case KeyEvent.VK_A:
					cam = cam.left(0.5);
					break;

				}

				render();
			}

		});

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON1)
					cameraPrevPoint = new Point((double) e.getX(), (double) e.getY());

				// render();
				panel.repaint();
			}

		});

		panel.addMouseMotionListener(new MouseAdapter() {

			public void mouseDragged(MouseEvent e) {
				cameraPoint=new Point(e.getX(),e.getY());
				System.out.println("tady");
				//bohuzel se mi nepovedlo zprovoznit alg z prednasky, ale vsiml jsem si ze to funguju, kdyz to vynasobim malym cislem
				double dx = (cameraPoint.getY() - cameraPrevPoint.getY())*0.001;
				double dy = (cameraPoint.getX() - cameraPrevPoint.getX())*0.001;
				
				cameraPrevPoint = new Point(cameraPoint);
				cam=cam.addAzimuth((dx));
				cam=cam.addZenith((dy));
				render();
				
			}

		});
		
		
		panel.addMouseWheelListener(new MouseAdapter()
				{
						public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
							if(e.getPreciseWheelRotation()>0) {
								cam=cam.backward(0.1);
							}
							else
							{
								cam=cam.forward(0.1);
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
		img.getGraphics().drawString("Use mouse buttons", 5, img.getHeight() - 5);
		render();
		panel.repaint();

	}

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

			panel.repaint();
			break;
		case 1:
			renderAxis();
			renderTetraHedron();
			panel.repaint();
			break;
		case 2:
			break;
		}

	}

	public void renderTetraHedron() {

		TetraHedron th = new TetraHedron();
		LineRasterizer lren = new LineRasterizer(img);
		TriangleRasterizer tren = new TriangleRasterizer(img);
		Renderer ren = new Renderer(tren, lren, img);
		ren.setModel(model);
		ren.setProjection(projectionMat);
		ren.setView(cam.getViewMatrix());
		ren.render(th);
		panel.repaint();
	}

	public void renderCube() {
		Cube cb = new Cube();
		LineRasterizer lren = new LineRasterizer(img);
		TriangleRasterizer tren = new TriangleRasterizer(img);
		Renderer ren = new Renderer(tren, lren, img);
		ren.setModel(model);
		ren.setProjection(projectionMat);
		ren.setView(cam.getViewMatrix());
		ren.render(cb);

	}

	public void renderAxis() {
		Axis x = new Axis();
		LineRasterizer lren = new LineRasterizer(img);
		TriangleRasterizer tren = new TriangleRasterizer(img);
		Renderer ren = new Renderer(tren, lren, img);
		ren.setModel(model);
		ren.setProjection(projectionMat);
		ren.setView(cam.getViewMatrix());
		ren.render(x);

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
	}

}