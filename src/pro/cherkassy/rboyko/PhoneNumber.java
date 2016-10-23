package pro.cherkassy.rboyko;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by rboyko on 21.10.16.
 */
public class PhoneNumber {
    protected  String phone;
    protected static byte count=1;
    final  static  byte MAX_PHONE_NUMBER_LENGTH=13;
    final static byte MIN_PHONE_NUMBER_LENGTH=10;
    final static byte PHONE_NUMBER_PREFIX_BEGIN=0;
    final static byte PHONE_NUMBER_PREFIX_END=3;
    final static byte PHONE_NUMBER_OPERATOR_LENGTH=3;
    final static String PHONE_NUMBER_PREFIX="+38";

    final static String ERROR_MESSAGE="Phone number is incorrect!!";
    final static String OK_MESSAGE="Phone number is correct.";
    final static String INPUT_MESSAGE="Please enter the phone number:";
    final static String TITLE_MESSAGE="PHONE NUMBER VALIDATOR";
    final static String CALCULATION_MESSAGE="%dst round of calculation, sum is: %d\n";
    final static String FINAL_MESSAGE="Final result is: %s\n";
    PhoneNumber(String phone){
        this.phone=phone;
    }
    public static void main(String[] args){
        System.out.println(TITLE_MESSAGE);
        System.out.print(INPUT_MESSAGE);
        String inputString=null;
        PhoneNumber phoneNumber =null;
        do {
            BufferedReader br = PhoneNumber.input();

            try {
                inputString = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            phoneNumber= new PhoneNumber(inputString);
        }while (!phoneNumber.isValid());

        byte sum=(byte)phoneNumber.roundCalculation();
        String strSum="";
        switch (sum){
            case 1: strSum="One";
                    break;
            case 2: strSum="Two";
                    break;
            case 3: strSum="Three";
                    break;
            case 4: strSum="Four";
                    break;
                default: strSum=Byte.toString(sum);
        }
        System.out.printf(FINAL_MESSAGE,strSum);
    }

    public int roundCalculation(){
        byte buffLength=MIN_PHONE_NUMBER_LENGTH;
        int startPhone=0;
        if(phone.length()==MAX_PHONE_NUMBER_LENGTH) {
            buffLength = MAX_PHONE_NUMBER_LENGTH - 1;
            startPhone=1;

        }
        char[] charDigits=new char[buffLength];
        phone.getChars(startPhone, phone.length(),charDigits,0);
        return calculation(convertChars2Digits(charDigits));
    }

    public static BufferedReader input(){
        return new BufferedReader(new InputStreamReader(System.in));
    }

    public boolean validateLength(){
        return (phone.length()==MAX_PHONE_NUMBER_LENGTH || phone.length()==MIN_PHONE_NUMBER_LENGTH)?true:false;
    }

    protected int calculation(byte[] digits){
        if(digits.length==1){
            return (int)digits[0];
        }
        int sum=0;
        for (byte digit:digits){
            sum+=digit;
        }
        System.out.printf(CALCULATION_MESSAGE,count++,sum);
        digits=convertChars2Digits(String.valueOf(sum).toCharArray());
        return calculation(digits);
    }

    protected byte[] convertChars2Digits(char[] chars){
        byte[] digits=new byte[chars.length];
        byte i=0;
        for (char ch:chars){
            digits[i]=(byte)Character.getNumericValue(ch);
            i++;
        }
        return digits;
    }

    protected boolean isValid() {
        if(validateLength() && validatePhone() && validatePrefix()){
            System.out.println(OK_MESSAGE);
            return true;
        }
        System.out.print(ERROR_MESSAGE+" "+INPUT_MESSAGE);
        return false;
    }

    protected boolean validatePhone() {
        for(char ch:phone.toCharArray()){
            if(ch=='+') continue;
            if(!Character.isDigit(ch))
                return false;
        }
        return true;
    }

    protected boolean validatePrefix() {
        if(phone.length() ==MAX_PHONE_NUMBER_LENGTH && !phone.substring(PHONE_NUMBER_PREFIX_BEGIN,PHONE_NUMBER_PREFIX_END).equals(PHONE_NUMBER_PREFIX)){
                return false;
        }
        if(validateOperator()) {
            return true;
        }
        return false;
    }

    protected boolean validateOperator() {
        byte operatorStart=0;
        if(phone.length()==MAX_PHONE_NUMBER_LENGTH){
            operatorStart=3;
        }
        switch (phone.substring(operatorStart,operatorStart+PHONE_NUMBER_OPERATOR_LENGTH)){
            case "097":
            case "096":
            case "050":
            case "063":
            case "093":
            case "098":
            case "099":return true;
        }
        return false;
    }

}
