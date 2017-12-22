package toolService.test;

/**
 * Created by simin on 2017/10/24.
 */
public class TestExtendsB extends TestExtendsA {


    private int c;


    public  TestExtendsB (int a,int b,int c){
        super(a,b);
        this.c=c;
    }

    public int sum1(){
        return  super.sum()+this.c;
    }


}
