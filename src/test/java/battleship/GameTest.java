package battleship;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Game.
 * Author: britoeabreu
 * Date: 2024-03-19
 * Time: 15:30
 * Cyclomatic Complexity for each method:
 * - Game (constructor): 1
 * - fire: 7
 * - getShots: 1
 * - getRepeatedShots: 1
 * - getInvalidShots: 1
 * - getHits: 1
 * - getSunkShips: 1
 * - getRemainingShips: 1
 * - validShot: 3
 * - repeatedShot: 2
 * - printBoard: 1
 * - printValidShots: 1
 * - printFleet: 1
 */
public class GameTest {

	private Game game;

	@BeforeEach
	void setUp() {
		game = new Game(new Fleet()); // Assuming Fleet is a concrete implementation of IFleet
	}

	@AfterEach
	void tearDown() {
		game = null;
	}

	@Test
	void constructor() {
		assertNotNull(game, "Game instance should not be null after construction.");
		assertNotNull(game.getShots(), "Shots list should not be null after initialization.");
		assertTrue(game.getShots().isEmpty(), "Shots list should be empty upon initialization.");
		assertEquals(0, game.getInvalidShots(), "Invalid shots count should be zero upon initialization.");
		assertEquals(0, game.getRepeatedShots(), "Repeated shots count should be zero upon initialization.");
		assertEquals(0, game.getHits(), "Hits count should be zero upon initialization.");
		assertEquals(0, game.getSunkShips(), "Sunk ships count should be zero upon initialization.");
	}

	@Test
	void fire1() {
		Position validPosition = new Position(2, 3);
		game.fire(validPosition);
		assertEquals(1, game.getShots().size(), "Valid shot should be added to the shots list.");
	}

	@Test
	void fire2() {
		Position invalidPosition = new Position(-1, 5);
		game.fire(invalidPosition);
		assertEquals(1, game.getInvalidShots(), "Invalid shots counter should increase for an invalid shot.");
	}

	@Test
	void fire3() {
		Position position = new Position(2, 3);
		game.fire(position);
		game.fire(position);
		assertEquals(1, game.getRepeatedShots(), "Repeated shots counter should increase for a repeated shot.");
	}

	@Test
	void validShot1() {
		Position validPosition = new Position(2, 3);
		assertTrue(game.validShot(validPosition), "Position (2,3) should be valid.");
	}

	@Test
	void validShot2() {
		Position invalidPosition = new Position(-1, 5);
		assertFalse(game.validShot(invalidPosition), "Position with negative row should be invalid.");
	}

	@Test
	void validShot3() {
		Position invalidPosition = new Position(IGame.BOARD_SIZE, 5);
		assertFalse(game.validShot(invalidPosition), "Position with row >= BOARD_SIZE should be invalid.");
	}

	@Test
	void repeatedShot1() {
		Position position = new Position(2, 3);
		game.fire(position);
		assertTrue(game.repeatedShot(position), "Position (2,3) should be marked as repeated after firing.");
	}

	@Test
	void repeatedShot2() {
		Position position = new Position(2, 3);
		assertFalse(game.repeatedShot(position), "Position (2,3) should not be marked as repeated before firing.");
	}

	@Test
	void getShots() {
		Position position = new Position(2, 3);
		game.fire(position);
		assertEquals(1, game.getShots().size(), "Shots list should contain one shot after firing once.");
	}

	@Test
	void getRemainingShips() {
		IFleet fleet = game.getFleet();
		Ship ship1 = new Barge(Compass.NORTH, new Position(1, 1));
		Ship ship2 = new Frigate(Compass.EAST, new Position(5, 5));

		fleet.addShip(ship1);
		assertEquals(1, game.getRemainingShips(), "Just one ship was created!");
		fleet.addShip(ship2);
		assertEquals(2, game.getRemainingShips(), "Two ships were created!");
		ship2.sink();
		assertEquals(1, game.getRemainingShips(), "Remaining ships count should be 1 after sinking one of two ships.");
	}

	@Test
	void printBoard() {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		final PrintStream originalOut = System.out;

		// System.setOut(new PrintStream(outContent));

		IFleet fleet = game.getFleet();

		Ship ship1 = new Barge(Compass.NORTH, new Position(1, 1));
		Ship ship2 = new Frigate(Compass.EAST, new Position(5, 5));
		fleet.addShip(ship1);
		fleet.addShip(ship2);

		game.printBoard(true);

		System.setOut(originalOut);

	}
}