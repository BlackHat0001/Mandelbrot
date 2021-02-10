/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandelbrot;

import java.io.IOException;
import java.awt.*;  
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author danma
*/

public class Mandelbrot extends Canvas  {

    /**
     * @param args the command line arguments
     */
    
    static int xValue = 10;
    static int yValue = 10;
    static boolean buddhabrot = false;
    static double zoomLevel = 1;
    
    static int Xconvert;
    static int Iconvert;
    static String pos[][];
    static int rateToInfinity[][];
    static int reso = 1;
    static double step = 0.0025;
    static double Xstart = -2.0;
    static double Xstop = 1.0;
    static double Istart = -1.0;
    static double Istop = 1.0;
    static double comparitor = 2.0;
    
    static double coordXAxis = Math.abs((0 - Xstart));
    static double coordIAxis = Math.abs((0 - Istart));
    static Color c = Color.black;
    static JFrame jfx = new JFrame();
    static JTextField label1 = new JTextField();
    static JPanel jf = new JPanel() {
                final RenderingHints QUALITY_RENDER = new RenderingHints(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
                final RenderingHints ANTIALIASING = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                protected void paintComponent(Graphics g) {
                    Graphics2D gx = (Graphics2D) g;
                    gx.addRenderingHints(ANTIALIASING);
                    gx.addRenderingHints(QUALITY_RENDER);
                      for (yValue = 0; yValue < Iconvert; yValue++) {
                            for (xValue = 0; xValue < Xconvert; xValue++) {
                                double rti = rateToInfinity[yValue][xValue];
                                double rtl = (rti / reso);
                                
                                double C = rtl * 255;
                                int B = (int) ((C%256) * 255);
                                int G =  (int)(((C/256) % 256) * 10000);
                                int R =  (int)((C/(256^2)) * 10000);
                                
                                if (R > 255) {
                                    R = 255;
                                } 
                                if(G > 255) {
                                    G = 255;
                                } 
                                if(B > 255) {
                                    B = 255;
                                }
                                c = new Color(R, G, B);
                                //System.out.println(R + " " + G  + " " + B);
                                if(rti >= reso) {
                                    c = new Color(0, 0, 0);
                                } else if(buddhabrot && pos[yValue][xValue] == "XX") {
                                    c = new Color(255, 255, 255);
                                }
                                if (pos[yValue][xValue] == "XX") {
                                    gx.setColor(c);
                                    
                                    gx.fillRect(xValue, yValue, 1, 1);
                                } else {
                                    gx.setColor(c);
                                    gx.fillRect(xValue, yValue, 1, 1);
                                }
                                gx.setColor(new Color(0, 0, 0));
                                gx.fillRect((int)(Xstart/step), (int) ((Math.abs(Istop - Istart)/2)/step), 1, (int) (Math.abs(Istop - Istart)/step));
                                
                            }
                        }
                      
                      
                      
                        
                }
        };
    
    public static void main(String[] args) throws IOException {
        comparitor = 100;
        reso = 10;
        JPanel mousepanel = generateSet(false, 0.0, 0.0, 1);
        jfx.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jf.updateUI();
        jfx.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jf.updateUI();
        mousepanel.addMouseListener(new MouseAdapter() {
            @Override
            //When mouse is pressed
            public void mousePressed(MouseEvent e) {
                
                Point xy = mousepanel.getMousePosition();
                zoomLevel = zoomLevel * 1/2;
                System.out.println(xy.x + " " + xy.y);
                try {
                    generateSet(true, xy.y, xy.x, zoomLevel);
                } catch (IOException ex) {
                    Logger.getLogger(Mandelbrot.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }

            @Override
            //When mouse is released
            public void mouseReleased(MouseEvent e) {
                //Mouse held is set to false

            }
        });
        
        
    }
    
    public static JPanel generateSet(boolean target, double xToTarget, double yToTarget, double zoomLevel) throws IOException {
        long startTime = System.currentTimeMillis();
        System.out.println(" x: " +coordXAxis + " i: " + coordIAxis);
        
        xToTarget = (xToTarget * step);
                yToTarget = (yToTarget * step);

                if (target == true) {
                    xToTarget = (coordXAxis - xToTarget)*-1;
                    yToTarget = (coordIAxis - yToTarget)*-1;
                    double xDiff = Math.abs(Xstart - Xstop);
                    double yDiff = Math.abs(Istart - Istop);
                    xDiff = xDiff*(zoomLevel/2);
                    yDiff = yDiff*(zoomLevel/2);
                    Xstart = xToTarget - xDiff;
                    Xstop = xDiff + xToTarget;
                    Istart =  yToTarget - yDiff;
                    Istop = yDiff + yToTarget;
                    step = step*zoomLevel;
                    coordXAxis += xToTarget;
                    coordIAxis += xToTarget;
                    System.out.println(xToTarget + " " + yToTarget + " " + Istart + " " + Istop);
                    System.out.println();
                    Xconvert = (int) ((Xstop - Xstart) / step);
                    Iconvert = (int) ((Istop - Istart) / step);
                } else {
                    Xconvert = (int) ((Xstop - Xstart) / step);
                    Iconvert = (int) ((Istop - Istart) / step);
                }
        //Xconvert = (int) ((Xstop - Xstart) / step);
        //Iconvert = (int) ((Istop - Istart) / step);
        jfx.setVisible(true);
        
        jf.setVisible(true);
        jf.setSize(Xconvert, Iconvert);
        jfx.setSize(Xconvert + 1, Iconvert + 1);
        jf.setMinimumSize(new Dimension(Xconvert, Iconvert));
        jf.setPreferredSize(new Dimension(Xconvert, Iconvert));
        jfx.add(jf);
        jfx.pack();
        
        System.out.println();
        pos = new String[Iconvert + 1][Xconvert + 1];
        rateToInfinity = new int[Iconvert + 1][Xconvert + 1];
        System.out.println(Xconvert + " " + Iconvert);
        int totalLoop = 0;
        Complex n = new Complex(0.1, 0.1);
        int xCount = 0;
        int iCount = 0;
        for (double x = Xstart; x < Xstop; x=x+step) {
            iCount = 0;
            for (double i = Istart; i < Istop; i=i+step) {
                n = new Complex(0, 0);
                
                
                Complex c = new Complex(x, i);
                int passes = 0;
                
                boolean breakLoop = false;
                int ma = 0;
                if(buddhabrot == false) {
                while (breakLoop == false) {
                    n = n.sqr(n);
                    
                    //System.out.println(n.getR() + " " + n.getI() + "i");
                    n = n.add(n, c);
                    if (Double.compare((Math.abs(n.getR())), comparitor) > 0 || Double.compare((Math.abs(n.getI())), comparitor) > 0) {
                        breakLoop = true;
                    } else {
                        passes = passes + 1;
                    }
                    ma = (int) Math.min(ma, Math.abs(n.getR()));
                    if (passes >= reso) {
                        breakLoop = true;
                    }
                    totalLoop = totalLoop + 1;
                    }
                }
                //System.out.println(passes);
                rateToInfinity[iCount][xCount] = ma;
            if (passes >= reso) {
                pos[iCount][xCount] = "XX";
                
            } else {
               pos[iCount][xCount] = "  ";

            }
            if(buddhabrot) {
                    boolean pointBuddha = IsBuddhabrotPoint(c, 1, reso);
                    
                    if (pointBuddha) {
                        pos[iCount][xCount] = "XX";
                    } else {
                        pos[iCount][xCount] = "  ";
                    } 
               }
                iCount = iCount + 1;
                //System.out.println(pos[xCount][iCount]);
            }
            //System.out.println("XCount: " + xCount + " ICount: " + iCount);
            
            xCount += 1;
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
        
        
//        StringBuilder str = new StringBuilder();
//        BufferedImage off_Image =
//            new BufferedImage(Xconvert + 1, Iconvert + 1,
//                              BufferedImage.TYPE_INT_ARGB);
//
//          
//        for (yValue = 0; yValue < Iconvert; yValue++) {
//            for (xValue = 0; xValue < Xconvert; xValue++) {
//                str.append(pos[yValue][xValue]);
//                double rti = rateToInfinity[yValue][xValue];
//                                double rtl = (rti / reso);
//                                
//                                double C = rtl * 255;
//                                int B = (int) ((C%256) * 255);
//                                int G =  (int)(((C/256) % 256) * 10000);
//                                int R =  (int)((C/(256^2)) * 10000);
//                                
//                                if (R > 255) {
//                                    R = 255;
//                                } 
//                                if(G > 255) {
//                                    G = 255;
//                                } 
//                                if(B > 255) {
//                                    B = 255;
//                                }
//                                //System.out.println(R + " " + G  + " " + B);
//                                c = new Color(R, G, B);
//                                if (rti >= reso) {
//                                    c = new Color(0, 0, 0);
//                                }
//                                if (pos[yValue][xValue] == "XX") {
//
//                                    off_Image.setRGB(xValue, yValue, c.getRGB());
//                                } else {
//                                    off_Image.setRGB(xValue, yValue, c.getRGB());
//                                }
//            }
//            
//            
//        }
        //File file = new File("mybrot.jpg");
        //ImageIO.write(off_Image, "jpg", file);
        
        
            return jf; 
    }
    
    public static boolean IsBuddhabrotPoint(Complex c, int minLimit, int maxLimit)
        {
            Complex z = new Complex(0, 0);

            for (int i = 0; i < maxLimit; i++)
            {
                z = z.sqr(z).add(z, c);

                // check if the point has escaped the circle of radius 2
                if (z.Magnitude(z) * z.Magnitude(z) > 4)
                {
                    // Filter by the minimum iteration limit too!
                    // Points that escape too fast are 'noisey'
                    return i >= minLimit;
                    
                }
            }

            // Point never escaped, so we think it's in the Mandelbrot set
            return false;
        }
    
}

class Complex {
    
    double r,i;

    public Complex(double r, double i) {
        this.r = r;
        this.i = i;
    }
    
    public Complex add(Complex a, Complex b) {
        return new Complex(a.getR() + b.getR(), a.getI() + b.getI());
    }
    
    public Complex sqr(Complex a) {
        double tempR = a.getR();
        double tempI = a.getI();
        tempR = Math.pow(tempR, 2.0) + (-1 * (Math.pow(a.getI(), 2.0)));
        tempI = 2*(a.getR() * a.getI() * -1);
        return new Complex(tempR, tempI);
    }
    
    public int choose(int a, int b) {
       return (int) (fact(a) / (fact(b)*fact((a - b))));
    }
    
    public long fact(int n) {
        long fact = 1;
        for (int i = 2; i <= n; i++) {
            fact = fact * i;
        }
        return fact;
    }
    
    public Complex sqrPower(Complex a, int power) {
        double r = a.getR();
        double i = a.getI();
        double binomealr = 0;
        double binomeali = 0;
        binomealr += Math.pow(r, power);
        for (int j = 0; j < power; j++) {
            binomeali += choose(power, j) * Math.pow(r, power - j) * Math.pow(i, j);
        }
        binomeali += Math.pow(i, power);
        return new Complex(binomealr, binomeali);
    }
    
    public double Magnitude(Complex a) {
        return Math.sqrt((a.getR()*a.getR()) + (a.getI()*a.getI()));
    }
    
    public double getI() {
        return i;
    }

    public void setI(double i) {
        this.i = i;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }
    
    
    
}
