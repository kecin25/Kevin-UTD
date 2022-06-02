# Space Invaders Game
# Description: A space invaders look alike game where the main object is to survive as long as possible.
#              As the player destroys enemy ships, they gain points towards their score, the highest score is saved
# Made by: Kevin Boudreaux
# Last edited: 1/22/2022
import pygame
import random
import os
from Ship import Ship
from Ship import Laser

# initializes fonts for pygame to use #
pygame.font.init()
pygame.init()
# Setting up display #
size_info = pygame.display.Info()
WIN = pygame.display.set_mode((750, 750), pygame.RESIZABLE)
pygame.display.set_caption("Space Invaders")

# Loading images
# instead of doing os.path.join you can also do .load(assets/filename)
RED_SPACE_SHIP = pygame.image.load(os.path.join("assets", "pixel_ship_red_small.png"))
BLUE_SPACE_SHIP = pygame.image.load(os.path.join("assets", "pixel_ship_blue_small.png"))
GREEN_SPACE_SHIP = pygame.image.load(os.path.join("assets", "pixel_ship_green_small.png"))
# Player ship
YELLOW_SPACE_SHIP = pygame.image.load(os.path.join("assets", "pixel_ship_yellow.png"))

# Lasers
RED_LASER = pygame.image.load("assets/pixel_laser_red.png")
BLUE_LASER = pygame.image.load("assets/pixel_laser_blue.png")
GREEN_LASER = pygame.image.load("assets/pixel_laser_green.png")
YELLOW_LASER = pygame.image.load("assets/pixel_laser_yellow.png")


# overwrites some functions in parent class called Ship
class Player(Ship):
    def __init__(self, x, y, health=100):
        # uses same function from parent constructor
        super().__init__(x, y, health)
        self.ship_img = YELLOW_SPACE_SHIP
        self.laser_img = YELLOW_LASER
        # mask is used to find exact size of the image being used for the player ship
        # helps with hit boxes
        self.mask = pygame.mask.from_surface(self.ship_img)
        self.max_health = health
        self.height = Ship.get_height(self)
        self.width = Ship.get_width(self)

    def move_lasers(self, vel, objs, game_height=WIN.get_height()):
        self.cooldown()
        score = 0
        # holds an array of lasers already shot
        # goes through array and moves each laser
        for laser in self.lasers:
            # moves the laser equal to the velocity passed through
            laser.move(vel)
            # to save time and space, deletes lasers that are off-screen
            if laser.off_screen(game_height):
                self.lasers.remove(laser)
            else:
                for obj in objs:
                    # checks to see if there is a collision between the current laser and all objects
                    if laser.collision(obj):
                        # all lasers deal 10 damage
                        obj.health -= 10  # obj loses 10 health
                        if obj.health <= 0:
                            # adds score depending on the strength of the enemy ship
                            if obj.ship_img == RED_SPACE_SHIP:
                                score += 30
                            elif obj.ship_img == GREEN_SPACE_SHIP:
                                score += 20
                            else:
                                score += 10
                            objs.remove(obj)
                        if laser in self.lasers:
                            self.lasers.remove(laser)
        # returns score to be displayed on GUI
        return score

    # draws player ship and health_bar using Ship draw function
    def draw(self, window):
        super().draw(window)
        self.health_bar(window)

    def health_bar(self, window):
        # draws a red health bar that is the same size of the player ship
        pygame.draw.rect(window, (255, 0, 0),
                         (self.x, self.y + self.ship_img.get_height() + 10, self.ship_img.get_width(), 10))
        # draws a green health bar of a size equal to (current health / max health) over the red bar
        pygame.draw.rect(window, (0, 255, 0), (self.x, self.y + self.ship_img.get_height() + 10,
                                               self.ship_img.get_width() * (self.health / self.max_health), 10))


