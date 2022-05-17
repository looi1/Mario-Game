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
import game.ground.*;
import game.items.PowerStar;
import game.items.StinkBug;
import game.items.SuperMushroom;
import game.items.Wrench;

/**
 * The main class for the Mario World game.
 *
 */
public class Application {

	public static void main(String[] args) {

			World world = new World(new Display());

			FancyGroundFactory groundFactory1 = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Sprout(), new WarpPipe());
			FancyGroundFactory groundFactory2 = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Sprout(), new WarpPipe(), new Lava());

			List<String> firstMap = Arrays.asList(
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

		List<String> secondMap = Arrays.asList(
				"C.......................##........................",
				"...................L......#.......................",
				"..........................#............L..........",
				".......L...................##..............L......",
				".............................#....................",
				"..............................#.........L.........",
				"..............L.................#.................",
				"........................L......##.....L...........",
				"..............................##..................",
				"....L.L.L..............#____####..................",
				"...L..L..L............#_____###....L..............",
				"...L.L.L.L............#______###..................",
				"....L.L.L..............#_____###..................",
				"...............................##...........L.....",
				".................................#................",
				"..................................#...............",
				"..........L........................#..............",
				".........................L..........#......L......",
				".....................................##...........");

			GameMap gameMap1 = new GameMap(groundFactory1, firstMap);
			GameMap gameMap2 = new GameMap(groundFactory2, secondMap);
			world.addGameMap(gameMap1);
			world.addGameMap(gameMap2);

			Player mario = new Player("Player", 'm', 1000);
			world.addPlayer(mario, gameMap1.at(44, 9));
			// world.addPlayer(mario, gameMap1.at(30, 9));


			Random r = new Random();
			int minX = gameMap1.getXRange().min();
			int maxX = gameMap1.getXRange().max();
			int minY = gameMap1.getYRange().min();
			int maxY = gameMap1.getYRange().max();

			for (int i = 0; i < 20; i++){
				int x = r.nextInt((maxX - minX) + minX);
				int y = r.nextInt((maxY - minY) + minY);

				gameMap1.at(x,y).setGround(new Sprout());
			}

			for (int j = 0; j < 4; j++){
				int x = r.nextInt((maxX - minX) + minX);
				int y = r.nextInt((maxY - minY) + minY);

				gameMap1.at(x,y).setGround(new WarpPipe());
			}

			for (int j = 0; j < 8; j++){
				int x = r.nextInt((maxX - minX) + minX);
				int y = r.nextInt((maxY - minY) + minY);

				gameMap1.at(x,y).addItem(new StinkBug());
			}

			

			gameMap1.at(44,9).addItem(new PowerStar());
			gameMap1.at(44,9).addItem(new SuperMushroom());
			gameMap1.at(44,10).addActor(new Toad());
			Yoshi yoshi = new Yoshi(mario);
			gameMap1.at(43,8).addActor(yoshi);
			mario.adoptYoshi(yoshi);
			gameMap1.at(31,9).addActor(new Koopa());
			// gameMap1.at(30,10).addActor(new Koopa());
			// gameMap1.at(29,10).addActor(new Koopa());

			Wallet.totalBalance = 10000;

			world.run();




	}
}
