#ifndef BULLET_H
#define BULLET_H
#include "Sprite.h"
#include <SDL.h>
#include <SDL_image.h>
#include "System.h"
#include "Game.h"
#include <iostream>

class Bullet : public Sprite {
public:
	static Bullet* getInstance(int x);
	
	~Bullet();
	void draw() const;
	void tick();
private:
	Bullet(int x);
	SDL_Texture *texture;
	int counter = 0;
	Game g;

};
#endif

