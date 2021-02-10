/*
    File: Mandelbrot.java
	
    Author: Daniel Marcovecchio 
    Author URI: https://github.com/BlackHat0001

    Description: The calculation and display class for the program

    Version: 1.1.0 Release
*/

//Sorry for the lack of comments

package mandelbrot;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.Dimension;
import java.io.IOException;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Canvas;

/**
*
* @author danma
*/
public class Mandelbrot extends Canvas {
    static int xValue = 10;
    static int yValue = 10;
    static double zoomLevel = 1.0;
    static int Xconvert;
    static int Iconvert;
    static String[][] pos;
    static int[][] rateToInfinity;
    static int reso;
    static double step = 0.0025;
    static double Xstart = -2.0;
    static double Xstop = 1.0;
    static double Istart = -1.0;
    static double Istop = 1.0;
    static double coordXAxis = Math.abs(0.0 - Xstart);
    static double coordIAxis = Math.abs(0.0 - Istart);
    
    static Color c = Color.black;
    static JFrame jfx = new JFrame();
    static JTextField label1 = new JTextField();
    
    static JPanel jf = jf = new JPanel() {
             RenderingHints QUALITY_RENDER = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
             RenderingHints ANTIALIASING = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            @Override
            protected void paintComponent( Graphics g) {
                 Graphics2D gx = (Graphics2D)g;
                gx.addRenderingHints(this.ANTIALIASING);
                gx.addRenderingHints(this.QUALITY_RENDER);
                yValue = 0;
                while (yValue < Iconvert) {
                    xValue = 0;
                    while (xValue < Xconvert) {
                         double rti = rateToInfinity[yValue][xValue];
                         double rtl = rti / reso;
                         double C = rtl * 255.0;
                        int B = (int)(C % 256.0 * 255.0);
                        int G = (int)(C / 256.0 % 256.0 * 10000.0);
                        int R = (int)(C / 258.0 * 10000.0);
                        if (R > 255) {
                            R = 255;
                        }
                        if (G > 255) {
                            G = 255;
                        }
                        if (B > 255) {
                            B = 255;
                        }
                        c = new Color(R, G, B);
                        if (rti >= reso) {
                            c = new Color(0, 0, 0);
                        }
                        if (pos[yValue][xValue] == "XX") {
                            gx.setColor(c);
                            gx.fillRect(xValue, yValue, 1, 1);
                        }
                        else {
                            gx.setColor(c);
                            gx.fillRect(xValue, yValue, 1, 1);
                        }
                        ++xValue;
                    }
                    ++yValue;
                }
            }
        };;
    
    public static void main( String[] args) throws IOException {
         JPanel mousepanel = generateSet(false, 0.0, 0.0, 0.0);
        jfx.setDefaultCloseOperation(3);
        jf.updateUI();
        mousepanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed( MouseEvent e) {
                 Point xy = mousepanel.getMousePosition();
                zoomLevel = zoomLevel * 1.0 / 2.0;
                System.out.println(xy.x + " " + xy.y);
            }
            
            @Override
            public void mouseReleased( MouseEvent e) {
            }
        });
    }
    
    public static JPanel generateSet( boolean target, double xToTarget, double yToTarget,  double zoomLevel) throws IOException {
         long startTime = System.currentTimeMillis();
        System.out.println(" x: " + coordXAxis + " i: " + coordIAxis);
        reso = 2000;
        Xconvert = (int)((Xstop - Xstart) / step);
        Iconvert = (int)((Istop - Istart) / step);
        jfx.setVisible(true);
        jf.setVisible(true);
        jf.setSize(Xconvert, Iconvert);
        jfx.setSize(Xconvert + 1, Iconvert + 1);
        jf.setMinimumSize(new Dimension(Xconvert, Iconvert));
        jf.setPreferredSize(new Dimension(Xconvert, Iconvert));
        jfx.add(jf);
        jfx.pack();
        xToTarget = Xstart + xToTarget * step;
        yToTarget = Istart + yToTarget * step;
        if (target) {}
        System.out.println();
        pos = new String[Iconvert + 1][Xconvert + 1];
        rateToInfinity = new int[Iconvert + 1][Xconvert + 1];
        System.out.println(Xconvert + " " + Iconvert);
        int totalLoop = 0;
        Complex n = new Complex(0.1, 0.1);
        int xCount = 0;
        int iCount = 0;
        
        for (double x = Xstart; x < Xstop; x += step) {
            iCount = 0;
            for (double i = Istart; i < Istop; i += step) {
                
                
                n = new Complex(0.0, 0.0);
                 Complex c = new Complex(x, i);
                int passes = 0;
                boolean breakLoop = false;
                
                while (!breakLoop) {
                    n = n.sqr(n);
                    n = n.add(n, c);
                    if (Double.compare(n.getR(), 2.0) > 0 || Double.compare(n.getI(), 2.0) > 0 || Double.compare(n.getR(), -2.0) < 0 || Double.compare(n.getI(), -2.0) < 0) {
                        breakLoop = true;
                    }
                    else {
                        ++passes;
                    }
                    if (passes >= reso) {
                        breakLoop = true;
                    }
                    ++totalLoop;
                }
                
                
                if (passes >= reso) {
                    pos[iCount][xCount] = "XX";
                }
                else {
                    pos[iCount][xCount] = "  ";
                }
                
                rateToInfinity[iCount][xCount] = passes;
                ++iCount;
            }
            ++xCount;
        }
         long endTime = System.currentTimeMillis();
        System.out.println("Size: " + Xconvert * Iconvert);
        System.out.println("Iterations: " + reso);
        System.out.println("Number times looped: " + totalLoop);
        System.out.println("Completed In: " + (endTime - startTime) + "ms");
        jf.removeAll();
        
        label1.setText("Size: " + Xconvert * Iconvert + "\n | Iterations: " + reso + "\n | Number times looped:  " + totalLoop + "\n | Completed In: " + (endTime - startTime) + "ms");
        label1.setLocation(5, 5);
        jf.add(label1);
        return jf;
    }
    
    
    
    
    
}
