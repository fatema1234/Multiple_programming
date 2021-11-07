
#include "Game.h"
#include "Enemy.h"
#include "Spaceship.h"


int main(int argc, char** argv) {
	Game g;
	g.add(Spaceship::getInstance());
	g.add(Enemy::getInstance(g.randomNr(), 2));


	g.run();
	return 0;
}
