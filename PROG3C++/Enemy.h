#ifndef ENEMY_H
#define ENEMY_H
#include "Sprite.h"
#include <SDL.h>
#include <SDL_image.h>
#include "System.h"
class Enemy : public Sprite {
public:
	static Enemy* getInstance(int x, int l);
	~Enemy();
	void draw() const;
	void tick();
	void takeDamage();
	int getLives();
	int getLevel();
private:
	Enemy(int x, int l);
	int lives;
	int level;
	SDL_Texture* texture;
	int counter = 0;
};



#endif
