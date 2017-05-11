# TODO

1. Improve ball movement/bounce.

2. Improve collision detection.

----------

3. Create working menu system.

4. Create powerups.

5. Create player and player life. (starting with 5.) 

6. Create level structure.

7. Make a code review of the Menu classes. 
   - Make their code consistent.
   - Check that Game class uses them right.
   - Make a code review of the Game class.
   - Fix these.
   - Make rectangles for the menu items. Maybe. Test it.
   - Make sure everything works.

Powerup ideas:
	1. Paddle speed increase.
	2. Paddle width increase.
	3. Slow down ball.
	4. Increase ball size.
	5. Shoot with projectiles.
	6. Remove 3 blocks.
	7. Increase life points.
	8. Increase ball speed.
	9. Decrease paddle with.
	10. Decrease ball size.
	11. Add 6 blocks.
    12. Thinner paddle.
    13. Thicker paddle.
    
Should the good and bad powerups be equal,
or should one be superior?
    
      
Known bugs:

1. The ball can get stuck on the paddle, as well as gliding
   through it. This is caused by the collision detection, 
   so it is most likely that which is in need of improvement.
   
2. The path in the sound paths are not working. 
   I suspect that it has something to do with the 
   audio class folder, that is not working.