import java.util.*;
import java.util.concurrent.ForkJoinPool;
import static java.util.concurrent.ForkJoinTask.invokeAll;

public class Main extends Thread{
    private static int N;
    private static volatile HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
    private static volatile int[] h1 = new int[4];
    public static Random r = new Random();


    public static int getN() {
        return N;
    }

    public static int[] getH1() {
        return h1;
    }

    public static HashMap<Integer, Integer> getHash() {
        return hash;
    }

    public static void setN(int n) {
        N = n;
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        Random r = new Random();
        System.out.println("Enter the number of nodes: ");
        setN(in.nextInt()-1);
        TreeNode.getParent();

        System.out.println("Input the numbers to be searched(Input 0 to search these numbers) : ");
        HashMap<Integer, Integer> hmapSeq = new HashMap<Integer, Integer>();
        int seqHeight = 0;
        int input;
        ArrayList<Integer> intarr= new ArrayList<Integer>();
        while((input = in.nextInt()) != 0)
        {
            if(input>=1 && input<=1000000)
            {
                hmapSeq.put(input, -1);
                Main.getHash().put(input, -1);
                intarr.add(input);
            }
            else
            {
                System.out.println("Not within the given range");
            }

        }
        double seqStart =0, seqEnd=0;
        seqStart = System.nanoTime();
        for(int i=0; i<intarr.size(); i++)
        {
            TreeNode.getParent().search(TreeNode.getParent(), hmapSeq, seqHeight, intarr.get(i));
        }
        seqEnd = System.nanoTime();
        System.out.println("Input the technique: (a)Explicit Multithreading (b)ForkJoinPool");
        String s = in.next();

        System.out.println("Enter the number of threads to be used: ");
        int threads = in.nextInt();
        double start=0, end=0;


        if(s.compareTo("a") == 0)
        {
            if(threads == 1)
            {
                Thread t = new Thread(new Main(){
                    public void run()
                    {  for(int i=0; i<intarr.size(); i++)
                    {
                        TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[0], intarr.get(i));
                    }
                    }}
                );
                start = System.nanoTime();
                t.start();
                t.join();
                end = System.nanoTime();

            }
            if(threads == 2)
            {
                Thread t = new Thread(new Main(){
                    public void run()
                    {
                        for(int i=0; i<intarr.size()/2; i++)
                        {
                            TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[0], intarr.get(i));
                        }
                    }}
                );
                Thread tt = new Thread(new  Main(){
                    public void run()
                    {
                        for(int i=intarr.size()/2; i<intarr.size(); i++)
                        {
                            TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[1], intarr.get(i));
                        }
                    }}
                );
                start = System.nanoTime();
                t.start(); tt.start();
                t.join();tt.join();
                end = System.nanoTime();
            }
            if( threads == 3)
            {
                Thread t = new Thread(new  Main(){
                    public void run()
                    {
                        for(int i=0; i<intarr.size()/3; i++)
                        {
                            TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[0], intarr.get(i));
                        }
                    }}
                );
                Thread tt = new Thread(new  Main(){
                    public void run()
                    {
                        for(int i=intarr.size()/3; i<2*intarr.size()/3; i++)
                        {
                            TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[1],intarr.get(i) );
                        }
                    }}
                );
                Thread ttt = new Thread(new  Main(){
                    public void run()
                    {
                        for(int i=2*intarr.size()/3; i<intarr.size(); i++)
                        {
                            TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[2], intarr.get(i));
                        }
                    }}
                );
                start = System.nanoTime();
                t.start();tt.start();ttt.start();
                t.join();tt.join();ttt.join();
                end = System.nanoTime();
            }
            if( threads == 4)
            {
                Thread t = new Thread(new  Main(){
                    public void run()
                    {
                        for(int i=0; i<intarr.size()/4; i++)
                        {
                           TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[0], intarr.get(i));
                        }
                    }}
                );
                Thread tt = new Thread(new  Main(){
                    public void run()
                    {
                        for(int i=intarr.size()/4; i<2*intarr.size()/4; i++)
                        {
                            TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[1], intarr.get(i));
                        }
                    }}
                );
                Thread ttt = new Thread(new  Main(){
                    public void run()
                    {
                        for(int i=2*intarr.size()/4; i<3*intarr.size()/4; i++)
                        {
                            TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[2], intarr.get(i));
                        }
                    }}
                );
                Thread tttt = new Thread(new  Main(){
                    public void run()
                    {
                        for(int i=3*intarr.size()/4; i<intarr.size(); i++)
                        {
                            TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[3], intarr.get(i));
                        }
                    }}
                );
                start = System.nanoTime();
                t.start();tt.start();ttt.start();tttt.start();
                t.join();tt.join();ttt.join();tttt.join();
                end = System.nanoTime();
            }

        }
        else if(s.compareTo("b")==0)
        {
            ForkJoinPool f = new ForkJoinPool(threads);
            TreeNode q1, q2, q3, q4;
            if(threads == 1)
            {
                q1 = new TreeNode(){
                    public void compute()
                    {
                        for(int i=0; i<intarr.size(); i++) {
                            TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[0], intarr.get(i));
                        }
                    }};
                start = System.nanoTime();
                invokeAll(q1);
                q1.helpQuiesce();
                end = System.nanoTime();
            }
            else if(threads == 2)
            {
                q1 = new TreeNode(){
                    public void compute()
                    {
                        for(int i=0; i<intarr.size()/2; i++) {
                            TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[0], intarr.get(i));
                        }
                    }};
                q2 = new TreeNode(){
                    public void compute()
                    {
                        for(int i=intarr.size()/2; i<intarr.size(); i++) {
                            TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[1], intarr.get(i));
                        }
                    }};
                start = System.nanoTime();
                invokeAll(q1, q2);
                q1.helpQuiesce(); q2.helpQuiesce();
                end = System.nanoTime();
            }
            else if(threads == 3)
            {
                q1 = new TreeNode(){
                    public void compute()
                    {
                        for(int i=0; i<intarr.size()/3; i++) {
                            TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[0], intarr.get(i));
                        }
                    }};
                q2 = new TreeNode(){
                    public void compute()
                    {
                        for(int i=intarr.size()/3; i<2*intarr.size()/3; i++) {
                            TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[1], intarr.get(i));
                        }
                    }};
                q3 = new TreeNode(){
                    public void compute()
                    {
                        for(int i=2*intarr.size()/3; i<intarr.size(); i++) {
                            TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[2], intarr.get(i));
                        }
                    }};
                start = System.nanoTime();
                invokeAll(q1, q2, q3);
                q1.helpQuiesce(); q2.helpQuiesce();q3.helpQuiesce();
                end = System.nanoTime();
            }
            else if(threads == 4)
            {
                q1 = new TreeNode(){
                    public void compute()
                    {
                        for(int i=0; i<intarr.size()/4; i++) {
                            TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[0], intarr.get(i));
                        }
                    }};
                q2 = new TreeNode(){
                    public void compute()
                    {
                        for(int i=intarr.size()/4; i<2*intarr.size()/4; i++) {
                            TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[1], intarr.get(i));
                        }
                    }};
                q3 = new TreeNode(){
                    public void compute()
                    {
                        for(int i=2*intarr.size()/4; i<3*intarr.size()/4; i++) {
                            TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[2], intarr.get(i));
                        }
                    }};
                q4 = new TreeNode(){
                    public void compute()
                    {
                        for(int i=3*intarr.size()/4; i<intarr.size(); i++) {
                            TreeNode.getParent().search(TreeNode.getParent(), hash, getH1()[3], intarr.get(i));
                        }
                    }};
                start = System.nanoTime();
                invokeAll(q1, q2, q3, q4);
                q1.helpQuiesce(); q2.helpQuiesce();q3.helpQuiesce();q4.helpQuiesce();
                end = System.nanoTime();
            }
            else
            {
                System.out.println("Invalid number of threads: rerun program");
                System.exit(0);
            }
        }
        else
        {
            System.out.println("Invalid type of multithreading: rerun program");
            System.exit(0);
        }

        Iterator hmIterator2 = getHash().entrySet().iterator();
        while(hmIterator2.hasNext())
        {
            Map.Entry mapElement = (Map.Entry)hmIterator2.next();
            if((int)mapElement.getValue()==-1)
            {
                System.out.println((int)mapElement.getKey()+" not present");
            }
            else
            {
                System.out.println((int)mapElement.getKey()+" present at height " + (int)mapElement.getValue());
            }
        }
        System.out.println("Time taken for sequential processing: "+ (seqEnd-seqStart)/1000000+" ms");
        System.out.println("Time taken for parallel processing: "+(end-start)/1000000+" ms ");
        double SpeedUp = (seqEnd-seqStart)/(end-start);
        System.out.println("SpeedUp: "+ SpeedUp);
        double eff = SpeedUp/threads;
        System.out.println("Parallel Efficiency:"+ eff);
    }


}
