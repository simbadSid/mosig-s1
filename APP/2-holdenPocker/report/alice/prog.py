	

    # This function returns an array of cards which you should take to win a game.
    def findBestWay(cards, mySum=0, sisterSum=0, myTurn=None):
        if len(cards) == 1:
            if myTurn:
                mySum += cards[0]
            else:
                sisterSum += cards[0]
            if mySum > sisterSum:
                return [cards[0]]
            return []
            
            
            
            
        if myTurn is None or myTurn == True:
            pickLeftResult = findBestWay(cards[1:], mySum + cards[0], sisterSum, False)
            if len(pickLeftResult) > 0:
                return [cards[0]] + pickLeftResult
            pickRightResult = findBestWay(cards[:-2], mySum + cards[-1], sisterSum, False)
            if len(pickLeftResult) > 0:
                return [cards[-1]] + pickRightResult





        if myTurn is None or myTurn == False:
            skipLeftResult = findBestWay(cards[1:], mySum, sisterSum + cards[0], True)
            if skipLeftResult:
                return skipLeftResult
            skipRightResult = findBestWay(cards[:-2], mySum, sisterSum + cards[-1], True)
            if skipRightResult:
                return skipRightResult


        return []


