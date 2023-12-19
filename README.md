# Project desription
This was a project that I did for the object-oriented programming course in December 2022. The goal was to create a Java application with a simulation of a world of predators and prey. The map consists of paths (gray color), interchanges (yellow), hideouts, and resources like food and water.

Predators (black squares) can step on every field except for hideouts. When they are hungry (low energy), they hunt for prey, and if they manage to kill it, they replenish their energy.

Prey can only move using paths. There can't be more than one prey on the interchange at the same time, so I used concurrent programming to handle that. Prey need to visit resource fields to replenish their energy and water. In hideouts, they can recover their HP and reproduce. Clicking on each animal or relevant field opens a window with information about the object. The user can also change the prey's destination by clicking on the desired field, pressing "select" in the field information window, and then clicking on the prey and pressing "go to the selected field" in the prey information window.
#### The graphics is really sketchy, as I didn't have time to polish it. When I was working on the project, I used NetBeans IDE, but unfortunately without git. I upload it now here as an example of a project that I did during my studies
## To run the project using NetBeans IDE:
1. Launch NetBeans IDE.
2. Choose File > Open Project.
3. Navigate to the location where you cloned the repository and select the project folder.
4. Click Open Project.
5. In NetBeans, right-click on the project in the "Projects" pane.
6. Choose Build from the context menu.
7. Wait for the build process to complete.
8. After a successful build, right-click click again on the project in the "Projects" pane.
9. Choose Run from the context menu.
The application should now start running.
