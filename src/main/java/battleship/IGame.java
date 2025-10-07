/**
 * 
 */
package battleship;

import java.util.List;

/**
 * The interface Game.
 */
public interface IGame
{
	/**
	 * The constant BOARD_SIZE.
	 */
	Integer BOARD_SIZE = 10;

	/**
	 * Fire ship.
	 *
	 * @param pos the pos
	 * @return the ship
	 */
	IShip fire(IPosition pos);

	/**
	 * Gets the fleet.
	 *
	 * @return the fleet
	 */
	IFleet getFleet();

	/**
	 * Gets shots.
	 *
	 * @return the shots
	 */
	List<IPosition> getShots();

	/**
	 * Gets repeated shots.
	 *
	 * @return the repeated shots
	 */
	int getRepeatedShots();

	/**
	 * Gets invalid shots.
	 *
	 * @return the invalid shots
	 */
	int getInvalidShots();

	/**
	 * Gets hits.
	 *
	 * @return the hits
	 */
	int getHits();

	/**
	 * Gets sunk ships.
	 *
	 * @return the sunk ships
	 */
	int getSunkShips();

	/**
	 * Gets remaining ships.
	 *
	 * @return the remaining ships
	 */
	int getRemainingShips();

	/**
	 * Print board.
	 */
	void printBoard(Boolean show_shots);

	/**
	 * Print valid shots.
	 */
	void printValidShots();

	/**
	 * Print fleet.
	 */
	void printFleet();
}
