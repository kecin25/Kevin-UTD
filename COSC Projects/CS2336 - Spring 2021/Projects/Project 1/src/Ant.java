public class Ant extends Creature
{
    public 
    
    //movement function is the brain of the ant and determines where it goes
    char movement(Character[][] array, Character Ant, Character Beetle)
    {
        int northDistance=0,eastDistance=0,southDistance=0,westDistance=0;
        // check north distance for beetle
        for(int i=rowLoco;i>=0;i--)
        {
            if(array[colLoco][i] == Beetle)
            {
                northDistance=rowLoco-i;
                break;
            }
        }
        // check south distance for beetle 
        for(int i=rowLoco;i<10;i++)
        {
            if(array[colLoco][i] == Beetle)
            {
                southDistance=i-rowLoco;
                break;
            }
        }
        // check east  distance for beetle
        for(int i=colLoco;i<10;i++)
        {
            if(array[i][rowLoco] == Beetle)
            {
                eastDistance=i-colLoco;
                break;
            }
        }
        // check west distance for beetle
        for(int i= colLoco;i>=0;i--)
        {
            if(array[i][rowLoco] == Beetle)
            {
                westDistance=colLoco-i;
                break;
            }
        }
        //if there are no beetles found
        if(northDistance== 0 && eastDistance == 0 && southDistance == 0 && westDistance == 0)
            return 'G';
        
        // finding the closest beetle if at least one beetle is found
        else
        {
            
            //finds the closest beetle
            int lowest = 0;
            if(northDistance!=0)
                lowest =northDistance;
            else if(eastDistance!=0)
                lowest = eastDistance;
            else if(southDistance!=0)
                lowest = southDistance;
            else if(westDistance !=0)
                lowest = westDistance;
            
            if(eastDistance<lowest && eastDistance != 0)
                lowest=eastDistance;
            if(southDistance<lowest && southDistance != 0)
                lowest=southDistance;
            if(westDistance<lowest && westDistance != 0)
                lowest=westDistance;

           
            //returning the direction to go if there is a 4 way tie
            if (lowest == northDistance && lowest == eastDistance && lowest == southDistance && lowest == westDistance && lowest != 0)
                return 'N';

            //returning the direction to go if there is a 3 way tie
            if (lowest == eastDistance && lowest == southDistance && lowest == westDistance && lowest != 0) return 'N';
            if (lowest == northDistance && lowest == southDistance && lowest == westDistance && lowest != 0) return 'E';
            if (lowest == northDistance && lowest == eastDistance && lowest == westDistance && lowest != 0) return 'S';
            if (lowest == northDistance && lowest == eastDistance && lowest == southDistance && lowest != 0) return 'W';

            //returning the direction to go in if there is a 2 way tie
            if (lowest == northDistance && lowest == eastDistance && lowest != 0) return 'S';
            if (lowest == northDistance && lowest == southDistance && lowest != 0) return 'E';
            if (lowest == northDistance && lowest == westDistance && lowest != 0) return 'E';
            if (lowest == eastDistance && lowest == southDistance && lowest != 0 && northDistance==0 && westDistance ==0) return 'N';
            if (lowest == eastDistance && lowest == westDistance && lowest != 0) return 'N';
            if (lowest == southDistance && lowest == westDistance && lowest != 0) return 'N';

            //if there is a beetle in every direction, returns the direction to the farthest beetle
            if(northDistance!=0 && eastDistance !=0 && southDistance !=0 && westDistance !=0)
            {
                int farthest=0;
                farthest =northDistance;
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
                    return  'S';
                if(farthest == westDistance)
                    return 'W';
            }
            
            //returning the direction to go in if no tie and beetle in every direction
            if (lowest == southDistance && southDistance != 0) return 'N';
            if (lowest == westDistance && westDistance != 0) return 'E';
            if (lowest == northDistance && northDistance != 0) return 'S';
            if (lowest == eastDistance && eastDistance != 0) return 'W';
        }
        
        //if error return P
        return 'P';
    }
    //function that checks to see if it is time to breed
    boolean breedChecker()
    {
        if(!pastCheck)
            breedCounter++;
        if(breedCounter==3 && !pastCheck)
        {
            resetBreedCounter();
            pastCheck=true;
            return true;
        }
        pastCheck=true;
        return false;
    }
}