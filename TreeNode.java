import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

public class TreeNode extends RecursiveAction {
    private int data;
    private List<TreeNode> t;
    private int max;
    private static boolean found = false;
    private static Random r = new Random();
    private static HashMap<Integer,Integer> hmap = new HashMap<Integer, Integer>();
    private int H = 0;
    private TreeNode(int d)
    {
        this.data = d;
        this.max = r.nextInt(5)+2;
        t = new ArrayList<TreeNode>();
    }
    private static TreeNode parent = null;

    public static TreeNode getParent() {
        if(parent == null)
        {
            double t1 = System.nanoTime();
            int p = r.nextInt(1000000)+1;
            int h = 0;
            parent = new TreeNode(p);
            parent.buildTree(parent, h);
            double t2 = System.nanoTime();
            System.out.println("Time Taken to construct tree :"+ (t2-t1)/1000000+" ms ");
            System.out.println("Height of the tree is: "+parent.getH());
        }
        return parent;
    }


    public static void setFound(boolean found) {
        TreeNode.found = found;
    }
    public static boolean getFound()
    {
        return found;
    }
    public int getH() {
        return H;
    }

    public List<TreeNode> getT() {
        return t;
    }

    public int getData() {
        return data;
    }

    public int getMax() {
        return max;
    }

    public void buildTree(TreeNode node, int height )
    {
        if(Main.getN() <= 0 || height>1000)
            return;
        for(int k=0; k<node.getMax() && Main.getN()>0; k++)
        {
            int put = r.nextInt(1000001)+1;
            while(hmap.containsKey(put) == true)
            {
                put = r.nextInt(1000001)+1;
            }

            TreeNode child = new TreeNode(put);
            node.getT().add(child);
            hmap.put(put, put);
            Main.setN(Main.getN()-1);
        }
        for(int i=0; i<node.getMax() && Main.getN()>0; i++)
        {
            H = Math.max(height+1, H);
            buildTree(node.getT().get(i), height+1);
        }
        return;
    }
    public TreeNode(){}

    @Override
    protected void compute() {

    }

    public void search(TreeNode node, HashMap<Integer, Integer> hash, int height, int d)
    {
        if(node.getData()==d)
        {
            synchronized (this)
            {
                hash.replace(node.getData(), height);
            }
            return;
        }
        for(int k=0; k<node.getT().size(); k++)
        {

            TreeNode child = node.getT().get(k);
            search(child, hash, height+1, d);
        }
        return;
    }




}