class Enemy(Ship):
    # used to determine the stats of the new ship
    COLOR_MAP = {
        "red": (RED_SPACE_SHIP, RED_LASER, 30, 60),  # Ship color, laser color, health, rate of fire/ pfs
        "blue": (GREEN_SPACE_SHIP, GREEN_LASER, 20, 120),  # Ship color, laser color, health, rate of fire/ pfs
        "green": (BLUE_SPACE_SHIP, BLUE_LASER, 10, 180)  # Ship color, laser color, health, rate of fire/ pfs
    }

    def __init__(self, x, y, color, health=100):
        # uses the same constructor as in the Ship class
        super().__init__(x, y, health)
        # updates the ship_img, laser_img, health, and cooldown depending on the passed in color
        self.ship_img, self.laser_img, self.health, self.COOLDOWN = self.COLOR_MAP[color]
        # updates the mask of the ship to fix the hit-box around the used image
        self.mask = pygame.mask.from_surface(self.ship_img)

    # moves the ship down the screen = to the velocity given
    def move(self, vel):
        self.y += vel

    def shoot(self):
        # checks to see if the ship's cooldown to shoot is over
        if self.cool_down_counter == 0:
            # since the different colored ships are different sizes
            # I needed to alter where their lasers spawn
            if self.ship_img == BLUE_SPACE_SHIP:
                laser = Laser(self.x - self.width / 2, self.y, self.laser_img)
            elif self.ship_img == GREEN_SPACE_SHIP:
                laser = Laser(self.x - self.width / 4, self.y, self.laser_img)
            else:
                laser = Laser(self.x - self.width / 3, self.y, self.laser_img)
            # adds the laser to an array held by the ship
            self.lasers.append(laser)
            self.cool_down_counter = 1


# opens a file called "scores" if one is not found, it makes one
def open_score():
    try:
        return open("scores", "r+")
    except IOError:
        return open("scores", "a+")


# gets the high score in the given file
# if lower than current score, it is overwritten with the new high score
def add_score(score, file):
    high_score = file.readlines()
    if len(high_score) != 0:
        high_score = high_score[0]
    else:
        high_score = 0
    if int(high_score) <= score:
        file.close()
        file = open("scores", "w")
        file.write(str(score))


