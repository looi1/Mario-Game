package game;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.behaviours.AttackBehaviour;
import game.behaviours.FollowBehaviour;
import game.enemies.Goomba;
import game.enemies.Koopa;
import game.ground.Dirt;
import game.ground.Floor;
import game.ground.Sprout;
import game.ground.Wall;
import game.items.PowerStar;
import game.items.SuperMushroom;
import game.items.Wrench;

/**
 * The main class for the Mario World game.
 *
 */
public class Application {

	public static void main(String[] args) {

			World world = new World(new Display());

			FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Sprout());

			List<String> map = Arrays.asList(
				"..........................................##....................................",
				"............................................#...................................",
				"............................................#...................................",
				".............................................##.................................",
				"...............................................#................................",
				"................................................#...............................",
				"..................................................#.............................",
				".................................................##.............................",
				"................................................##..............................",
				".........................................#____####..............................",
				"........................................#_____###...............................",
				"........................................#______###..............................",
				".........................................#_____###..............................",
				".................................................##.............................",
				"...................................................#............................",
				"....................................................#...........................",
				".....................................................#..........................",
				"......................................................#.........................",
				".......................................................##.......................");

			GameMap gameMap = new GameMap(groundFactory, map);
			world.addGameMap(gameMap);

			Player mario = new Player("Player", 'm', 100);
			world.addPlayer(mario, gameMap.at(44, 9));

			Random r = new Random();
			int minX = gameMap.getXRange().min();
			int maxX = gameMap.getXRange().max();
			int minY = gameMap.getYRange().min();
			int maxY = gameMap.getYRange().max();

			for (int i = 0; i < 20; i++){
				int x = r.nextInt((maxX - minX) + minX);
				int y = r.nextInt((maxY - minY) + minY);

				gameMap.at(x,y).setGround(new Sprout());
			}

			gameMap.at(44,9).addItem(new PowerStar());
			gameMap.at(44,9).addItem(new SuperMushroom());
			gameMap.at(44,10).addActor(new Toad());
			Yoshi yoshi = new Yoshi(mario);
			gameMap.at(43,8).addActor(yoshi);
			mario.adoptYoshi(yoshi);
			Koopa dummy = new Koopa();
			// gameMap.at(43,9).addActor(dummy);
			
			

			world.run();




	}
}
