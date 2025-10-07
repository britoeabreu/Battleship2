package battleship;

/**
 * The interface Position.
 *
 * @author fba
 */
public interface IPosition
{
	/**
	 * Gets row.
	 *
	 * @return the row
	 */
	int getRow();

	/**
	 * Gets column.
	 *
	 * @return the column
	 */
	int getColumn();

	/**
	 * Equals boolean.
	 *
	 * @param other the other
	 * @return the boolean
	 */
	boolean equals(Object other);

	/**
	 * Checks if the position is valid.
	 *
	 * @return true if valid, false otherwise
	 */
	boolean isValid();

	/**
	 * Is adjacent to boolean.
	 *
	 * @param other the other
	 * @return the boolean
	 */
	boolean isAdjacentTo(IPosition other);

	/**
	 * Occupy.
	 */
	void occupy();

	/**
	 * Shoot.
	 */
	void shoot();

	/**
	 * Is occupied boolean.
	 *
	 * @return the boolean
	 */
	boolean isOccupied();

	/**
	 * Is hit boolean.
	 *
	 * @return the boolean
	 */
	boolean isHit();
}
