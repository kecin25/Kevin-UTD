/*Name: Project 3 - BinTree
Description: Payload used to store the data from the text file and accessed by the Generic Node class
Created by: Kevin Boudreaux
Created on: 3/31/2021
ID: KCB180002
*/
public class Payload extends Node<Payload> implements Comparable<Payload> //payload that is used to store the data
{
    private int CoefficientNum;
    private int CoefficientDen=1;
    private int exponent;
    private int InnerCoefficient;
    private String trigID ="";

    public Payload(){} //default constructor
    public Payload(Payload x) //overloaded constructor
    {
        super(x);
        CoefficientDen=x.getCoefficientDen();
        CoefficientNum=x.getCoefficientNum();
        exponent=x.getExponent();
        InnerCoefficient=x.getInnerCoefficient();
        trigID=x.getTrigID();
    }

    public void setCoefficientNum(int x){CoefficientNum=x;}
    public void setCoefficientDen(int x){CoefficientDen=x;}
    public void setExponent(int x){exponent=x;}
    public void setInnerCoefficient(int x){InnerCoefficient=x;}
    public void setTrigID(String x){trigID=x;}
    
    public int getCoefficientNum(){return CoefficientNum;}
    public int getCoefficientDen(){return CoefficientDen;}
    public int getExponent(){return exponent;}
    public int getInnerCoefficient(){return InnerCoefficient;}
    public String getTrigID(){return trigID;}
    
