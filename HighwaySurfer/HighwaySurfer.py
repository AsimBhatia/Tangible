import pygame, time, math, random, sys, winsound
from Highscores import highscoreList as scoreList
from Highscores import nameList as nameList

'''
http://programarcadegames.com/index.php?lang=en&chapter=back_to_looping
'''
 
# Define some colors
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)
GREEN = (0, 255, 0)
RED = (255, 0, 0)
GREY = (128, 128, 128)
 
# Loop until the user clicks the close button.
done = False
 
# Used to manage how fast the screen updates
clock = pygame.time.Clock()

#loads up the image
trafficImg = pygame.image.load('car2.png')
titleImg = pygame.image.load('gameTitlePage.png')
leaderboardImg = pygame.image.load('lBoardBackground.png')
controlsImg = pygame.image.load('controlsBackground.png')
garageImg = pygame.image.load('garageBackground.png')

greenCar = pygame.image.load('greenCar.png')
blueCar = pygame.image.load('blueCar.png')
greyCar = pygame.image.load('greyCar.png')
orangeCar = pygame.image.load('orangeCar.png')
purpleCar = pygame.image.load('purpleCar.png')
redCar = pygame.image.load('redCar.png')
whiteCar = pygame.image.load('whiteCar.png')
yellowCar = pygame.image.load('yellowCar.png')

carImg = whiteCar

#defines a function that displays the image
def car(x,y):
    screen.blit(carImg,(x,y))

def traffic(x,y):
    screen.blit(trafficImg,(x,y))

def drawTraffic(trafficY):
    if trafficLane == 1:
        traffic(lane1x, trafficY)
    elif trafficLane == 2:
        traffic(lane2x, trafficY)
    else:
        traffic(lane3x, trafficY)

    trafficY += 1

def roadLines(x, y, width, height, color):
    pygame.draw.rect(screen, color, [x, y, width, height])

def clearFrame():
    screen.fill(GREY)
        
    roadLines(195, y1, 10, 100, WHITE)
    roadLines(395, y1, 10, 100, WHITE)

    roadLines(195, y1+(175*1), 10, 100, WHITE)
    roadLines(395, y1+(175*1), 10, 100, WHITE)
    
    roadLines(195, y1+(175*2), 10, 100, WHITE)
    roadLines(395, y1+(175*2), 10, 100, WHITE)

    roadLines(195, y1+(175*3), 10, 100, WHITE)
    roadLines(395, y1+(175*3), 10, 100, WHITE)

    roadLines(195, y1+(175*4), 10, 100, WHITE)
    roadLines(395, y1+(175*4), 10, 100, WHITE)

    roadLines(195, y1+(175*5), 10, 100, WHITE)
    roadLines(395, y1+(175*5), 10, 100, WHITE)

    car(currentx, screenHeight-180)

def text_objects(text, font, color):
                       #render(text, antialias, color, background=None) -> Surface(in this case = textSurface)
    textSurface = font.render(text, True, color)
    return textSurface, textSurface.get_rect()

def messageDisplay(text, x, y, size, color):
    largeText = pygame.font.Font('freesansbold.ttf', size)
    textSurf, textRect = text_objects(text, largeText, color)
    textRect.center = (x, y)
    screen.blit(textSurf, textRect)
    pygame.display.update()

def crash():
    messageDisplay('You Crashed', (screenWidth/2), (screenHeight*(1/4)), 75, GREEN)
    messageDisplay("Score: "+(str(int(score))), (screenWidth/2), (screenHeight*(1/2)), 45, GREEN)



begin = True

