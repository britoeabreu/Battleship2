/**
 * Represents an abstract ship in the Discoveries Battleship Game.
 * <p>
 * This class provides the base implementation for all ship types in the game,
 * including position management, hit detection, and fleet validation logic.
 * Concrete ship types (e.g., {@link Galleon}, {@link Frigate}) extend this class.
 * </p>
 *
 * @author IGE-106806
 * @version 1.0
 * @see IShip
 */
package battleship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public abstract class Ship implements IShip
{
	/** Identifier constant for the Galleon ship type (size 5). */
	private static final String GALEAO = "galeao";

	/** Identifier constant for the Frigate ship type (size 4). */
	private static final String FRAGATA = "fragata";

	/** Identifier constant for the Carrack ship type (size 3). */
	private static final String NAU = "nau";

	/** Identifier constant for the Caravel ship type (size 2). */
	private static final String CARAVELA = "caravela";

	/** Identifier constant for the Barge ship type (size 1). */
	private static final String BARCA = "barca";

	/**
	 * Factory method that creates and returns a concrete {@link Ship} instance
	 * based on the given ship kind identifier.
	 *
	 * @param shipKind a string identifying the type of ship to create
	 *                 (e.g., {@code "galeao"}, {@code "fragata"}, {@code "nau"},
	 *                 {@code "caravela"}, {@code "barca"})
	 * @param bearing  the initial orientation of the ship ({@link Compass#NORTH},
	 *                 {@link Compass#EAST}, etc.)
	 * @param pos      the initial anchor position of the ship on the grid
	 * @return a new {@link Ship} instance of the appropriate subclass,
	 *         or {@code null} if {@code shipKind} is not recognised
	 */
	static Ship buildShip(String shipKind, Compass bearing, Position pos)
    {
        Ship s;
        switch (shipKind)
        {
        case BARCA:
            s = new Barge(bearing, pos);
            break;
        case CARAVELA:
            s = new Caravel(bearing, pos);
            break;
        case NAU:
            s = new Carrack(bearing, pos);
            break;
        case FRAGATA:
            s = new Frigate(bearing, pos);
            break;
        case GALEAO:
            s = new Galleon(bearing, pos);
            break;
        default:
            s = null;
        }
        return s;
    }

    //---------------------------------------------------------

	/** The category (type name) of this ship, e.g., {@code "galeao"}. */
	private String category;

	/** The current bearing (orientation) of this ship on the grid. */
	private Compass bearing;

	/** The anchor (top-left) position of this ship on the grid. */
	private IPosition pos;

	/**
	 * The number of grid cells this ship occupies.
	 * Determined by the concrete subclass at construction time.
	 */
	private Integer size;

	/**
	 * The list of all grid positions occupied by this ship.
	 * Populated by concrete subclasses during construction.
	 */
	protected List<IPosition> positions;


	/**
	 * Constructs a new {@code Ship} with the specified category, bearing,
	 * anchor position, and size.
	 * <p>
	 * All parameters are mandatory; passing {@code null} for {@code category},
	 * {@code bearing}, or {@code pos} will throw a {@link NullPointerException}.
	 * </p>
	 *
	 * @param category the ship type identifier (must not be {@code null})
	 * @param bearing  the orientation of the ship on the grid (must not be {@code null})
	 * @param pos      the anchor position of the ship on the grid (must not be {@code null})
	 * @param size     the number of grid cells this ship occupies (must be &gt; 0)
	 * @throws NullPointerException if {@code category}, {@code bearing}, or {@code pos} is {@code null}
	 */
	public Ship(String category, Compass bearing, IPosition pos, int size)
    {
		this.category = Objects.requireNonNull(category, "Ship's category must not be null");
		this.bearing = Objects.requireNonNull(bearing, "Ship's bearing must not be null");
		this.pos = Objects.requireNonNull(pos, "Ship's position must not be null");

		this.size = size;
		positions = new ArrayList<>();
    }

	/**
	 * Returns the category (type name) of this ship.
	 *
	 * @return a {@link String} identifying the ship type (e.g., {@code "galeao"})
	 */
    @Override
    public String getCategory()
    {
		return category;
    }

	/**
	 * Returns the list of all grid positions currently occupied by this ship.
	 *
	 * @return a {@link List} of {@link IPosition} objects representing each cell
	 *         this ship occupies on the grid
	 */
	public List<IPosition> getPositions()
    {
		return positions;
    }

	/**
	 * Returns the anchor (origin) position of this ship on the grid.
	 * This is the position passed at construction time, typically the
	 * top-most or left-most cell.
	 *
	 * @return the anchor {@link IPosition} of this ship
	 */
    @Override
    public IPosition getPosition()
    {
		return pos;
    }

	/**
	 * Returns the current bearing (orientation) of this ship.
	 *
	 * @return the {@link Compass} direction this ship is oriented towards
	 */
    @Override
    public Compass getBearing()
    {
		return bearing;
    }

	/**
	 * Returns the number of grid cells this ship occupies.
	 *
	 * @return the size of the ship as an {@link Integer}
	 */
	public Integer getSize()
	{
		return size;
	}

	/**
	 * Determines whether this ship is still afloat, i.e., at least one of its
	 * positions has not yet been hit.
	 *
	 * @return {@code true} if at least one position remains unhit;
	 *         {@code false} if all positions have been hit (ship is sunk)
	 */
    @Override
    public boolean stillFloating()
    {
		for (int i = 0; i < getSize(); i++)
		    if (!getPositions().get(i).isHit())
			return true;
		return false;
    }

	/**
	 * Returns the row index of the top-most position occupied by this ship.
	 * Useful for boundary and overlap checks on the grid.
	 *
	 * @return the smallest row index among all positions of this ship
	 */
    @Override
    public int getTopMostPos()
    {
		int top = getPositions().get(0).getRow();
		for (int i = 1; i < getSize(); i++)
		    if (getPositions().get(i).getRow() < top)
			top = getPositions().get(i).getRow();
		return top;
    }

	/**
	 * Returns the row index of the bottom-most position occupied by this ship.
	 * Useful for boundary and overlap checks on the grid.
	 *
	 * @return the largest row index among all positions of this ship
	 */
    @Override
    public int getBottomMostPos()
    {
		int bottom = getPositions().get(0).getRow();
		for (int i = 1; i < getSize(); i++)
		    if (getPositions().get(i).getRow() > bottom)
			bottom = getPositions().get(i).getRow();
		return bottom;
    }

	/**
	 * Returns the column index of the left-most position occupied by this ship.
	 * Useful for boundary and overlap checks on the grid.
	 *
	 * @return the smallest column index among all positions of this ship
	 */
    @Override
    public int getLeftMostPos()
    {
		int left = getPositions().get(0).getColumn();
		for (int i = 1; i < getSize(); i++)
		    if (getPositions().get(i).getColumn() < left)
			left = getPositions().get(i).getColumn();
		return left;
    }

	/**
	 * Returns the column index of the right-most position occupied by this ship.
	 * Useful for boundary and overlap checks on the grid.
	 *
	 * @return the largest column index among all positions of this ship
	 */
    @Override
    public int getRightMostPos()
    {
		int right = getPositions().get(0).getColumn();
		for (int i = 1; i < getSize(); i++)
		    if (getPositions().get(i).getColumn() > right)
			right = getPositions().get(i).getColumn();
		return right;
    }

	/**
	 * Checks whether this ship occupies the given grid position.
	 *
	 * @param pos the {@link IPosition} to check; must not be {@code null}
	 * @return {@code true} if any of this ship's positions equals {@code pos};
	 *         {@code false} otherwise
	 */
    @Override
    public boolean occupies(IPosition pos)
    {
		assert pos != null;

		for (int i = 0; i < getSize(); i++)
			if (getPositions().get(i).equals(pos))
				return true;
		return false;
    }

	/**
	 * Checks whether this ship is too close to another ship.
	 * <p>
	 * Two ships are considered too close if any position of {@code other}
	 * is adjacent (including diagonally) to any position of this ship.
	 * Ships must not touch each other when placed on the grid.
	 * </p>
	 *
	 * @param other the other {@link IShip} to compare against; must not be {@code null}
	 * @return {@code true} if any position of {@code other} is adjacent to
	 *         any position of this ship; {@code false} otherwise
	 */
    @Override
    public boolean tooCloseTo(IShip other)
    {
		assert other != null;
		
		Iterator<IPosition> otherPos = other.getPositions().iterator();
		while (otherPos.hasNext())
		    if (tooCloseTo(otherPos.next()))
			return true;

		return false;
    }

	/**
	 * Checks whether this ship is too close to a given grid position.
	 * <p>
	 * A ship is considered too close if any of its positions is adjacent
	 * (including diagonally) to {@code pos}.
	 * </p>
	 *
	 * @param pos the {@link IPosition} to check proximity against; must not be {@code null}
	 * @return {@code true} if any position of this ship is adjacent to {@code pos};
	 *         {@code false} otherwise
	 */
    @Override
    public boolean tooCloseTo(IPosition pos)
    {
		for (int i = 0; i < this.getSize(); i++)
		    if (getPositions().get(i).isAdjacentTo(pos))
			return true;
		return false;
    }

	/**
	 * Registers a shot fired at the given grid position.
	 * <p>
	 * If this ship occupies {@code pos}, that position is marked as hit.
	 * If the ship does not occupy {@code pos}, no state change occurs.
	 * </p>
	 *
	 * @param pos the {@link IPosition} being targeted; must not be {@code null}
	 *            and must be a valid grid position
	 */
    @Override
    public void shoot(IPosition pos)
    {
		assert pos != null;
		assert pos.isValid();

		for (IPosition position : getPositions())
		{
			if (position.equals(pos))
				position.shoot();
		}
    }

	/**
	 * Immediately sinks this ship by marking all of its positions as hit.
	 * <p>
	 * After calling this method, {@link #stillFloating()} will return {@code false}.
	 * This can be used to forcefully remove a ship from play.
	 * </p>
	 */
	@Override
	public void sink() {
		for (IPosition position : getPositions())
			position.shoot();
	}

	/**
	 * Returns a human-readable string representation of this ship,
	 * including its category, bearing, and anchor position.
	 * <p>
	 * Format: {@code [category bearing pos]}
	 * </p>
	 *
	 * @return a {@link String} in the format {@code "[category bearing pos]"}
	 */
	@Override
    public String toString()
    {
		return "[" + category + " " + bearing + " " + pos + "]";
    }

}
