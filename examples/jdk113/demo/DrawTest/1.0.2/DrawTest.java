/*
 * @(#)DrawTest.java	1.1 97/03/20
 *
 * Copyright (c) 1994-1996 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

import java.awt.*;
import java.applet.*;

import java.util.Vector;

public class DrawTest extends Applet {
    public void init() {
	setLayout(new BorderLayout());
	DrawPanel dp = new DrawPanel();
	add("Center", dp);
	add("South",new DrawControls(dp));
    }

    public boolean handleEvent(Event e) {
	switch (e.id) {
	  case Event.WINDOW_DESTROY:
	    System.exit(0);
	    return true;
	  default:
	    return false;
	}
    }

    public static void main(String args[]) {
	Frame f = new Frame("DrawTest");
	DrawTest drawTest = new DrawTest();
	drawTest.init();
	drawTest.start();

	f.add("Center", drawTest);
	f.resize(300, 300);
	f.show();
    }
}

class DrawPanel extends Panel {
    public static final int LINES = 0;
    public static final int POINTS = 1;
    int	   mode = LINES;
    Vector lines = new Vector();
    Vector colors = new Vector();
    int x1,y1;
    int x2,y2;
    int xl, yl;

    public DrawPanel() {
	setBackground(Color.white);
    }

    public void setDrawMode(int mode) {
	switch (mode) {
	  case LINES:
	  case POINTS:
	    this.mode = mode;
	    break;
	  default:
	    throw new IllegalArgumentException();
	}
    }

    public boolean handleEvent(Event e) {
	switch (e.id) {
	  case Event.MOUSE_DOWN:
	    switch (mode) {
	      case LINES:
		x1 = e.x;
		y1 = e.y;
		x2 = -1;
		break;
	      case POINTS:
	      default:
		colors.addElement(getForeground());
		lines.addElement(new Rectangle(e.x, e.y, -1, -1));
		x1 = e.x;
		y1 = e.y;
		repaint();
		break;
	    }
	    return true;
	  case Event.MOUSE_UP:
	    switch (mode) {
	      case LINES:
		colors.addElement(getForeground());
		lines.addElement(new Rectangle(x1, y1, e.x, e.y));
		x2 = xl = -1;
		break;
	      case POINTS:
	      default:
		break;
	    }
	    repaint();
	    return true;
	  case Event.MOUSE_DRAG:
	    switch (mode) {
	      case LINES:
		xl = x2;
		yl = y2;
		x2 = e.x;
		y2 = e.y;
		break;
	      case POINTS:
	      default:
		colors.addElement(getForeground());
		lines.addElement(new Rectangle(x1, y1, e.x, e.y));
		x1 = e.x;
		y1 = e.y;
		break;
	    }
	    repaint();
	    return true;
	  case Event.WINDOW_DESTROY:
	    System.exit(0);
	    return true;
	  default:
	    return false;
	}
    }

    public void paint(Graphics g) {
	int np = lines.size();

	/* draw the current lines */
	g.setColor(getForeground());
	g.setPaintMode();
	for (int i=0; i < np; i++) {
	    Rectangle p = (Rectangle)lines.elementAt(i);
	    g.setColor((Color)colors.elementAt(i));
	    if (p.width != -1) {
		g.drawLine(p.x, p.y, p.width, p.height);
	    } else {
		g.drawLine(p.x, p.y, p.x, p.y);
	    }
	}
	if (mode == LINES) {
	    g.setXORMode(getBackground());
	    if (xl != -1) {
		/* erase the last line. */
		g.drawLine(x1, y1, xl, yl);
	    }
	    g.setColor(getForeground());
	    g.setPaintMode();
	    if (x2 != -1) {
		g.drawLine(x1, y1, x2, y2);
	    }
	}
    }
}


class DrawControls extends Panel {
    DrawPanel target;

    public DrawControls(DrawPanel target) {
	this.target = target;
	setLayout(new FlowLayout());
	setBackground(Color.lightGray);
	target.setForeground(Color.red);
	CheckboxGroup group = new CheckboxGroup();
	Checkbox b;
	add(b = new Checkbox(null, group, false));
	b.setBackground(Color.red);
	add(b = new Checkbox(null, group, false));
	b.setBackground(Color.green);
	add(b = new Checkbox(null, group, false));
	b.setBackground(Color.blue);
	add(b = new Checkbox(null, group, false));
	b.setBackground(Color.pink);
	add(b = new Checkbox(null, group, false));
	b.setBackground(Color.orange);
	add(b = new Checkbox(null, group, true));
	b.setBackground(Color.black);
	target.setForeground(b.getForeground());
	Choice shapes = new Choice();
	shapes.addItem("Lines");
	shapes.addItem("Points");
	shapes.setBackground(Color.lightGray);
	add(shapes);
    }

    public void paint(Graphics g) {
	Rectangle r = bounds();

	g.setColor(Color.lightGray);
	g.draw3DRect(0, 0, r.width, r.height, false);
    }

    public boolean action(Event e, Object arg) {
	if (e.target instanceof Checkbox) {
	    target.setForeground(((Component)e.target).getBackground());
	} else if (e.target instanceof Choice) {
	    String choice = (String)arg;

	    if (choice.equals("Lines")) {
		target.setDrawMode(DrawPanel.LINES);
	    } else if (choice.equals("Points")) {
		target.setDrawMode(DrawPanel.POINTS);
	    }
	}
	return true;
    }
}
	


    
