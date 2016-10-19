import random
from collections import namedtuple


Point = namedtuple('Point', ['x', 'y'])


Wall = namedtuple('Wall', ['startPoint', 'endPoint', 'doorPoint'])


class MazeWall(object):

    def __init__(self, startP, endP, doorP):
        self.__startPoint = startP
        self.__endPoint = endP
        self.__doorPoint = doorP


class Maze(object):

    def __init__(self, width, height, startDoor, endDoor):
        self.__width = width
        self.__height = height
        self.__startDoor = startDoor
        self.__endDoor = endDoor
        self.__walls = []
        self.__path = ([self.__startDoor] +
                   self.createWallsAndPath(startDoor=self.__startDoor, endDoor=self.__endDoor) +
                   [self.__endDoor])

    # @return: path
    def createWallsAndPath(self, leftTop=None, rightBottom=None, startDoor=None, endDoor=None):
        if leftTop is None:
            leftTop = Point(0, 0)
        if rightBottom is None:
            rightBottom = Point(self.__width, self.__height)
        minSize = min(rightBottom.x - leftTop.x, rightBottom.y - leftTop.y)
        path = []
        if minSize < 1:
            return path
        fSDoor = fEDoor = sSDoor = sEDoor = None
        if minSize == rightBottom.x - leftTop.x:
            wallYCoord = random.randint(leftTop.y, rightBottom.y - 1)
            doorXCoord = random.randint(leftTop.x, rightBottom.x)
            wall = Wall(Point(leftTop.x, wallYCoord), Point(rightBottom.x, wallYCoord),
                        Point(doorXCoord, wallYCoord))
            # create new door path
            if startDoor != endDoor:
                if startDoor.y <= wallYCoord:
                    if wallYCoord < endDoor.y:
                        sSDoor = fEDoor = Point(doorXCoord, wallYCoord)
                        fSDoor = startDoor
                        sEDoor = endDoor
                        path = [sSDoor]
                    else:
                        sSDoor = sEDoor = None
                        fSDoor = startDoor
                        fEDoor = endDoor
                else:
                    if wallYCoord < endDoor.y:
                        sSDoor = startDoor
                        sEDoor = endDoor
                        fSDoor = fEDoor = None
                    else:
                        fSDoor = startDoor
                        fEDoor = sSDoor = Point(doorXCoord, wallYCoord)
                        sEDoor = endDoor
                        path = [fEDoor]
            path += self.createWallsAndPath(Point(wall.startPoint.x, wall.startPoint.y + 1),
                                    rightBottom, sSDoor, sEDoor)
        else:
            wallXCoord = random.randint(leftTop.x, rightBottom.x - 1)
            doorYCoord = random.randint(leftTop.y, rightBottom.y)
            wall = Wall(Point(wallXCoord, leftTop.y), Point(wallXCoord, rightBottom.y),
                        Point(wallXCoord, doorYCoord))
            if startDoor != endDoor:
                if startDoor.x <= wallXCoord:
                    if wallXCoord < endDoor.x:
                        sSDoor = fEDoor = Point(wallXCoord, doorYCoord)
                        fSDoor = startDoor
                        sEDoor = endDoor
                        path = [sSDoor]
                    else:
                        sSDoor = sEDoor = None
                        fSDoor = startDoor
                        fEDoor = endDoor
                else:
                    if wallXCoord < endDoor.x:
                        sSDoor = startDoor
                        sEDoor = endDoor
                        fSDoor = fEDoor = None
                    else:
                        fSDoor = startDoor
                        fEDoor = sSDoor = Point(wallXCoord, doorYCoord)
                        sEDoor = endDoor
                        path = [fEDoor]
            path += self.createWallsAndPath(Point(wall.startPoint.x + 1, wall.startPoint.y),
                                    rightBottom, sSDoor, sEDoor)
        path = self.createWallsAndPath(leftTop, wall.endPoint, fSDoor, fEDoor) + path
        self.__walls.append(wall)
        return path

    def getWalls(self):
        return self.__walls

    def getPath(self):
        return self.__path


maze = Maze(6, 4, Point(0, 0), Point(6, 4))
print maze.getWalls()
print maze.getPath()
