package bsu.java.group6.lab4.Gritskov.var7;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class GraphicsDisplay extends JPanel{
	
	private Double[][] graphicsData;
	
	private boolean showAxis = true;
	private boolean showMarkers = false;
	
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	
	private double scale;
	
	private BasicStroke graphicsStroke;
	private BasicStroke axisStroke;
	private BasicStroke markerStroke;
	
	private Font axisFont;
	
	public void showGraphics(Double [][] graphicsData) {
		this.graphicsData = graphicsData;
		repaint();
	}
	
	public void setShowAxis(boolean showAxis) {
		this.showAxis = showAxis;
		repaint();
	}
	
	public void setShowMarkers(boolean showMarkers) {
		this.showMarkers = showMarkers;
		repaint();
	}
	
	protected Point2D.Double xyToPoint(double x, double y) {
		double deltaX = x - minX;
		double deltaY = maxY - y;
		return new Point2D.Double(deltaX*scale, deltaY*scale);
	}
	
	protected Point2D.Double shiftPoint(Point2D.Double src, double deltaX, double deltaY) {
			Point2D.Double dest = new Point2D.Double();
			dest.setLocation(src.getX() + deltaX, src.getY() + deltaY);
			return dest;
	}
	
	protected void paintGraphics(Graphics2D canvas) {
		canvas.setStroke(graphicsStroke);
		canvas.setColor(Color.RED);
		GeneralPath graphics = new GeneralPath();
		for (int i = 0; i < graphicsData.length; i++) {
			Point2D.Double point = xyToPoint(graphicsData[i][0], graphicsData[i][1]);
			if (i > 0) {
				graphics.lineTo(point.getX(), point.getY());
			}
			else {
				graphics.moveTo(point.getX(), point.getY());
			}
		}
		canvas.draw(graphics);
	}
	
	public GraphicsDisplay() {
		setBackground(Color.WHITE);
		float[] dash = {10.0f, 10.0f};
		graphicsStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f, dash, 0.0f);
		axisStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);
		markerStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);
		axisFont = new Font("Serif", Font.BOLD, 36);
		
	}

}
