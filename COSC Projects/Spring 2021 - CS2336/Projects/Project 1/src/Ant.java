import java.util.function.ToDoubleBiFunction;

public class Ant extends Creature
{
    char movement(char array[][])
    {
        // TODO: 2/8/2021 check north 
        
        
        
        // TODO: 2/8/2021 check east 
        // TODO: 2/8/2021 check south 
        // TODO: 2/8/2021 check west 
    }
    boolean breedChecker()
    {
        if(getBreedCounter()==3)
        {
            setBreedCounter(0);
            return true;
        }
        setBreedCounter(getBreedCounter()+1);
        return false;
    }
}
