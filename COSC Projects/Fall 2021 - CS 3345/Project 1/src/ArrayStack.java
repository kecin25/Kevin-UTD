public class ArrayStack implements BKStack
{
    //array is the data structure used by the ArrayStack class to hold the input data
    private double[] array = null;
    
    @Override
    //is empty checks to see if the stack is empty
    public boolean isEmpty()
    {
        //returns true or false depending on if the first spot of array is empty or not
        return array == null;
    }

    @Override
    //push adds a new double to the top of the stack
    public void push(double d)
    {
        //if statement checks to see if the array is empty or not
        if(isEmpty())
        {
            array = new double[1];
            array[0] = d;
        }
        else
        {
            //if the array is not empty then temp is used to make the array one larger 
            double[] temp = new double[array.length+1];
            //for loop copies everything from array to temp but one spot over to make room for the new double
            System.arraycopy(array, 0, temp, 1, array.length);
            //places the given double at the front of the array
            temp[0] = d;
            //sets temp to become the new private array
            array = temp;
        }
    }

    @Override
    //pop removes the top double in the stack and returns it to the user
    public double pop()
    {
        //value is the double that is being pushed back to the user and removed from the array
        double value = array[0];
        //if there is only one double in the array then set it to zero and return value
        if(array.length == 1)
        {
            array = null;
        }
        else
        {
            //if there is more than one value in the array then set up temp to be an array one slot smaller than array
            double[] temp = new double[array.length-1];
            //for loop copies everything over from array but moves it one spot to the left 
            System.arraycopy(array, 1, temp, 0, temp.length);
            //sets temp to become the new private array with value not in it since it was popped
            array = temp;
        }
        //returns the popped value
        return value;
    }

    @Override
    //peek looks at the top double and returns it to the user
    public double peek()
    {
        //peek looks at the first/top value in the array
        return array[0];
    }
}
