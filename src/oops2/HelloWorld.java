package oops2;

/**
 * @author priyamvora
 * @created 13/04/2021
 */
interface A
{
    int x=10;
}
interface B
{
    int x=100;
}
class HelloWorld implements A,B
{
    public static void Main(String args[])
    {
        /* reference to x is ambiguous both variables are x
         * so we are using interface name to resolve the
         * variable
         */

        System.out.println(A.x);
        System.out.println(B.x);
    }
}
