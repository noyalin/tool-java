package toolService.test;

/**
 * Created by simin on 2017/10/24.
 */
public class TestExtendsA {
    private int a;
    private int b;
    private int c;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public TestExtendsA(){}
    public TestExtendsA(int a,int b){
        this.a=a;
        this.b=b;
    }

    public TestExtendsA(int a,int b,int c){
        this.a=a;
        this.b=b;
        this.c=c;
    }

    public int sum(){
        return  this.a+this.b;
    }

    public int cal(){
        return this.a+this.b;
    }
}
