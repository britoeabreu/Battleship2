package battleship;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Tasks.
 */
public class Tasks
{
	/**
	 * The constant LOGGER.
	 */
	private static final Logger LOGGER = LogManager.getLogger();

	/**
	 * The constant NUMBER_SHOTS.
	 */
	private static final int NUMBER_SHOTS = 3;

	/**
	 * The constant GOODBYE_MESSAGE.
	 */
	private static final String GOODBYE_MESSAGE = "Bons ventos!";

	/**
	 * Strings to be used by the user
	 */
	private static final String NOVAFROTA = "nova";
	/**
	 * The constant DESISTIR.
	 */
	private static final String DESISTIR = "desisto";
	/**
	 * The constant RAJADA.
	 */
	private static final String RAJADA = "rajada";
	/**
	 * The constant VERTIROS.
	 */
	private static final String VERTIROS = "ver";
	/**
	 * The constant BATOTA.
	 */
	private static final String BATOTA = "mapa";
	/**
	 * The constant STATUS.
	 */
	private static final String STATUS = "estado";

    
    /////////////////////////////////////////////////////////////////////////////
    // hereafter one may find some code that can be converted to automatic tests,
    // as long as appropriate changes are made. It also shows that we should
    // develop our code incrementally e.g. first the ships, then the fleet,
    // then some rule checking, then dealing with firing and so on
    /////////////////////////////////////////////////////////////////////////////

	/**
	 * This task tests the building up of ships: For each ship, reads positions and
	 * indicates whether the ship occupies each one of such positions or not
	 */
	public static void taskA()
    {
	Scanner in = new Scanner(System.in);
	while (in.hasNext())
	{
	    Ship s = readShip(in);
	    if (s != null)
		for (int i = 0; i < NUMBER_SHOTS; i++)
		{
		    Position p = readPosition(in);
		    LOGGER.info("{} {}", p, s.occupies(p));
		}
	}
    }

	/**
	 * This task tests the building up of fleets
	 */
	public static void taskB()
    {
	Scanner in = new Scanner(System.in);
	IFleet fleet = null;
	String command = in.next();
	while (!command.equals(DESISTIR))
	{
	    switch (command)
	    {
	    case NOVAFROTA:
		fleet = buildFleet(in);
		break;
	    case STATUS:
		fleet.printStatus();
		break;
	    default:
		LOGGER.info("Que comando é esse??? Repete lá ...");
	    }
	    // The other commands are unknown in this task
	    command = in.next();
	}
	LOGGER.info(GOODBYE_MESSAGE);
    }

	/**
	 * This task tests the building up of fleets and takes into consideration the
	 * possibility of cheating
	 */
	public static void taskC()
    {
	Scanner in = new Scanner(System.in);
	IFleet fleet = null;
	String command = in.next();
	while (!command.equals(DESISTIR))
	{
	    switch (command)
	    {
	    case NOVAFROTA:
		fleet = buildFleet(in);
		break;

	    case STATUS:
		fleet.printStatus();
		break;

	    case BATOTA:
		LOGGER.info(fleet);
		break;

	    default:
		LOGGER.info("Que comando é esse??? Repete lá ...");
	    }
	    // The other commands are unknown in this task
	    command = in.next();
	}
	LOGGER.info(GOODBYE_MESSAGE);
    }

	/**
	 * This task also tests the fighting element of a round of three shots
	 */
	public static void taskD()
    {

	Scanner in = new Scanner(System.in);
	IFleet fleet = null;
	IGame game = null;
	String command = in.next();
	while (!command.equals(DESISTIR))
	{
	    switch (command)
	    {
	    case NOVAFROTA:
		fleet = buildFleet(in);
		game = new Game(fleet);
		break;
	    case STATUS:
		if (fleet != null)
		    fleet.printStatus();
		break;
	    case BATOTA:
		if (fleet != null)
		    game.printFleet();
		break;
	    case RAJADA:
		if (game != null)
		{
		    firingRound(in, game);

		    LOGGER.info("Hits: {} Inv: {} Rep: {} Restam {} navios.", game.getHits(), game.getInvalidShots(),
			    game.getRepeatedShots(), game.getRemainingShips());
		    if (game.getRemainingShips() == 0)
			LOGGER.info("Maldito sejas, Java Sparrow, eu voltarei, glub glub glub...");
		}
		break;
	    case VERTIROS:
		if (game != null)
		    game.printValidShots();
		break;
	    default:
		LOGGER.info("Que comando é esse??? Repete ...");
	    }
	    command = in.next();
	}
	LOGGER.info(GOODBYE_MESSAGE);
    }

	/**
	 * This operation allows the build up of a fleet, given user data
	 *
	 * @param in The scanner to read from
	 * @return The fleet that has been built
	 */
	static Fleet buildFleet(Scanner in)
    {
	assert in != null;

	Fleet fleet = new Fleet();
	int i = 0; // i represents the total of successfully created ships
	while (i <= Fleet.FLEET_SIZE)
	{
	    IShip s = readShip(in);
	    if (s != null)
	    {
		boolean success = fleet.addShip(s);
		if (success)
		    i++;
		else
		    LOGGER.info("Falha na criacao de {} {} {}", s.getCategory(), s.getBearing(), s.getPosition());
	    } else
	    {
		LOGGER.info("Navio desconhecido!");
	    }
	}
	LOGGER.info("{} navios adicionados com sucesso!", i);
	return fleet;
    }

	/**
	 * This operation reads data about a ship, build it and returns it
	 *
	 * @param in The scanner to read from
	 * @return The created ship based on the data that has been read
	 */
	static Ship readShip(Scanner in)
    {
	String shipKind = in.next();
	Position pos = readPosition(in);
	char c = in.next().charAt(0);
	Compass bearing = Compass.charToCompass(c);
	return Ship.buildShip(shipKind, bearing, pos);
    }

	/**
	 * This operation allows reading a position in the map
	 *
	 * @param in The scanner to read from
	 * @return The position that has been read
	 */
	static Position readPosition(Scanner in)
    {
	int row = in.nextInt();
	int column = in.nextInt();
	return new Position(row, column);
    }

	/**
	 * This operation allows firing a round of shots (three) over a fleet, in the
	 * context of a game
	 *
	 * @param in   The scanner to read from
	 * @param game The context game while fleet is being attacked
	 */
	static void firingRound(Scanner in, IGame game)
    {
	for (int i = 0; i < NUMBER_SHOTS; i++)
	{
	    IPosition pos = readPosition(in);
	    IShip sh = game.fire(pos);
	    if (sh != null)
		LOGGER.info("Mas... mas... {}s nao sao a prova de bala? :-(", sh.getCategory());
	}

    }

}
