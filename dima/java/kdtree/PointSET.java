package dima.java.kdtree;

/* *****************************************************************************
 *  Name: Dumitru Hanciu
 *  Description: dima.java.kdtree.PointSET
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> pointS;

    // construct an empty set of points
    public PointSET() {
        pointS = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointS.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointS.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        pointS.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return pointS.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : pointS) p.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        Stack<Point2D> rectPoints = new Stack<>();
        for (Point2D p : pointS) {
            if (rect.contains(p)) {
                rectPoints.push(p);
            }
        }
        return rectPoints;
    }

    public Point2D nearest(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        Point2D nearest = null;
        for (Point2D p : pointS) {
            if (nearest == null || point.distanceSquaredTo(p) < point.distanceSquaredTo(nearest)) {
                nearest = p;
            }
        }
        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
}
