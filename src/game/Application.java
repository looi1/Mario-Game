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
import game.enemies.Goomba;
import game.enemies.Koopa;

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

			Actor mario = new Player("Player", 'm', 100);
			world.addPlayer(mario, gameMap.at(44, 9));

			Random r = new Random();
			int minX = gameMap.getXRange().min();
			int maxX = gameMap.getXRange().max();
			int minY = gameMap.getYRange().min();
			int maxY = gameMap.getYRange().max();

			for (int i = 0; i < 16; i++){
				int x = r.nextInt((maxX - minX) + minX);
				int y = r.nextInt((maxY - minY) + minY);

				gameMap.at(x,y).setGround(new Sprout());
			}

			// FIXME: the Goomba should be generated from the Tree
			Goomba goomba = new Goomba();
			goomba.addBehaviour(8,new AttackBehaviour(mario));
			gameMap.at(35, 10).addActor(goomba);
			gameMap.at(44,8).addItem(new PowerStar());
			gameMap.at(44,10).addActor(new Toad());
			Wallet.totalBalance = 1000;

			Koopa koopa = new Koopa();
			koopa.addBehaviour(8,new AttackBehaviour(mario));
			gameMap.at(34,10).addActor(koopa);

			//delete
		Wrench wrench = new Wrench();
		gameMap.at(33,10).addItem(wrench);


			world.run();




	}
}
