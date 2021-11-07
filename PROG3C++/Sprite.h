#ifndef SPRITE_H
#define SPRITE_H
#include <SDL.h>

class Sprite {

public:
	virtual void draw() const = 0;
	virtual int getX();
	virtual int getY();
	SDL_Rect getRect() const;
	virtual void tick() = 0;
	
	virtual ~Sprite() {}
protected:
	Sprite(int x, int y, int w, int h);
	SDL_Rect rect;
	
private:
	Sprite(const Sprite& other) = delete;
	const Sprite& operator=(const Sprite& other) = delete;
	
};


#endif


