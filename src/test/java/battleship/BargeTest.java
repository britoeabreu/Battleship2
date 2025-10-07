package battleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Test class for the Barge class.
 * Author: ${user.name}
 * Date: ${current_date}
 * Time: ${current_time}
 * Cyclomatic Complexity for each method:
 * - Constructor: 1
 * - getSize: 1
 * - stillFloating: 2
 * - getPositions: 1
 * - shoot: 2
 * - occupies: 2
 * - tooCloseTo (IShip): 2
 * - tooCloseTo (IPosition): 2
 * - getTopMostPos: 1
 * - getBottomMostPos: 1
 * - getLeftMostPos: 1
 * - getRightMostPos: 1
 */
public class BargeTest {

    private Barge barge;

    @BeforeEach
    void setUp() {
        barge = new Barge(Compass.NORTH, new Position(5, 5));
    }

    @AfterEach
    void tearDown() {
        barge = null;
    }

    /**
     * Test for the constructor.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testConstructor() {
        assertNotNull(barge, "Error: Barge instance should not be null.");
        assertEquals("Barca", barge.getCategory(), "Error: Barge category should be 'Barca'.");
        assertEquals(Compass.NORTH, barge.getBearing(), "Error: Barge bearing is incorrect.");
        assertEquals(1, barge.getSize(), "Error: Barge size should be 1.");
        assertEquals(1, barge.getPositions().size(), "Error: Barge should have exactly one position.");
        assertEquals(new Position(5, 5), barge.getPositions().get(0), "Error: Barge position is incorrect.");
    }

    /**
     * Test for the getSize method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testGetSize() {
        assertEquals(1, barge.getSize(), "Error: Barge size should be 1.");
    }

    /**
     * Test for the stillFloating method (position intact).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testStillFloating1() {
        assertTrue(barge.stillFloating(), "Error: Barge should still be floating.");
    }

    /**
     * Test for the stillFloating method (position hit).
     */
    @Test
    void testStillFloating2() {
        barge.getPositions().get(0).shoot();
        assertFalse(barge.stillFloating(), "Error: Barge should not float after being hit.");
    }

    /**
     * Test for the shoot method (valid position).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testShoot1() {
        barge.shoot(new Position(5, 5));
        assertTrue(barge.getPositions().get(0).isHit(), "Error: The position should be hit.");
    }

    /**
     * Test for the shoot method (invalid position).
     */
    @Test
    void testShoot2() {
        barge.shoot(new Position(1, 1)); // Valid position but unused
        assertFalse(barge.getPositions().get(0).isHit(), "Error: Shot should not affect the barge for invalid positions.");
    }

    /**
     * Test for the occupies method (occupied position).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testOccupies1() {
        assertTrue(barge.occupies(new Position(5, 5)), "Error: Barge should occupy position (5, 5).");
    }

    /**
     * Test for the occupies method (non-occupied position).
     */
    @Test
    void testOccupies2() {
        assertFalse(barge.occupies(new Position(1, 1)), "Error: Barge should not occupy position (1, 1).");
    }

    /**
     * Test for tooCloseTo with another Barge (close proximity).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testTooCloseToShip1() {
        Barge nearbyBarge = new Barge(Compass.NORTH, new Position(5, 6));
        assertTrue(barge.tooCloseTo(nearbyBarge), "Error: Barges should be too close.");
    }

    /**
     * Test for tooCloseTo with another Barge (no proximity).
     */
    @Test
    void testTooCloseToShip2() {
        Barge distantBarge = new Barge(Compass.NORTH, new Position(10, 10));
        assertFalse(barge.tooCloseTo(distantBarge), "Error: Barges should not be too close.");
    }

    /**
     * Test for tooCloseTo with an IPosition (adjacent position).
     * Cyclomatic Complexity: 2
     */
    @Test
    void testTooCloseToPosition1() {
        assertTrue(barge.tooCloseTo(new Position(5, 6)), "Error: Barge should be too close to adjacent position (5, 6).");
    }

    /**
     * Test for tooCloseTo with an IPosition (non-adjacent position).
     */
    @Test
    void testTooCloseToPosition2() {
        assertFalse(barge.tooCloseTo(new Position(7, 7)), "Error: Barge should not be too close to non-adjacent position (7, 7).");
    }

    /**
     * Test for the getTopMostPos method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testGetTopMostPos() {
        assertEquals(5, barge.getTopMostPos(), "Error: The topmost position should be 5.");
    }

    /**
     * Test for the getBottomMostPos method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testGetBottomMostPos() {
        assertEquals(5, barge.getBottomMostPos(), "Error: The bottommost position should be 5.");
    }

    /**
     * Test for the getLeftMostPos method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testGetLeftMostPos() {
        assertEquals(5, barge.getLeftMostPos(), "Error: The leftmost position should be 5.");
    }

    /**
     * Test for the getRightMostPos method.
     * Cyclomatic Complexity: 1
     */
    @Test
    void testGetRightMostPos() {
        assertEquals(5, barge.getRightMostPos(), "Error: The rightmost position should be 5.");
    }

    /**
     * Test for constructor with null inputs.
     * Ensure exception handling.
     */
    @Test
    void testConstructorNullInput() {
        assertThrows(NullPointerException.class, () -> new Barge(null, null),
                "Error: NullPointerException should be thrown for null input.");
        assertThrows(NullPointerException.class, () -> new Barge(Compass.NORTH, null),
                "Error: NullPointerException should be thrown for null position.");
    }
}