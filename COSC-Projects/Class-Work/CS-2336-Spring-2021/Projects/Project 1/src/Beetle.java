public class Beetle extends Creature
{
    private
    int starveCounter=1;
    
    public
    //movement function is the brain of the beetle and decides which way it goes
    char movement(Character[][] array, Character Ant, Character Beetle)
    {
        int northDistance=0,eastDistance=0,southDistance=0,westDistance=0;

        // check north distance for amt
        for(int i=rowLoco;i>=0;i--)
        {
            if(array[colLoco][i] == Ant)
            {
                northDistance=rowLoco-i;
                break;
            }
        }
        // check south distance for amt 
        for(int i=rowLoco;i<10;i++)
        {
            if(array[colLoco][i] == Ant)
            {
                southDistance=i-rowLoco;
                break;
            }
        }
        // check east distance for amt 
        for(int i=colLoco;i<10;i++)
        {
            if(array[i][rowLoco] == Ant)
            {
                eastDistance=i-colLoco;
                break;
            }
        }
        // check west distance for amt 
        for(int i= colLoco;i>=0;i--)
        {
            if(array[i][rowLoco] == Ant)
            {
                westDistance=colLoco-i;
                break;
            }
        }
        //if no ants are found
        if(northDistance == 0 && eastDistance == 0 && southDistance == 0 && westDistance == 0)
        {
            //finds the distance to each edge of the grid
            northDistance=Math.abs(-rowLoco);
            eastDistance=Math.abs(9-colLoco);
            southDistance=Math.abs(9-rowLoco);
            westDistance=Math.abs(-colLoco);
            
            //if 4 way tie for farthest edge
            if(northDistance == eastDistance && northDistance == southDistance && northDistance ==westDistance)
                return 'N';
            
            //find farthest edge from current beetle
            int farthest =northDistance;
            
            if(farthest<eastDistance)
                farthest=eastDistance;
            if(farthest<southDistance)
                farthest=southDistance;
            if(farthest<westDistance)
                farthest=westDistance;
            
            if(farthest == northDistance)
                return 'N';
            if(farthest == eastDistance)
                return 'E';
            if(farthest == southDistance)
                return 'S';
            else
                return 'W';
        }
        //if ants are found
        else
        {
            //finds the closest ant
            int nearest;
            if(northDistance!=0)
                nearest =northDistance;
            else if(eastDistance!=0)
                nearest = eastDistance;
            else if(southDistance!=0)
                nearest = southDistance;
            else
                nearest = westDistance;
                
            
            if(nearest>eastDistance && eastDistance != 0)
                nearest=eastDistance;
            if(nearest>southDistance && southDistance != 0)
                nearest=southDistance;
            if(nearest>westDistance && westDistance != 0)
                nearest=westDistance;
            
            //4 way tie for nearest ant
            if(nearest == northDistance && nearest == eastDistance && nearest == southDistance && nearest ==westDistance && nearest != 0)
            {
                int antsNorth,antsSouth,antsEast,antsWest;
                
                //finding ants around north ant
                antsNorth=getAntsAreaNS(array,rowLoco-northDistance, Ant);
                
                //finding ants around south ant
                antsSouth=getAntsAreaNS(array,rowLoco+southDistance, Ant);
                
                //finding ants around east ant
                antsEast=getAntsAreaEW(array,colLoco+eastDistance, Ant);
                
                //finding ants around west ant
                antsWest=getAntsAreaEW(array,colLoco-westDistance, Ant);
                
                //find which one has the most ants
                int mostAnts=antsNorth;
                if(mostAnts<antsEast)
                    mostAnts = antsEast;
                if(mostAnts<antsSouth)
                    mostAnts = antsSouth;
                if(mostAnts<antsWest)
                    mostAnts = antsWest;
                
                //returning spot with most ants
                if(antsNorth == mostAnts)
                    return 'N';
                if(antsEast == mostAnts)
                    return 'E';
                if(antsSouth == mostAnts)
                    return 'S';
                else
                    return 'W';

            }
            
            //3 way tie for nearest ant
            if(nearest == eastDistance && nearest == southDistance && nearest == westDistance && nearest != 0)
            {
                int antsSouth,antsEast,antsWest;

                //finding ants around south ant
                antsSouth=getAntsAreaNS(array,rowLoco+southDistance, Ant);

                //finding ants around east ant
                antsEast=getAntsAreaEW(array,colLoco+eastDistance, Ant);

                //finding ants around west ant
                antsWest=getAntsAreaEW(array,colLoco-westDistance, Ant);

                //find which one has the most ants
                int mostAnts =antsEast;
                if(mostAnts<antsSouth)
                    mostAnts = antsSouth;
                if(mostAnts<antsWest)
                    mostAnts = antsWest;

                //returning spot with most ants
                if(antsEast == mostAnts)
                    return 'E';
                if(antsSouth == mostAnts)
                    return 'S';
                else
                    return 'W';
            }
            if(nearest == northDistance && nearest == southDistance && nearest == westDistance && nearest != 0)
            {
                int antsSouth,antsNorth,antsWest;

                //finding ants around south ant
                antsSouth=getAntsAreaNS(array,rowLoco+southDistance, Ant);

                //finding ants around north ant
                antsNorth=getAntsAreaNS(array,rowLoco-northDistance, Ant);

                //finding ants around west ant
                antsWest=getAntsAreaEW(array,colLoco-westDistance, Ant);

                //find which one has the most ants
                int mostAnts =antsNorth;
                if(mostAnts<antsSouth)
                    mostAnts = antsSouth;
                if(mostAnts<antsWest)
                    mostAnts = antsWest;

                //returning spot with most ants
                if(antsNorth == mostAnts)
                    return 'N';
                if(antsSouth == mostAnts)
                    return 'S';
                else
                    return 'W';
            }

            if(nearest == eastDistance && nearest == northDistance && nearest == westDistance && nearest != 0)
            {
                int antsNorth,antsEast,antsWest;

                //finding ants around north ant
                antsNorth=getAntsAreaNS(array,rowLoco-northDistance, Ant);

                //finding ants around east ant
                antsEast=getAntsAreaEW(array,colLoco+eastDistance, Ant);

                //finding ants around west ant
                antsWest=getAntsAreaEW(array,colLoco-westDistance, Ant);

                //find which one has the most ants
                int mostAnts =antsEast;
                if(mostAnts<antsNorth)
                    mostAnts = antsNorth;
                if(mostAnts<antsWest)
                    mostAnts = antsWest;

                //returning spot with most ants
                if(antsNorth == mostAnts)
                    return 'N';
                if(antsEast == mostAnts)
                    return 'E';
                else
                    return 'W';
            }

            if(nearest != 0 && nearest == eastDistance && nearest == southDistance && nearest == northDistance)
            {
                int antsSouth,antsEast,antsNorth;

                //finding ants around south ant
                antsSouth=getAntsAreaNS(array,rowLoco+southDistance, Ant);

                //finding ants around east ant
                antsEast=getAntsAreaEW(array,colLoco+eastDistance, Ant);

                //finding ants around north ant
                antsNorth=getAntsAreaNS(array,rowLoco-northDistance, Ant);

                //find which one has the most ants
                int mostAnts =antsEast;
                if(mostAnts<antsSouth)
                    mostAnts = antsSouth;
                if(mostAnts<antsNorth)
                    mostAnts = antsNorth;

                //returning spot with most ants
                if(antsNorth == mostAnts)
                    return 'N';
                if(antsEast == mostAnts)
                    return 'E';
                else
                    return 'S';
                
            }
            
            //2 way tie for nearest ant
            if(nearest == northDistance && nearest == eastDistance && nearest != 0)
            {
                int antsNorth, antsEast;

                //finding ants around north ant
                antsNorth=getAntsAreaNS(array,rowLoco-northDistance, Ant);
                
                //finding ants around east ant
                antsEast=getAntsAreaEW(array,colLoco+eastDistance, Ant);

                int mostAnts = antsNorth;
                if(antsEast>mostAnts)
                    mostAnts=antsEast;
                
                if(mostAnts == antsNorth)
                    return 'N';
                else
                    return 'E';
            }
            if(nearest == northDistance && nearest == southDistance && nearest != 0)
            {
                int antsNorth, antsSouth;

                //finding ants around north ant
                antsNorth=getAntsAreaNS(array,rowLoco-northDistance, Ant);

                //finding ants around south ant
                antsSouth=getAntsAreaNS(array,rowLoco+southDistance, Ant);

                int mostAnts = antsNorth;
                if(antsSouth>mostAnts)
                    mostAnts=antsSouth;

                if(mostAnts == antsNorth)
                    return 'N';
                else
                    return 'S';
            }
            if(nearest == northDistance && nearest == westDistance && nearest != 0)
            {
                int antsNorth, antsWest;

                //finding ants around north ant
                antsNorth=getAntsAreaNS(array,rowLoco-northDistance, Ant);

                //finding ants around west ant
                antsWest=getAntsAreaEW(array,colLoco-westDistance, Ant);

                int mostAnts = antsNorth;
                if(antsWest>mostAnts)
                    mostAnts=antsWest;

                if(mostAnts == antsNorth)
                    return 'N';
                else
                    return 'W';
            }
            if(nearest == eastDistance && nearest == southDistance && nearest != 0)
            {
                int antsEast, antsSouth;

                //finding ants around east ant
                antsEast=getAntsAreaEW(array,colLoco+eastDistance, Ant);

                //finding ants around south ant
                antsSouth=getAntsAreaNS(array,rowLoco+southDistance, Ant);

                int mostAnts = antsEast;
                if(antsSouth>mostAnts)
                    mostAnts=antsSouth;

                if(mostAnts == antsEast)
                    return 'E';
                else
                    return 'S';
            }
            if(nearest == eastDistance && nearest == westDistance && nearest != 0)
            {
                int antsEast, antsWest;

                //finding ants around east ant
                antsEast=getAntsAreaEW(array,colLoco+eastDistance, Ant);

                //finding ants around west ant
                antsWest=getAntsAreaEW(array,colLoco-westDistance, Ant);

                int mostAnts = antsEast;
                if(antsWest>mostAnts)
                    mostAnts=antsWest;

                if(mostAnts == antsEast)
                    return 'E';
                else
                    return 'W';
            }
            if(nearest == southDistance && nearest == westDistance && nearest != 0)
            {
                int antsSouth, antsWest;

                //finding ants around south ant
                antsSouth=getAntsAreaNS(array,rowLoco+southDistance, Ant);

                //finding ants around west ant
                antsWest=getAntsAreaEW(array,colLoco-westDistance, Ant);

                int mostAnts = antsSouth;
                if(antsWest>mostAnts)
                    mostAnts=antsWest;

                if(mostAnts == antsSouth)
                    return 'S';
                else
                    return 'W';
            }
            //no ties for the closest ant
            if(nearest == northDistance && northDistance != 0)
                return 'N';
            if(nearest == eastDistance && eastDistance != 0)
                return 'E';
            if(nearest == southDistance && southDistance != 0)
                return 'S';
            if(nearest == westDistance && westDistance != 0)
                return 'W';
            

        }
        //return P is used in case there is an error when looking for ants
        return 'P';
    }
    //breedChecker is used to see if it is time to breed for the beetles
    boolean breedChecker()
    {
        if(!pastCheck)
            breedCounter++;
        if(breedCounter==8 && !pastCheck)
        {
            resetBreedCounter();
            pastCheck=true;
            return true;
        }
        pastCheck=true;
        return false;

    }
    //counter to see if it is time for the beetle to starve to death
    boolean starveCheck()
    {
        if(starveCounter ==5)
            return true;
        starveCounter++;
        return false;

    }
    
    //if the beetle eats an ant, resets the starve counter to zero
    void antEaten()
    {
        starveCounter=0;
    }
    
    private
    //looks for ants around a tied ant in the North or South Direction
    int getAntsAreaNS(Character[][] array, int rowDistance, Character Ant)
    {
        int antsFound=0;
        
        //looking north
        if(rowDistance-1>=0 && array[colLoco][rowDistance-1] == Ant)
            antsFound++;
        //looking north east
        if(rowDistance-1>=0 && colLoco+1<10 && array[colLoco+1][rowDistance-1] == Ant)
            antsFound++;
        //looking east
        if(colLoco+1<10 && array[colLoco+1][rowDistance] == Ant)
            antsFound++;
        //looking south east
        if(rowDistance+1<10 && colLoco+1<10 && array[colLoco+1][rowDistance+1] == Ant)
            antsFound++;
        //looking south
        if(rowDistance+1<10 && array[colLoco][rowDistance+1] == Ant)
            antsFound++;
        //looking south west
        if(rowDistance+1<10 && colLoco-1>=0 && array[colLoco-1][rowDistance+1] == Ant)
            antsFound++;
        //looking west
        if(colLoco-1>=0 && array[colLoco-1][rowDistance] == Ant)
            antsFound++;
        //looking north west
        if(rowDistance-1>=0 && colLoco-1>=0 && array[colLoco-1][rowDistance-1] == Ant)
            antsFound++;
        
        return antsFound;
    }
    
    //looks for ants around a tied ant in the East or West Direction
    int getAntsAreaEW(Character[][] array, int colDistance, Character Ant)
    {
        int antsFound=0;

        //looking north
        if(rowLoco-1>=0 && array[colDistance][rowLoco - 1] == Ant)
            antsFound++;
        //looking north east
        if(colDistance+1<10 && rowLoco-1>=0 && array[colDistance+1][rowLoco-1] == Ant)
            antsFound++;
        //looking east
        if(colDistance+1<10 && array[colDistance+1][rowLoco] == Ant)
            antsFound++;
        //looking south east
        if(colDistance+1<10 && rowLoco+1<10 && array[colDistance+1][rowLoco+1] == Ant)
            antsFound++;
        //looking south
        if(rowLoco+1<10 && array[colDistance][rowLoco+1] == Ant)
            antsFound++;
        //looking south west
        if(colDistance-1>=0 && rowLoco+1<10 && array[colDistance-1][rowLoco+1] == Ant)
            antsFound++;
        //looking west
        if(colDistance-1>=0 && array[colDistance-1][rowLoco] == Ant)
            antsFound++;
        //looking north west
        if(colDistance-1>=0 && rowLoco-1>=0 && array[colDistance-1][rowLoco-1] == Ant)
            antsFound++;
        
        return antsFound;
    }
   
}