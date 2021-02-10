/*
    File: Complex.java
	
    Author: Daniel Marcovecchio 
    Author URI: https://github.com/BlackHat0001

    Description: Class for complex numbers. Performs calculations on complex numbers.

    Version: 1.1.0 Release
*/

package mandelbrot;

class Complex
{
    double r;
    double i;
    
    public Complex(final double r, final double i) {
        this.r = r;
        this.i = i;
    }
    
    public Complex add(final Complex a, final Complex b) {
        return new Complex(a.getR() + b.getR(), a.getI() + b.getI());
    }
    
    public Complex sqr(final Complex a) {
        double tempR = a.getR();
        double tempI = a.getI();
        tempR = Math.pow(tempR, 2.0) + -1.0 * Math.pow(a.getI(), 2.0);
        tempI = 2.0 * (a.getR() * a.getI());
        return new Complex(tempR, tempI);
    }
    
    public double getI() {
        return this.i;
    }
    
    public void setI(final double i) {
        this.i = i;
    }
    
    public double getR() {
        return this.r;
    }
    
    public void setR(final double r) {
        this.r = r;
    }
}