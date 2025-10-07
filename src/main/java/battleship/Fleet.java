/**
 * 
 */
package battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Fleet.
 */
public class Fleet implements IFleet
{
	/**
	 * This operation prints all the given ships
	 *
	 * @param ships The list of ships
	 */
	static void printShips(List<IShip> ships)
    {
	for (IShip ship : ships)
	    System.out.println(ship);
    }

    // -----------------------------------------------------

	/**
	 * The Ships.
	 */
	private List<IShip> ships;

	/**
	 * Instantiates a new Fleet.
	 */
	public Fleet()
    {
	ships = new ArrayList<>();
    }

	/**
	 * Gets ships.
	 *
	 * @return the ships
	 */
	@Override
    public List<IShip> getShips()
    {
	return ships;
    }

	/**
	 * Add ship boolean.
	 *
	 * @param s the s
	 * @return the boolean
	 */
	/*
     * (non-Javadoc)
     * 
     * @see battleship.IFleet#addShip(battleship.IShip)
     */
    @Override
    public boolean addShip(IShip s)
    {
	boolean result = false;
	if ((ships.size() <= FLEET_SIZE) && (isInsideBoard(s)) && (!colisionRisk(s)))
	{
	    ships.add(s);
	    result = true;
	}
	return result;
    }

	/**
	 * Gets ships like.
	 *
	 * @param category the category
	 * @return the ships like
	 */
	/*
     * (non-Javadoc)
     * 
     * @see battleship.IFleet#getShipsLike(java.lang.String)
     */
    @Override
    public List<IShip> getShipsLike(String category)
    {
	List<IShip> shipsLike = new ArrayList<>();
	for (IShip s : ships)
	    if (s.getCategory().equals(category))
		shipsLike.add(s);
	
	return shipsLike;
    }

	/**
	 * Gets floating ships.
	 *
	 * @return the floating ships
	 */
	/*
     * (non-Javadoc)
     * 
     * @see battleship.IFleet#getFloatingShips()
     */
    @Override
    public List<IShip> getFloatingShips()
    {
	List<IShip> floatingShips = new ArrayList<>();
	for (IShip s : ships)
	    if (s.stillFloating())
		floatingShips.add(s);

	return floatingShips;
    }

	/**
	 * Ship at ship.
	 *
	 * @param pos the pos
	 * @return the ship
	 */
	/*
     * (non-Javadoc)
     * 
     * @see battleship.IFleet#shipAt(battleship.IPosition)
     */
    @Override
    public IShip shipAt(IPosition pos)
    {
	for (int i = 0; i < ships.size(); i++)
	    if (ships.get(i).occupies(pos))
		return ships.get(i);
	return null;
    }

	/**
	 * Is inside board boolean.
	 *
	 * @param s the s
	 * @return the boolean
	 */
	private boolean isInsideBoard(IShip s)
    {
	return (s.getLeftMostPos() >= 0 && s.getRightMostPos() <= IGame.BOARD_SIZE - 1 && s.getTopMostPos() >= 0
		&& s.getBottomMostPos() <= IGame.BOARD_SIZE - 1);
    }

	/**
	 * Colision risk boolean.
	 *
	 * @param s the s
	 * @return the boolean
	 */
	private boolean colisionRisk(IShip s)
    {
	for (int i = 0; i < ships.size(); i++)
	{
	    if (ships.get(i).tooCloseTo(s))
		return true;
	}
	return false;
    }


	/**
	 * This operation shows the state of a fleet
	 */
	public void printStatus()
    {
		printAllShips();
		printFloatingShips();
		printShipsByCategory("Galeao");
		printShipsByCategory("Fragata");
		printShipsByCategory("Nau");
		printShipsByCategory("Caravela");
		printShipsByCategory("Barca");
    }

	/**
	 * This operation prints all the ships of a fleet belonging to a particular
	 * category
	 *
	 * @param category The category of ships of interest
	 */
	public void printShipsByCategory(String category)
    {
	assert category != null;

	printShips(getShipsLike(category));
    }

	/**
	 * This operation prints all the ships of a fleet but not yet shot
	 */
	public void printFloatingShips()
    {
	printShips(getFloatingShips());
    }

	/**
	 * This operation prints all the ships of a fleet
	 *
	 * @param fleet The fleet of ships
	 */
	void printAllShips()
    {
	printShips(ships);
    }

}
