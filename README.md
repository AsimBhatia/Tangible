Tangible
Tangible is a Universal Touch Screen hardware add-on that can be installed on any monitor to enable touch-functionality. It allows for the modernization of old computing setups without the need to replace all of the original hardware.

Gallery
scroll

Implementation Details
Hardware
Tangible uses six HC-SR04 Ultrasonic Distance Sensors arranged in a row along the top edge of the screen. The sensors are held in place using a cardboard frame that sits atop the monitor. Each sensor has a functional range of 2 cm to 400 cm, and a precision of 3 mm.

Control
The Tangible hardware is controlled by an Arduino Leonardo. The sensor code (sensors.ino) on the Arduino, written in C++, is responsible for activating the ultrasonic sensors, as well as reading and processing their inputs. The software is able to calculate the distance from a given sensor to the nearest obstruction (i.e. a finger) by measuring the time it takes for a pulse of sound to return to the sensor after firing. This time delay is multiplied by the speed of sound at room temperature and is divided by two.

Upon startup, the user places their palm at the bottom right corner of their display as the software takes an initial measurement of the user’s screen height for calibration by activating the rightmost sensor and storing the measured distance. Moreover, this calibration process generalizes for any screen height.

Signal Processing
Depending on the current context, the user’s touch input is interpreted using either a discrete or continuous method. For discrete touches, the sensor code divides the user’s screen into a 4x6 array, with each of the six sensors being responsible for their respective column. The raw measurement of the user’s vertical finger position measured by a given sensor is translated to a position in one of the four cells in a column, by converting that distance into a percentage of the recorded screen height. Distances greater than the screen height are ignored. This process maps the user’s finger position to a two-dimensional, binary-valued touchMap array which is later used to determine coordinates for the cursor, or as virtual buttons to be mapped to app-specific key inputs. In the continuous touch mode, we store position information from the previous loop and compare it to that of the current loop. This allows us to calculate the position derivative v_swipe=ds/dt and execute actions (such as scrolling, dragging, or drawing) in a manner proportional to the velocity of the gesture. As an additional preprocessing step, all distances are first normalized to the screen height.

Signal Noise Reduction
Several techniques were used to improve the reliability of our device and overcome undesired sensor behaviour. For discrete touches, we use Boolean logic on two bool arrays, hasTouchInput and alreadyHasTouchInput, to determine whether a given input is intentional or a false positive reading that was previously detected. This helped prevent multiple “phantom” touches, and allowed us to have precise control over when the device was actively looking for a new input.

For continuous touch and swipe gestures, such as velocity-based scrolling, we calculate v_swipe=ds/dt using position information from two consecutive loops. However, the high frequency of loops executed by the Arduino (about 117kHz) resulted in ds/dt=0 between each loop. At the same time, slowing down the refresh rate would cause the scroll movements themselves to appear choppy. Thus, we implemented a modularly-conditional input scheme using a variable loopCounter in Z_10={0,1,2,3,4,5,6,7,8,9} so that while the screen would always update, input measurements would only be made 10% of the time. This greatly enhanced the intuitive feel of the swipe gestures.

Software
To showcase the various features of our device, we developed a set of custom applications that are similar to ones found on a typical touchscreen mobile device. The following apps have been integrated into a single home screen interface called TangibleOS, in which users can quickly hop between apps and have Tangible automatically adjust the way it interprets touch input.

Asteroids is an exciting arcade-style space shooter which allows the user to maneuver a rocket ship in a 2D plane with the objective to try to dodge and destroy asteroids using Tangible’s 4x6 grid mapping to simulate on-screen buttons.
Jump! is a minimalist platformer proof-of-concept that also uses a similar button-mapping technique. We worked on adjusting these controls to make gameplay feel more natural to the user.
Bin2Dec is a binary to decimal number converter, and was designed as a convenient utility inside our operating system with a highly accessible input format.
TangibleGuitar features a playable acoustic guitar that users can strum or pluck. The aim of this app is to demonstrate the accuracy of Tangible’s continuous touch input on the vertical axis. The sounds for this app were created by recording the individual strings of an actual acoustic guitar.
TangibleInk is a simple drawing app that uses a unique joystick-like input to direct the motion of the virtual paintbrush.
HighwaySurfers is an arcade game in which the player steers their car to avoid crashing into other vehicles on the highway. This game showcases a special application of Tangible’s continuous input: simulating a steering wheel. To determine the degree of rotation of this virtual steering wheel, the slope between the measured points of the two hands was calculated and translated into a speed and direction for the in-game car.