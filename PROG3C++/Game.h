#ifndef SESSION_H
#define SESSION_H
#include "Sprite.h"
#include "SDL_ttf.h"

#include <vector>


class Game
{
public:
	void add(Sprite* sprite);
	void remove(Sprite* sprite);
	bool checkCollision(Sprite* c1, Sprite* c2);
	void handleCollision(Sprite* c1, Sprite* c2);
	void outOfWindow(Sprite* s);
	void run();
	int randomNr();
	void spawnEnemy();
private:
	std::vector<Sprite*> sprites;
	std::vector<Sprite*> added, removed;
	const int windowWidth = 700, windowHeigth = 500;

};

#endif#pragma once

