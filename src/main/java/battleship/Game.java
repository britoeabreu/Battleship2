/**
 *
 */
package battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Game.
 *
 * @author fba
 */
public class Game implements IGame
{
	private static final char EMPTY_MARKER = '.';
	private static final char SHIP_MARKER = '#';
	private static final char SHOT_SHIP_MARKER = '*';
	private static final char SHOP_WATER_MARKER = 'o';

	/**
	 * The Fleet.
	 */
	private IFleet fleet;
	/**
	 * The Shots.
	 */
	private List<IPosition> shots;

	/**
	 * The Count invalid shots.
	 */
	private Integer countInvalidShots;
	/**
	 * The Count repeated shots.
	 */
	private Integer countRepeatedShots;
	/**
	 * The Count hits.
	 */
	private Integer countHits;
	/**
	 * The Count sinks.
	 */
	private Integer countSinks;


	/**
	 * Instantiates a new Game.
	 *
	 * @param fleet The fleet
	 */
	public Game(IFleet fleet)
	{
		shots = new ArrayList<>();
		countInvalidShots = 0;
		countRepeatedShots = 0;
		countHits = 0;
		countSinks = 0;
		this.fleet = fleet;
	}

	/**
	 * Gets shots.
	 *
	 * @return the shots
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see battleship.IGame#getFleet()
	 */
	@Override
	public IFleet getFleet()
	{
		return fleet;
	}

	/**
	 * Gets shots.
	 *
	 * @return the shots
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see battleship.IGame#getShots()
	 */
	@Override
	public List<IPosition> getShots()
	{
		return shots;
	}

	/**
	 * Fire ship.
	 *
	 * @param pos the pos
	 * @return the ship
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see battleship.IGame#fire(battleship.IPosition)
	 */
	@Override
	public IShip fire(IPosition pos)
	{
		if (!validShot(pos))
			countInvalidShots++;
		else
		{ // valid shot!
			if (repeatedShot(pos))
				countRepeatedShots++;
			else
			{
				shots.add(pos);
				IShip s = fleet.shipAt(pos);
				if (s != null)
				{
					s.shoot(pos);
					countHits++;
					if (!s.stillFloating())
					{
						countSinks++;
						return s;
					}
				}
			}
		}
		return null;
	}


	/**
	 * Gets repeated shots.
	 *
	 * @return the repeated shots
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see battleship.IGame#getRepeatedShots()
	 */
	@Override
	public int getRepeatedShots()
	{
		return this.countRepeatedShots;
	}

	/**
	 * Gets invalid shots.
	 *
	 * @return the invalid shots
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see battleship.IGame#getInvalidShots()
	 */
	@Override
	public int getInvalidShots()
	{
		return this.countInvalidShots;
	}

	/**
	 * Gets hits.
	 *
	 * @return the hits
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see battleship.IGame#getHits()
	 */
	@Override
	public int getHits()
	{
		return this.countHits;
	}

	/**
	 * Gets sunk ships.
	 *
	 * @return the sunk ships
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see battleship.IGame#getSunkShips()
	 */
	@Override
	public int getSunkShips()
	{
		return this.countSinks;
	}

	/**
	 * Gets remaining ships.
	 *
	 * @return the remaining ships
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see battleship.IGame#getRemainingShips()
	 */
	@Override
	public int getRemainingShips()
	{
		List<IShip> floatingShips = fleet.getFloatingShips();
		return floatingShips.size();
	}

	/**
	 * Valid shot boolean.
	 *
	 * @param pos the pos
	 * @return the boolean
	 */
	public boolean validShot(IPosition position) {
		if (position == null) {
			return false; // Null positions are invalid
		}
		int row = position.getRow();
		int column = position.getColumn();
		// Check for negative values or values outside the board size
		return row >= 0 && row < IGame.BOARD_SIZE && column >= 0 && column < IGame.BOARD_SIZE;
	}

	/**
	 * Repeated shot boolean.
	 *
	 * @param pos the pos
	 * @return the boolean
	 */
	public boolean repeatedShot(IPosition pos)
	{
		for (int i = 0; i < shots.size(); i++)
			if (shots.get(i).equals(pos))
				return true;
		return false;
	}



	public void printBoard(Boolean show_shots)
	{
		char[][] map = new char[IGame.BOARD_SIZE][IGame.BOARD_SIZE];

		for (int r = 0; r < IGame.BOARD_SIZE; r++)
			for (int c = 0; c < IGame.BOARD_SIZE; c++)
				map[r][c] = EMPTY_MARKER;

		for (IShip ship : this.getFleet().getShips())
			for (IPosition ship_pos : ship.getPositions())
				map[ship_pos.getRow()][ship_pos.getColumn()] = SHIP_MARKER;

		for (int row = 0; row < IGame.BOARD_SIZE; row++)
		{
			for (int col = 0; col < IGame.BOARD_SIZE; col++)
				System.out.print(map[row][col]);
			System.out.println();
		}

	}


	/**
	 * Prints the board showing valid shots that have been fired
	 */
	public void printValidShots()
	{
		printBoard(true);
	}


	/**
	 * Prints the board showing the fleet
	 */
	public void printFleet()
	{
		printBoard(false);
	}

}
