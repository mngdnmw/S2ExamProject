/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

/**
 *
 * @author Mecaa
 */
public class ErrorManager
{

    private static int errorCode = 0;
    private static final String unknownError = "STR_FIRST_TIME_ERROR";
    private static final String duplicateError = "STR_ERROR_2627";

    public void setErrorCode(int eCode)
    {
        errorCode = eCode;
    }

    public static int getErrorCode()
    {
        return errorCode;
    }

    public String getErrorString()
    {
        System.out.println(errorCode + "");
        switch (errorCode)
        {
            case 0:
                return null;
            case 2627:
                errorCode = 0;
                return duplicateError;
                
            default:
                errorCode = 0;
                return unknownError;
        }

    }
}
