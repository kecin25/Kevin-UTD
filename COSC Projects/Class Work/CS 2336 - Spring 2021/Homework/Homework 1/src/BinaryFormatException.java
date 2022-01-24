public class BinaryFormatException extends Exception
{
    private int InvalidBinaryNum;
    String error;
    
    //constructors 
    
    BinaryFormatException()
    {InvalidBinaryNum = 0; error = "";}
    
    BinaryFormatException(BinaryFormatException temp)
    {
        this.InvalidBinaryNum = temp.InvalidBinaryNum;
        this.error = temp.error;
    }
    BinaryFormatException(String temp)
    {this.error = temp;}
    
    public int getInvalidBinaryNum(){return InvalidBinaryNum;}
    
    public void setInvalidBinaryNum(int x){InvalidBinaryNum = x;}
    
    public String getMessage() { return Integer.toString(InvalidBinaryNum); }
}