# main loop that handles all events
def main(high_score):
    # run is used to keep the loop going
    run = True
    # lost is used to check to see if the player has lost
    lost = False
    # counter used to display lost message for a certain amount of time before resetting
    lost_time_counter = 0
    # fps is used to keep track of refresh rate of the game
    fps = 120
    # level keeps track of the players current level
    level = 0
    # keeps track of the number of lives the player has
    lives = 5
    # used to display the score of killing enemy ships
    score = 0
    clock = pygame.time.Clock()
    # fonts used to display messages in the game
    main_font = pygame.font.SysFont("comicsans", 50)
    lost_font = pygame.font.SysFont("comicsans", 55)
    # array used to hold all enemies
    enemies = []
    # wave length is used to spawn a certain amount of enemies
    wave_length = 5

    # spawns the player in the middle of the screen
    player = Player(WIN.get_width() / 2, WIN.get_height() / 2)

    # function used to see if two objects have collided (could be two ships or a ship and a laser)
    def collide(obj1, obj2):
        offset_x = obj2.x - obj1.x
        offset_y = obj2.y - obj1.y
        return obj1.mask.overlap(obj2.mask, (offset_x, offset_y))

    # redraws the game every tick update
    def redraw_window():
        # reprints the background to keep after images from appearing
        WIN.blit(bg, (0, 0))
        # printing text
        lives_label = main_font.render(f"lives: {lives}", True, (255, 255, 255))
        level_label = main_font.render(f"level: {level}", True, (255, 255, 255))
        score_label = main_font.render(f"score: {score}", True, (255, 255, 255))
        high_score_label = main_font.render("High Score: " + high_score, True, (255, 255, 255))
        WIN.blit(lives_label, (10, 10))
        WIN.blit(level_label, (WIN.get_width() - level_label.get_width() - 10, 10))
        WIN.blit(score_label,
                 (WIN.get_width() - score_label.get_width() - 10, WIN.get_height() - score_label.get_height() - 10))
        WIN.blit(high_score_label, (10, WIN.get_height() - high_score_label.get_height() - 10))
        # redraws all enemies in the array onto the screen
        for enemy in enemies:
            enemy.draw(WIN)
        # redraws the player
        player.draw(WIN)

        # if the player has lost because lives have gone to zero
        if lost:
            lost_label = lost_font.render("You ran out of lives, you lose!", True, (255, 255, 255))
            WIN.blit(lost_label, (WIN.get_width() / 2 - lost_label.get_width() / 2, WIN.get_height() / 2))
        pygame.display.update()

    # main loop that runs the game
    while run:
        # sets the max rate the program will run to the size of FPS
        clock.tick(fps)
        # updates the background to the current window height and width
        bg = pygame.transform.scale(pygame.image.load("assets/background-black.png"),
                                    (WIN.get_width(), WIN.get_height()))
        # updates the velocities depending on the new width of window
        enemy_vel = 1 * WIN.get_width() / 750
        player_vel = 5 * WIN.get_width() / 750
        laser_vel = 4 * WIN.get_width() / 750

        # calls the redraw_window function to update the game window
        # new ships will not be spawned in the new area (if window is enlarged) until the next wave
        redraw_window()
        # if player's health goes to zero, lower the life counter by 1, then resets the player's health
        if player.health <= 0:
            if lives > 0:
                lives -= 1
            if lives > 0:
                player.health = player.max_health

        # checks to see if the player ran out of lives
        if lives <= 0:
            lost = True
            lost_time_counter += 1
        # used to check to see if the amount of time passed is more than 3 seconds
        # stops the main loop once 3 seconds have passed
        if lost:
            if lost_time_counter > fps * 3:
                file = open("scores", "r")
                add_score(score, file)
                file.close()
                run = False
            else:
                continue

        # checks to see if there are no enemies still alive in the current round
        if len(enemies) == 0:
            # resets the player health to make the game easier
            player.health = player.max_health
            # gives the player 1 life if not at level 0, this is used to help the player on harder levels
            if level != 0:
                lives += 1
            level += 1
            # used to ramp up the amount of enemies that are spawned past wave 10
            if level == 1:
                wave_length = 5
            elif level <= 10:
                wave_length = (5 + (2 * level))
            else:
                wave_length += (5 + (2 * level))
            # spawns the correct amount of enemies in regard to the current wave
            for i in range(wave_length):
                enemy = Enemy(
                    x=0,
                    # randomises the range enemies will spawn so in larger waves, they are not on top of each other
                    y=random.randrange((-1 * (500 + wave_length * 50)), -100),
                    # randomises the color of the new enemies
                    # red has a 10% of being spawned, blue has %20 and green is %70
                    color=random.choices(["red", "blue", "green"], cum_weights=(10, 30, 100), k=1)[0])
                # randomises the x value the enemies are spawned on
                enemy.x = random.randrange(enemy.get_width(), WIN.get_width() - enemy.get_width())
                enemies.append(enemy)
        # ends the program if window is closed
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                return True

        # player input
        # bounds are used to makesure player is not out of bounds of the screen
        keys = pygame.key.get_pressed()
        if (keys[pygame.K_a] or keys[pygame.K_LEFT]) and player.x > 0:  # moving left
            player.x -= player_vel
        if (keys[pygame.K_d] or keys[pygame.K_RIGHT]) and player.x + player.width < WIN.get_width():  # moving right
            player.x += player_vel
        if (keys[pygame.K_w] or keys[pygame.K_UP]) and player.y > 0:  # moving up
            player.y -= player_vel
            # moving down, the 20 represents 20 pixels used for player health_bar
        if (keys[pygame.K_s] or keys[pygame.K_DOWN]) and player.y + player.height + 20 < WIN.get_height():
            player.y += player_vel
        if keys[pygame.K_SPACE]:
            player.shoot()

        # makes a copy of the enemies array and moves each enemy and laser that they have shot so far
        for enemy in enemies[:]:
            enemy.move(enemy_vel)
            enemy.move_lasers(laser_vel, player, WIN.get_height())
            # randomises the chance of the target shooting
            # on average will shoot once every two seconds
            if random.randrange(0, 2 * fps) == 1:
                enemy.shoot()

            # if an enemy and a player are in the space, the enemy is destroyed and the player loses 20 health
            if collide(enemy, player):
                player.health -= 20
                enemies.remove(enemy)
            # checks to see if the enemy is at the bottom of the screen
            # if so, delete the enemy and player loses a life
            elif enemy.y + enemy.get_height() > WIN.get_height():
                lives -= 1
                enemies.remove(enemy)
        # calls function to move all of player's lasers and
        # updates the score for enemies shot by the player
        score += player.move_lasers((-1 * (laser_vel + 3)), enemies)


# main menu that the player first sees, they also see it after losing a game
def main_menu():
    run = True
    title_font = pygame.font.SysFont("comicsans", 70)
    score_font = pygame.font.SysFont("comicsans", 30)

    while run:
        file = open_score()
        score = file.readline()

        # updates the size of the background to fit the size of the window
        bg = pygame.transform.scale(pygame.image.load("assets/background-black.png"),
                                    (WIN.get_width(), WIN.get_height()))
        WIN.blit(bg, (0, 0))
        title_label = title_font.render("left click to begin...", True, (255, 255, 255))

        WIN.blit(title_label, (WIN.get_width() / 2 - title_label.get_width() / 2, 250))

        score_label = score_font.render("High Score: " + score, True, (255, 255, 255))
        WIN.blit(score_label, (
            WIN.get_width() / 2 - score_label.get_width() / 2, 500 + (2 * title_label.get_height() / 3)))

        pygame.display.update()
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                run = False
            # if player clicks the mouse, starts the game
            if event.type == pygame.MOUSEBUTTONDOWN:
                file.close()
                if main(score):
                    return
    pygame.quit()


main_menu()
