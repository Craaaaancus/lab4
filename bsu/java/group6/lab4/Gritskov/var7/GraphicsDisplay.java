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
	protected void paintAxis(Graphics2D canvas) {
		canvas.setStroke(axisStroke);
		canvas.setColor(Color.BLACK);
		canvas.setPaint(Color.BLACK);
		canvas.setFont(axisFont);
		FontRenderContext context = canvas.getFontRenderContext();
		if (minX <= 0.0 && maxX >= 0.0) {
			canvas.draw(new Line2D.Double(xyToPoint(0, maxY), xyToPoint(0, minY)));
			GeneralPath arrow = new GeneralPath();
			Point2D.Double lineEnd = xyToPoint(0, maxY);
			arrow.moveTo(lineEnd.getX(), lineEnd.getY());
			arrow.lineTo(arrow.getCurrentPoint().getX()+5, arrow.getCurrentPoint().getY()+20);
			arrow.lineTo(arrow.getCurrentPoint().getX()-10, arrow.getCurrentPoint().getY());
			arrow.closePath();
			canvas.draw(arrow);
			canvas.fill(arrow);
			Rectangle2D bounds = axisFont.getStringBounds("y", context);
			Rectangle2D bounds1 = axisFont.getStringBounds("0", context);
			Rectangle2D bounds2 = axisFont.getStringBounds("1", context);
			Point2D.Double labelPos = xyToPoint(0, maxY);
			Point2D.Double labelZero = xyToPoint(0,0);
			Point2D.Double labelOne = xyToPoint(1,0);
			GeneralPath lineX = new GeneralPath();
			GeneralPath lineY = new GeneralPath();
			
			canvas.drawString("y", (float)labelPos.getX() + 10, (float)(labelPos.getY() - bounds.getY()));
			canvas.drawString("0", (float)labelZero.getX() - 20, (float)(labelZero.getY() - bounds1.getY()));
			
			canvas.drawString("1", (float)labelOne.getX(), (float)(labelOne.getY() - bounds2.getY()));
			lineX.moveTo((float)labelOne.getX() + 10,(float)labelOne.getY() - 5);
			lineX.lineTo(lineX.getCurrentPoint().getX(), lineX.getCurrentPoint().getY() + 10);
			canvas.draw(lineX);
			
			labelOne = xyToPoint(0,1);
			canvas.drawString("1", (float)(labelOne.getX()), (float)(labelOne.getY()));
			lineY.moveTo((float)labelOne.getX() - 5 ,(float)labelOne.getY() - 10);
			lineY.lineTo(lineY.getCurrentPoint().getX() + 10, lineY.getCurrentPoint().getY());
			canvas.draw(lineY);
		}
		if (minY <= 0.0 && maxY >= 0.0) {
			canvas.draw(new Line2D.Double(xyToPoint(minX, 0), xyToPoint(maxX, 0)));
			GeneralPath arrow = new GeneralPath();
			Point2D.Double lineEnd = xyToPoint(maxX, 0); 
			arrow.moveTo(lineEnd.getX(), lineEnd.getY());
			arrow.lineTo(arrow.getCurrentPoint().getX()-20, arrow.getCurrentPoint().getY()-5);
			arrow.lineTo(arrow.getCurrentPoint().getX(), arrow.getCurrentPoint().getY()+10);
			arrow.closePath();
			canvas.draw(arrow);
			canvas.fill(arrow);
			Rectangle2D bounds = axisFont.getStringBounds("x", context);
			Point2D.Double labelPos = xyToPoint(maxX, 0);
			canvas.drawString("x", (float)(labelPos.getX()-bounds.getWidth()-10), (float)(labelPos.getY() + bounds.getY()));
		}
		
	}
	
	protected void paintMarkers(Graphics2D canvas) {
		canvas.setStroke(markerStroke);
		
		for (Double[] point:graphicsData) {
			Rectangle2D.Double markerRect = new Rectangle2D.Double();
			Point2D.Double center = xyToPoint(point[0], point[1]);
			Point2D.Double corner = shiftPoint(center, 5.5, 5.5);
			markerRect.setFrameFromCenter(center, corner);
			GeneralPath markerLines = new GeneralPath();
			markerLines.moveTo(center.getX()-5.5, center.getY()+5.5);
			markerLines.lineTo(markerLines.getCurrentPoint().getX()+11, markerLines.getCurrentPoint().getY()-11);
			markerLines.lineTo(markerLines.getCurrentPoint().getX(), markerLines.getCurrentPoint().getY()+11);
			markerLines.lineTo(markerLines.getCurrentPoint().getX()-11, markerLines.getCurrentPoint().getY()-11);
			
			String str = String.valueOf(point[1]);
			boolean inAscendingOrder = true;
			Integer firstNum = new Integer(str.charAt(0));
			for (int i = 1; i < str.length(); i++) {
				if (str.charAt(i) == '.') {
					continue;
				}
				if (firstNum > new Integer(str.charAt(i)) ) {
					inAscendingOrder = false;
					break;
				}
				firstNum = new Integer(str.charAt(i));
			}
			
			if (inAscendingOrder) {
				canvas.setColor(Color.BLUE);
				canvas.draw(markerRect);
				canvas.draw(markerLines);
			}
			else {
				canvas.setColor(Color.RED);
				canvas.draw(markerRect);
				canvas.draw(markerLines);
			}	
		}
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
