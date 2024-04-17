package telran.shapes;

import java.util.Iterator;
import java.util.NoSuchElementException;

import telran.shapes.exceptions.NoCanvasException;
import telran.shapes.exceptions.ShapeAlreadyExistsException;
import telran.shapes.exceptions.ShapeNotFoundException;
import telran.util.Arrays;

public class Page implements Iterable<Shape> {
    private Shape[] shapes = new Shape[0];

    public void addShape(Shape shape) {
        if (Arrays.indexOf(shapes, shape) > -1) {
            throw new ShapeAlreadyExistsException(shape.getId());
        }
        shapes = Arrays.add(shapes, shape);
    }

    public void addShape(Long[] canvasIds, Shape shape) {
        Canvas canvas = getCanvas(canvasIds);
        if (Arrays.indexOf(canvas.shapes, shape) > -1) {
            throw new ShapeAlreadyExistsException(shape.getId());
        }
        canvas.shapes = Arrays.add(canvas.shapes, shape);
    }

    private Canvas getCanvas(Long[] canvasIds) {
        Canvas canvas = (Canvas) shapes[Arrays.indexOf(shapes, new Canvas(canvasIds[0]))];
        for (int i = 1; i < canvasIds.length; i++) {
            canvas = (Canvas) canvas.shapes[Arrays.indexOf(canvas.shapes, new Canvas(canvasIds[i]))];
        }
        return canvas;
    }

    public Shape removeShape(long id) {
        int index = Arrays.indexOf(shapes, new Shape(id) {
            public int square() { return 0; }
            public int perimeter() { return 0; }
        });
        if (index == -1) {
            throw new ShapeNotFoundException(id);
        }
        Shape removedShape = shapes[index];
        shapes = Arrays.removeIf(shapes, s -> s.getId() == id);
        return removedShape;
    }

    public Shape removeShape(Long[] canvasIds, long id) {
        Canvas canvas = getCanvas(canvasIds);
        Shape foundShape = findShapeOnCanvas(canvas, id);
        if (foundShape == null) {
            throw new ShapeNotFoundException(id);
        }
        canvas.shapes = Arrays.removeIf(canvas.shapes, s -> s.getId() == id);
        return foundShape;
    }

    private Shape findShapeOnCanvas(Canvas canvas, long id) {
        Shape foundShape = null;
        for (Shape shape : canvas.shapes) {
            if (shape.getId() == id) {
                foundShape = shape;
                break;
            }
        }
        return foundShape;
    }

    public boolean contains(Shape shape) {
        for (Shape s : shapes) {
            if (s.equals(shape)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Shape> iterator() {
        return new Iterator<Shape>() {
            private int currentIndex = 0;

            public boolean hasNext() {
                return currentIndex < shapes.length;
            }

            public Shape next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return shapes[currentIndex++];
            }
        };
    }
}