    public int compareTo(Payload x) //compares the exponents of the current payload and given payload
    {
        return Integer.compare(this.exponent, x.getExponent());
    }
    @Override
    public String toString() //overrides the default to string function 
    {
        String Value;
        if(CoefficientNum== 0 ) //if the numerator is 0 returns nothing
            return null;
        if(CoefficientDen <0) //if the denominator is negative, multiplies numerator and denominator by -1
        {
            CoefficientDen*=-1;
            CoefficientNum*=-1;
        }
        if(trigID.equals("")) // if there is no trig
        {
            if(CoefficientDen == 1 && exponent != 1)  // if there is no fraction and a exponent
            {
                if(CoefficientNum >0) // if the coefficient is positive
                {
                    if(CoefficientNum == 1) // if the coefficient is one
                        Value = " + x^" + exponent;
                    else // if the coefficient is not one
                        Value = " + " + CoefficientNum + "x^" + exponent;
                }
                else // if the coefficient is negative
                {
                    if(CoefficientNum == -1) // if the coefficient is negative one
                        Value = " - x^" + exponent;
                    else // if the coefficient is a negative and not negative one
                        Value = " - " + Math.abs(CoefficientNum) + "x^" + exponent;
                }
            }
            else if (CoefficientDen != 1 && exponent != 1) //if there is a fraction and not an exponent
            {
                if(CoefficientNum >0 && CoefficientDen>0) //if the fraction is positive
                {
                    if(Math.abs(CoefficientDen)==Math.abs(CoefficientNum)) //if the absolute values of the numerator and denominator are the same
                        Value = " + X^" + exponent;
                    else
                        Value = " + (" + CoefficientNum + "/" + CoefficientDen + ")x^" + exponent; //if the absolute values of the numerator and denominator are the not same
                }
                else //if the fraction is not positive
                {
                    if(Math.abs(CoefficientDen)==Math.abs(CoefficientNum)) //if the absolute values of the numerator and denominator are the same
                        Value = " - X^" + exponent;
                    else
                        Value = " - (" + Math.abs(CoefficientNum) + "/" + Math.abs(CoefficientDen) + ")x^" + exponent; //if the absolute values of the numerator and denominator are the not same
                }
            }
            else if(Math.abs(CoefficientDen) == 1) // if there is no fraction or exponent
            {
                if(CoefficientNum >0) //if the coefficient is positive
                {
                    if(CoefficientNum == 1) //if the coefficient is one
                        Value = " + x";
                    else // if the coefficient is not one
                        Value = " + " + CoefficientNum + "x";
                }
                else //if the coefficient is negative
                {
                    if(CoefficientDen == -1) //if the coefficient is negative one
                        Value = " - x";
                    else //if the coefficient is not negative one
                        Value = " - " + Math.abs(CoefficientNum) + "x";
                }
            }
            else //if the Coefficient is not one and the exponent is one
            {
                if(CoefficientNum < 0) //if the numerator is negative
                {
                    if(CoefficientDen > 0) //if the denominator is negative
                        Value = " - x";
                    else //if the denominator is positive
                        Value = " + x";
                }
                else //if the numerator is positive
                {
                    if(CoefficientDen > 0) //if the denominator is positive
                        Value = " + (" + CoefficientNum + "/" + CoefficientDen + ")x";
                    else //if the denominator is negative
                        Value = " - (" + Math.abs(CoefficientNum) + "/" + Math.abs(CoefficientDen) + ")x";
                }
            }
        }
        else //if there is a trig value
        {
            if(CoefficientDen == 1) // checks to see if there is no fraction
            {
                if(CoefficientNum >0) //if the coefficient is positive
                {
                    if(InnerCoefficient != 1) //if the inner coefficient is not one
                    {
                        if (CoefficientNum == 1) //if the numerator is one
                        {
                            Value = " + " + trigID + " " + InnerCoefficient + "x";
                        }
                        else //if the numerator is not one
                        {
                            Value = " + " + CoefficientDen + trigID + " " + InnerCoefficient + "x";
                        }
                    }
                    else //if the inner coefficient is one
                    {
                        if (CoefficientNum == 1) //if the numerator is one
                        {
                            Value = " + " + trigID + " x";
                        }
                        else //if the numerator is not one
                        {
                            Value = " + " + CoefficientDen + trigID + " x";
                        }
                    }
                }
                else //if the numerator is negative
                {
                    if(InnerCoefficient != 1) //if the inner coefficient is not one
                    {
                        if (CoefficientNum == -1) //if the numerator is negative one
                        {
                            Value = " - " + trigID + " " + InnerCoefficient + "x";
                        }
                        else //if the numerator is not negative one
                        {
                            Value = " - " + CoefficientNum + trigID + " " + InnerCoefficient + " x";
                        }
                    }
                    else //if the inner coefficient is one
                    {
                        if(CoefficientNum == -1) //if the numerator is negative one
                            Value = " - " + trigID + " x";
                        else //if the numerator not negative one
                            Value = " - " + CoefficientNum + trigID + " x";
                    }
                }
            }
            else //there is a fraction
            {
                if(CoefficientNum < 0 && CoefficientDen < 0) // if both parts of the fraction are negative
                {
                    if(CoefficientNum==CoefficientDen) //if the numerator equals the denominator
                    {
                        if(InnerCoefficient==1) //if the inner coefficient is one
                            Value = " + " + trigID + " x";
                        else  //if the inner coefficient is not one
                            Value = " - " + trigID + " " + Math.abs(InnerCoefficient) + "x";
                    }
                    else //if the numerator does not equal the denominator
                    {
                        if(InnerCoefficient==1) //if the inner coefficient is one
                            Value = " + (" + Math.abs(CoefficientNum) + "/" + Math.abs(CoefficientDen) + ")" + trigID + " x";
                        else  // if the inner coefficient not one
                            Value = " + (" + Math.abs(CoefficientNum) + "/" + Math.abs(CoefficientDen) + ")" + trigID + " " + Math.abs(InnerCoefficient) + "x";
                    }
                }
                else if(CoefficientNum < 0 || CoefficientDen < 0 ) // if only one part of the fraction is negative
                {
                    if(Math.abs(CoefficientNum)==Math.abs(CoefficientDen)) //checks to see if the numerator and denominator are the same
                    {
                        if(InnerCoefficient==1) //if the inner coefficient one
                            Value = " - " + trigID + " x";
                        else  //if the inner coefficient not one
                            Value = " - " + trigID + " " + Math.abs(InnerCoefficient) + "x";
                    }
                    else // if the numerator and the denominator are not the same
                    {
                        if(InnerCoefficient == 1) //if the inner coefficient is one
                        
                            Value = " - (" + Math.abs(CoefficientNum) + "/" + Math.abs(CoefficientDen) + ")" + trigID + " x";
                        else //if the inner coefficient is not one
                            Value = " - (" + Math.abs(CoefficientNum) + "/" + Math.abs(CoefficientDen) + ")" + trigID + " " + Math.abs(InnerCoefficient) + "x";
                    }
                }
                else // if neither parts of the fraction are negative
                {
                    if(CoefficientNum == CoefficientDen) //if the numerator and the denominator are equal
                    {
                        if(InnerCoefficient==1) //if the inner coefficient is one
                            Value = " + " + trigID + " x";
                        else //if the inner coefficient is not one
                            Value = " + " + trigID + " " + InnerCoefficient + "x";
                    }
                    else //if the numerator and the denominator are not equal
                    {
                        if(InnerCoefficient==1) //if the inner coefficient is one
                            Value = " + (" + CoefficientNum + "/" + CoefficientDen + ")" + trigID + " x";
                        else //if the inner coefficient is not one
                            Value = " + (" + CoefficientNum + "/" + CoefficientDen + ")" + trigID + " " +
                                    InnerCoefficient + "x";
                    }
                }
            }
            
        }
     return Value; 
    }
    public double toNum(int Bounds) //finds the value of the given bound
    {
        double BoundsValue;
        BoundsValue = Math.pow(Bounds, exponent); //powers the given bounds by the exponent
        BoundsValue = BoundsValue * CoefficientNum; //multiplies the given bounds by the numerator
        BoundsValue = BoundsValue / CoefficientDen; //divides the given bounds by the denominator
        return BoundsValue;
    }
}