import java.util.ArrayList;

public class RSA {
    public static final int START = 200;
    private int p = 11; // 서로 다른 두 소수중 하나
    private int q = 7; // 서로 다른 두 소수중 하나
    private int n; // 두 소수의 곱
    private int phiN;
    private int e;
    private int d;

    // 서로 다른 두 소수를 구한다. 두 소수 모드 100보다 커야 하며 서로 값이 다르다.
    public void makePQ(){
        p = (int) (Math.random()*START);
        q = (int) (Math.random()*START);
        while((p==q) || (p<100 || q<100) || (!isPrime(p)) || (!isPrime(q))) {
            p = (int) (Math.random()*START);
            q = (int) (Math.random()*START);
        }
    }

    // 두 수의 최대 공약수
    private int getGCD(int a, int b){
        while (b != 0){
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // 소수 판별기
    private boolean isPrime(int number) {
        if(number < 2){
            return false;
        }
        for(int i = 2; i<=Math.sqrt(number);i++){
            if (number % i == 0){
                return false;
            }
        }

        return true;
    }

    // 두 소수의 곱을 구한다
    public void makeN(){
        n = p * q;
    }

    public int getN(){
        return n;
    }

    public void print() {
        System.out.printf("(p,q)=(%d,%d)\n" + "(n,phi)=(%d,%d)\n(e,d)=(%d,%d)\n",p,q,n,phiN,e,d);
    }

    // 각 소수에서 1을 뺀 수를 곱한 phiN을 구한다
    public void makePHIN(){
        this.phiN=(p-1)*(q-1);
    }

    public int getPhiN(){
        return phiN;
    }

    // 2 <= e < phiN의 공개키를 구하며, 공개키와 phiN의 최대 공약수는 1이다. 이 관계를 서로소라 하며 공개키 후보는 여러개일수도 있기 때문에 리스트에 저장한다
    public void makeE(){
        ArrayList<Integer> es = new ArrayList<>();
        for(int i = 2; i < phiN; i++){
            if(getGCD(phiN,i) == 1){
                es.add(i);
                //System.out.print(i+",");
            }
        }
        e=es.get(es.size()-1); // 가장 큰것(후에 랜덤으로 교체)
    }

    // d*e%phiN==1을 만족하는 개인키 d를 찾는다
    public void makeD(){
        int count = 2;
        while(!(count*e%phiN==1 && count!=e)){
            count++;
        }
        d=count;
    }

    // 암호화할 문자 m을 e번 곱한 후 n의 나머지를 구한다. pow(m,e)%n
    public int toCiph(int m){
        int tot=1;
        for(int i = 0; i < e; i++){
            tot=(tot*m)%n;
        }
        return tot;
    }

    // 복호화할 문자 c를 d번 곱한 후 n의 나머지를 구한다. pow(c,d)%n
    public int toUnCiph(int c){
        int tot=1;
        for(int i = 0; i < d; i++){
            tot=(tot*c)%n;
        }
        return tot;
    }

    public static void main(String[] args){
        RSA rsa = new RSA();

        // rsa.makePQ();
        rsa.makeN();
        rsa.makePHIN();
        rsa.makeE();
        rsa.makeD();
        rsa.print();
        for(int i = 65; i < 200; i++){
            int bbb = rsa.toCiph(i);
            int ccc = rsa.toUnCiph(bbb);
            System.out.println(String.format("%d, %d, %d",i,bbb,ccc));
        }
    }
}
