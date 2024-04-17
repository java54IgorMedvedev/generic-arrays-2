package telran.shapes.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import telran.shapes.*;
import telran.shapes.exceptions.ShapeAlreadyExistsException;
import telran.shapes.exceptions.ShapeNotFoundException;

class PageTest {
    private Page page;
    private Shape shape1;
    private Shape shape2;
    private Canvas canvas;

    @BeforeEach
    void setUp() {
        page = new Page();
        shape1 = new Rectangle(1, 10, 20);  
        shape2 = new Square(2, 15);         
        canvas = new Canvas(100);           
    }

    @Test
    void testAddShapeShape() {
        page.addShape(shape1);
        Exception exception = assertThrows(ShapeAlreadyExistsException.class, () -> page.addShape(shape1));
    }

    @Test
    void testAddShapeLongArrayShape() {
        page.addShape(canvas);
        Long[] canvasIds = {100L};
        page.addShape(canvasIds, shape1);
        assertThrows(ShapeAlreadyExistsException.class, () -> page.addShape(canvasIds, shape1));
    }

    @Test
    void testRemoveShapeLong() {
        Page page = new Page();
        Shape shape1 = new Square(1, 10);

        page.addShape(shape1);
        assertTrue(page.contains(shape1));

        assertThrows(ShapeNotFoundException.class, () -> page.removeShape(shape1.getId()));
    }


    @Test
    void testRemoveShapeLongArrayLong() {
        page.addShape(canvas);
        Long[] canvasIds = {100L};
        page.addShape(canvasIds, shape1);
        Shape removedShape = page.removeShape(canvasIds, shape1.getId());
        assertEquals(shape1, removedShape);
        assertThrows(ShapeNotFoundException.class, () -> page.removeShape(canvasIds, shape1.getId()));
    }

    @Test
    void testIterator() {
        page.addShape(shape1);
        page.addShape(shape2);
        Iterator<Shape> it = page.iterator();
        assertTrue(it.hasNext());
        assertEquals(shape1, it.next());
        assertTrue(it.hasNext());
        assertEquals(shape2, it.next());
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }
}
