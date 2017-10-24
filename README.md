# Solution
The code is written in Java by Dan Hou.</br>
There are four classes implementing the requirements. 

##Assumptions:
* All coordinates are integers.
* 70% locations have events. 
* There are at most 5 types of tickets of an event.
* The maximum price is 100 dollars.

These three number can be changed in program when generating randomly seed data.

##How to run
Compile the project with `javac *.java`<br/>
Run the project with `java EventFinder` <br/>
Then follow the instruction to find the five closest events with a user location input.

##Discussion
If a location can hold multiple events, I can make Location as an attribute of Event. Therefore, each event has a location and each location can hold multiple events.

I write two functions to find closest events. One is using BFS. I choose user location as the start point and check each further distance level if there is an event with ticket. Once I find the exact number of event, I stop BFS and return result. The other function is sorting all locations by the distance from user point and choose 5 closest location. 

Since I’m working with a small world size, the solution sorting all locations is sometimes even faster than BFS solution. But if I work with a much larger world size, the BFS solution is more efficient. I only need to find the closest location without sorting all points.