while begin == True:
    # Set the width and height of the screen [width, height]
    pygame.init()

    screenWidth = 600
    screenHeight = 800
    size = [screenWidth, screenHeight]
    screen = pygame.display.set_mode(size)

    pygame.display.set_caption("Highway Surfers")

    screen.blit(titleImg,(0,0))
    pygame.display.flip()

    showLeaderboard = False
    showControls = False
    showGarage = False
    start = False
    
    while start == False:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
                break
            
            mouseX, mouseY = pygame.mouse.get_pos()
            if mouseX > 17 and mouseX < 247 and mouseY > 437 and mouseY < 477:
                pygame.draw.rect(screen, BLACK, ((5, 430), (255, 50)), 2)
            elif mouseX > 374 and mouseX < 598 and mouseY > 408 and mouseY < 442:
                pygame.draw.rect(screen, BLACK, ((378, 400), (220, 45)), 2)
            elif mouseX > 452 and mouseX < 598 and mouseY > 461 and mouseY < 493:
                pygame.draw.rect(screen, BLACK, ((450, 458), (148, 40)), 2)
            elif mouseX > 475 and mouseX < 598 and mouseY > 516 and mouseY < 559:
                pygame.draw.rect(screen, BLACK, ((475, 516), (123, 43)), 2)
            else:
                screen.blit(titleImg,(0,0))

            pygame.display.flip()
            
            
            if event.type == pygame.MOUSEBUTTONDOWN:
                mouseX, mouseY = pygame.mouse.get_pos()
                if mouseX > 17 and mouseX < 247 and mouseY > 437 and mouseY < 477:
                    start = True
                elif mouseX > 374 and mouseX < 598 and mouseY > 408 and mouseY < 442:
                    showLeaderboard = True
                    back = False
                elif mouseX > 452 and mouseX < 598 and mouseY > 461 and mouseY < 493:
                    showControls = True
                    back = False
                elif mouseX > 475 and mouseX < 598 and mouseY > 516 and mouseY < 559:
                    showGarage = True
                    back = False
                    
            
            if showLeaderboard == True:
                p1 = str(nameList[0]) + " -------- " + str(scoreList[0])
                p2 = str(nameList[1]) + " -------- " + str(scoreList[1])
                p3 = str(nameList[2]) + " -------- " + str(scoreList[2])
                p4 = str(nameList[3]) + " -------- " + str(scoreList[3])
                p5 = str(nameList[4]) + " -------- " + str(scoreList[4])
                
                while back == False:
                    for event in pygame.event.get():
                        if event.type == pygame.QUIT:
                            pygame.quit()
                            sys.exit()
                        
                        mouseX, mouseY = pygame.mouse.get_pos()

                        if mouseX > 11 and mouseX < 115 and mouseY > 45 and mouseY < 115:
                            pygame.draw.rect(screen, BLACK, ((11, 45), (104, 50)), 2)
                        else:
                            screen.blit(leaderboardImg,(0,0))
                            
                            messageDisplay(p1, screenWidth/2, 350, 25, BLACK)
                            messageDisplay(p2, screenWidth/2, 400, 25, BLACK)
                            messageDisplay(p3, screenWidth/2, 450, 25, BLACK)
                            messageDisplay(p4, screenWidth/2, 500, 25, BLACK)
                            messageDisplay(p5, screenWidth/2, 550, 25, BLACK)
                            time.sleep(0.3)
                        
                        if event.type == pygame.MOUSEBUTTONDOWN:
                           if mouseX > 11 and mouseX < 115 and mouseY > 45 and mouseY < 95:
                               back = True
                               showLeaderboard = False

                    pygame.display.flip()

            
            if showControls == True:
                screen.blit(controlsImg, (0, 0))
                while back == False:
                    for event in pygame.event.get():
                        if event.type == pygame.QUIT:
                            pygame.quit()
                            sys.exit()
                        
                        mouseX, mouseY = pygame.mouse.get_pos()

                        if mouseX > 11 and mouseX < 115 and mouseY > 45 and mouseY < 115:
                            pygame.draw.rect(screen, BLACK, ((11, 45), (104, 50)), 2)
                        else:
                            screen.blit(controlsImg,(0,0))
                        
                        if event.type == pygame.MOUSEBUTTONDOWN:
                           if mouseX > 11 and mouseX < 115 and mouseY > 45 and mouseY < 95:
                               back = True
                               showControls = False

                    pygame.display.flip()


            if showGarage == True:
                screen.blit(garageImg, (0, 0))
                while back == False:
                    for event in pygame.event.get():
                        if event.type == pygame.QUIT:
                            pygame.quit()
                            sys.exit()
                        
                        mouseX, mouseY = pygame.mouse.get_pos()

                        if mouseX > 11 and mouseX < 115 and mouseY > 45 and mouseY < 115:
                            pygame.draw.rect(screen, BLACK, ((11, 45), (104, 50)), 2)
                        elif mouseX > 44 and mouseX < 134 and mouseY > 338 and mouseY < 525:
                            pygame.draw. rect(screen, BLACK, ((44, 338), (90, 187)), 2)
                        elif mouseX > 177 and mouseX < 272 and mouseY > 338 and mouseY < 525:
                            pygame.draw. rect(screen, BLACK, ((180, 338), (90, 187)), 2)
                        elif mouseX > 316 and mouseX < 408 and mouseY > 338 and mouseY < 525:
                            pygame.draw. rect(screen, BLACK, ((323, 338), (90, 187)), 2)
                        elif mouseX > 463 and mouseX < 562 and mouseY > 338 and mouseY < 525:
                            pygame.draw. rect(screen, BLACK, ((466, 338), (90, 187)), 2)


                        elif mouseX > 44 and mouseX < 134 and mouseY > 554 and mouseY < 733:
                            pygame.draw. rect(screen, BLACK, ((44, 550), (90, 187)), 2)
                        elif mouseX > 177 and mouseX < 272 and mouseY > 554 and mouseY < 733:
                            pygame.draw. rect(screen, BLACK, ((180, 550), (90, 187)), 2)
                        elif mouseX > 316 and mouseX < 408 and mouseY > 554 and mouseY < 733:
                            pygame.draw. rect(screen, BLACK, ((323, 550), (90, 187)), 2)
                        elif mouseX > 463 and mouseX < 562 and mouseY > 554 and mouseY < 733:
                            pygame.draw. rect(screen, BLACK, ((466, 550), (90, 187)), 2)


                            
                        else:
                            screen.blit(garageImg,(0,0))
                        
                        if event.type == pygame.MOUSEBUTTONDOWN:

                            if mouseX > 44 and mouseX < 134 and mouseY > 338 and mouseY < 525:
                                carImg = greenCar
                            elif mouseX > 180 and mouseX < 272 and mouseY > 338 and mouseY < 525:
                                carImg = purpleCar
                            elif mouseX > 316 and mouseX < 408 and mouseY > 338 and mouseY < 525:
                                carImg = yellowCar
                            elif mouseX > 463 and mouseX < 562 and mouseY > 338 and mouseY < 525:
                                carImg = greyCar
                            elif mouseX > 44 and mouseX < 134 and mouseY > 554 and mouseY < 733:
                                carImg = blueCar 
                            elif mouseX > 177 and mouseX < 272 and mouseY > 554 and mouseY < 733:
                                carImg = whiteCar
                            elif mouseX > 316 and mouseX < 408 and mouseY > 554 and mouseY < 733:
                                carImg = orangeCar
                            elif mouseX > 463 and mouseX < 562 and mouseY > 554 and mouseY < 733:
                                carImg = redCar
                            
                            if mouseX > 11 and mouseX < 115 and mouseY > 45 and mouseY < 95:
                               back = True
                               showGarage = False

                        currentCar = pygame.transform.scale(carImg, (60, 120))
                        screen.blit(currentCar, (50,200))
                        pygame.draw. rect(screen, BLACK, ((46, 195), (70, 130)), 2)

                    pygame.display.flip()
    
    y1 = -100
    trafficY = -174

    #Car's starting speed
    speed = 5
    laneNum = 2

    #Car's x location at each of the lanes
    lane1x = 55
    lane2x = 256
    lane3x = 457
    currentx = lane2x

    #Player's starting score.
    score = 0

    #This is the background sound.
    pygame.mixer.music.load('backgroundMusic.wav')
    pygame.mixer.music.play(loops = 1000)
    
    # -------- Main Program Loop -----------
    while not done:
        # --- Main event loop
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                sys.exit()
            if event.type == pygame.KEYDOWN:
                #LEFT
                if event.key == pygame.K_LEFT and laneNum != 1:
                    laneNum -= 1
                #RIGHT
                elif event.key == pygame.K_RIGHT and laneNum != 3:
                    laneNum +=1
                else:
                    laneNum = laneNum
                if event.key == pygame.K_UP:
                    speed = speed * 1.3
                    score += speed * 1.5
     
        # --- Game logic should go here
        if trafficY == -174:
            trafficLane = random.randint(1,3)


        if trafficLane == 1:
            trafficx = lane1x
        elif trafficLane == 2:
            trafficx = lane2x
        else:
            trafficx = lane3x

        carRect = pygame.Rect(currentx, screenHeight-180, 87, 174)
        trafficRect = pygame.Rect(trafficx, y1, 83, 175)

        # --- Screen-clearing code goes here
     
        # Here, I clear the screen to grey. Don't put other drawing commands
        # above this, or they will be erased with this command.
        # If you want a background image, replace this clear with blit'ing the
        # background image.
        screen.fill(GREY)
     
        # --- Drawing code should go here        
        y1 += speed
        speed += 0.005
            
        roadLines(195, y1, 10, 100, WHITE)
        roadLines(395, y1, 10, 100, WHITE)

        roadLines(195, y1+(175*1), 10, 100, WHITE)
        roadLines(395, y1+(175*1), 10, 100, WHITE)
        
        roadLines(195, y1+(175*2), 10, 100, WHITE)
        roadLines(395, y1+(175*2), 10, 100, WHITE)

        roadLines(195, y1+(175*3), 10, 100, WHITE)
        roadLines(395, y1+(175*3), 10, 100, WHITE)

        roadLines(195, y1+(175*4), 10, 100, WHITE)
        roadLines(395, y1+(175*4), 10, 100, WHITE)

        roadLines(195, y1+(175*5), 10, 100, WHITE)
        roadLines(395, y1+(175*5), 10, 100, WHITE)

        if laneNum == 1:
            if currentx > lane1x:
                currentx -= 20
                clearFrame()
                car(currentx, screenHeight-180)
                drawTraffic(trafficY)
                pygame.display.flip()
        elif laneNum == 2:
            if currentx > lane2x:
                if currentx > lane2x:
                    currentx -= 20
                    clearFrame()
                    car(currentx, screenHeight-180)
                    drawTraffic(trafficY)
                    pygame.display.flip()
            else:
                if currentx < lane2x:
                    currentx += 20
                    clearFrame()
                    car(currentx, screenHeight-180)
                    drawTraffic(trafficY)
                    pygame.display.flip()
        else:
            if currentx < lane3x:
                currentx += 20
                clearFrame()
                car(currentx, screenHeight-180)
                drawTraffic(trafficY)
                pygame.display.flip()

        car(currentx, screenHeight-180)

        if trafficLane == 1:
            traffic(lane1x, trafficY)
        elif trafficLane == 2:
            traffic(lane2x, trafficY)
        else:
            traffic(lane3x, trafficY)

        trafficY += speed - 5
                    
        if trafficY >= (screenHeight + 174):
            trafficY = -174

        if y1 > screenHeight-screenHeight+100:
            y1 = -75

        #Displays the current score.            
        messageDisplay(str(int(score)), screenWidth - 75, 20, 25, WHITE)

        
        # --- Go ahead and update the screen with what we've drawn.
        pygame.init()
        pygame.display.flip()
        
        #Checking for collisions here
        if trafficLane == 1:
            if trafficLane == laneNum and currentx <= lane1x and trafficY >= (screenHeight-180-170) and trafficY < screenHeight:
                screen.fill(BLACK)
                crash()
                time.sleep(1)
                break
        elif trafficLane == 2:
            if trafficLane == laneNum and currentx == lane2x and trafficY >= (screenHeight-180-170) and trafficY < screenHeight:
                screen.fill(BLACK)
                crash()
                time.sleep(1)
                break
        else:
            if trafficLane == laneNum and currentx >= lane3x and trafficY >= (screenHeight-180-170) and trafficY < screenHeight:
                screen.fill(BLACK)
                crash()
                time.sleep(1)
                break
        
        score += speed 
        # --- Limit to 60 frames per second
        clock.tick(60)

    # Close the window and quit.
    pygame.quit()

    score = int(score)
    
    print("Your score was", score)

    scoreList = list(scoreList)
    nameList = list(nameList)
    
    if score > scoreList[4]:
        del scoreList[4]
        del nameList[4]
        print("Leaderborad Score!!!")

        name = input("Congratulations, what is your name fine driver: ")
        
        if score > scoreList[0]:
            print("NEW HIGHSCORE!!!")
            scoreList.insert(0, score)
            nameList.insert(0, name)
            with open('Highscores.py', 'w') as f:
                f.write('highscoreList = %s\n' % scoreList)
                f.write('nameList = %s' % nameList)
        elif score > scoreList[1]:
            scoreList.insert(1, score)
            nameList.insert(1, name)
            with open('Highscores.py', 'w') as f:
                f.write('highscoreList = %s\n' % scoreList)
                f.write('nameList = %s' % nameList)
        elif score > scoreList[2]:
            scoreList.insert(2, score)
            nameList.insert(2, name)
            with open('Highscores.py', 'w') as f:
                f.write('highscoreList = %s\n' % scoreList)
                f.write('nameList = %s' % nameList)
        elif score > scoreList[3]:
            scoreList.insert(3, score)
            nameList.insert(3, name)
            with open('Highscores.py', 'w') as f:
                f.write('highscoreList = %s\n' % scoreList)
                f.write('nameList = %s' % nameList)
        else:
            scoreList.append(score)
            nameList.append(name)
            with open('Highscores.py', 'w') as f:
                f.write('highscoreList = %s\n' % scoreList)
                f.write('nameList = %s' % nameList)

    play = (input("Would you like to return to main menu? (Y/N): ")).lower()
    while play != "y" and play != "n":
        play = (input("Would you like to return to main menu? (Y/N): ")).lower()
    if play == "y":
        begin = True
    else:
        begin = False


with open('Highscores.py', 'w') as f:
    f.write('highscoreList = %s\n' % scoreList)
    f.write('nameList = %s' % nameList)

print("\nThank you for playing.")
print("\n\nLeaderboard:\n")
for i in range(5):
    print(i+1, end = ". ")
    print(nameList[i], end = " ----- ")
    print(scoreList[i])

