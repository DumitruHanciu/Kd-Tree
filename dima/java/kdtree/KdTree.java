package dima.java.kdtree;

/* *****************************************************************************
 *  Name: Dumitru Hanciu
 *  Date: 07.01.2019
 *  Description: dima.java.kdtree.KdTree
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private Node r;
    private int s;

    // construct an empty set of pointK
    public KdTree() {
        r = null;
        s = 0;
    }

    /**
     * The Node structure which will contain following informations:
     */
    private class Node {
        private Point2D p;
        private RectHV d;
        private Node l;
        private Node r;
        private boolean a;

        Node(Point2D p, RectHV d, Node l, Node r, boolean a) {
            this.p = p;
            this.d = d;
            this.l = l;
            this.r = r;
            this.a = a;
        }
    }

    private int compare(Point2D p, Node n, boolean a) {
        return a ? Double.compare(p.x(), n.p.x()) : Double.compare(p.y(), n.p.y());
    }

    // is the set empty?
    public boolean isEmpty() {
        return s == 0;
    }

    // number of pointK in the set
    public int size() {
        return s;
    }

    private int size(Node n) {
        return n == null ? 0 : 1 + size(n.l) + size(n.r);
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        r = put(p, r, 0, 0, 1, 1, true);
    }

    private Node put(Point2D p, Node n, double xmin, double ymin, double xmax,
                     double ymax, boolean a) {
        if (n == null) {
            s++;
            RectHV rect = new RectHV(xmin, ymin, xmax, ymax);
            return new Node(p, rect, null, null, a);
        }
        else if (compare(p, n, a) < 0) {
            double newXMax = n.a ? n.p.x() : xmax;
            double newYMax = n.a ? ymax : n.p.y();
            n.l = put(p, n.l, xmin, ymin, newXMax, newYMax, !n.a);
        }
        else if (compare(p, n, a) > 0 || compare(p, n, !n.a) != 0) {
            double newXMin = n.a ? n.p.x() : xmin;
            double newYMin = n.a ? ymin : n.p.y();
            n.r = put(p, n.r, newXMin, newYMin, xmax, ymax, !n.a);
        }
        return n;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return contains(p, r);
    }

    private boolean contains(Point2D p, Node n) {
        return n != null &&
                (n.p.equals(p) || (compare(p, n, n.a) < 0 ? contains(p, n.l) : contains(p, n.r)));
    }

    // draw all pointK to standard draw
    public void draw() {
        draw(r);
    }

    private void draw(Node n) {
        if (n == null) return;
        draw(n.l);
        draw(n.r);
        n.p.draw();
    }

    // all pointK that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.IllegalArgumentException();
        return add(r, new Stack<>(), rect);
    }

    private Stack<Point2D> add(Node n, Stack<Point2D> s, RectHV r) {
        if (n == null) return s;
        if (n.l != null && r.intersects(n.l.d)) add(n.l, s, r);
        if (n.r != null && r.intersects(n.r.d)) add(n.r, s, r);
        if (r.contains(n.p)) s.push(n.p);
        return s;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        return r == null ? null : nearest(r, r.p, p);
    }

    private Point2D nearest(Node r, Point2D nr, Point2D p) {
        if (r == null) return nr;
        nr = p.distanceSquaredTo(nr) > p.distanceSquaredTo(r.p) ? r.p : nr;
        int cmp = compare(p, r, r.a);
        if (cmp <= 0) {
            nr = nearest(r.l, nr, p);
            if (r.r != null && p.distanceSquaredTo(nr) >= r.r.d.distanceSquaredTo(p))
                nr = nearest(r.r, nr, p);
        }
        else {
            nr = nearest(r.r, nr, p);
            if (r.l != null) if (p.distanceSquaredTo(nr) >= r.l.d.distanceSquaredTo(p))
                nr = nearest(r.l, nr, p);
        }
        return nr;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        StdOut.print();
    }
}











































































