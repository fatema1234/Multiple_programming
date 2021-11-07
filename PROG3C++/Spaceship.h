#ifndef SPACESHIP_H
#define SPACESHIP_H
#include "Sprite.h"
#include <SDL.h>
#include <SDL_image.h>
#include "System.h"
#include <iostream>
#include "Game.h"
#include "Bullet.h"
class Spaceship : public Sprite {
public:
	static Spaceship* getInstance();
	~Spaceship();
	void draw() const;
	void moveLeft();
	void moveRight();
	void setLeft(bool b);
	void setRight(bool b);
	void tick();
	Bullet* shoot();
	void takeDamage();
	int getLives();
	

private:
	Spaceship();
	SDL_Texture* texture;
	int counter = 0;
	int lives = 10;
	Game g;
	bool left, right;
};
#endif

