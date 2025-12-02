
import java.util.HashMap; 
import java.util.HashSet; 

class Solution {
    public int maxKDivisibleComponents(int n, int[][] edges, int[] values, int k) {
      return merge_or_split(n,edges,values,k);
    }

    public int merge_or_split(int n, int[][] edges, int[] values, int k) {
        var n_edges = new HashMap<Integer, HashSet<Integer>>();
        for (int i=0; i<edges.length; i++) {
            var a = edges[i][0];
            var b = edges[i][1];

            if (!n_edges.containsKey(a)) {
                n_edges.put(a, new HashSet());
            }
            n_edges.get(a).add(b);

            if (!n_edges.containsKey(b)) {
                n_edges.put(b, new HashSet());
            }
            n_edges.get(b).add(a);
        }

        return merge_or_split(n_edges, values, k);
    }

    public int merge_or_split(HashMap<Integer, HashSet<Integer>> edges, int[] values, int k) {
      var no_part = 1;

      while (!edges.isEmpty()) {
        var it = edges.entrySet().iterator();
        while (it.hasNext()) {
          var entry = it.next();
          var conns = entry.getValue();
          var from = entry.getKey();
          if (conns.size() == 0) {
            //System.out.println("has none: "+from+", val: "+values[from]+", "+ values[from]%k);
            assert(values[from] % k == 0);
            //no_part++;
            it.remove();
          }

          if (conns.size() == 1) {
            var to = conns.iterator().next();
            if (values[from] % k == 0) {
                // split
                no_part++;
                //System.out.println("split: "+from+"->"+to);
            } else {
                // merge with parent.
                values[to] = (values[to] + values[from]) % k;
                //System.out.println("merge: "+from+"->"+to+ " ("+values[to]+")");
            }
            it.remove();
            edges.get(to).remove(from);
          }
        }
      }
      return no_part;
    }

    static public void main(String[] args) {
      {
        int n = 5;
        int[][] edges = new int[][]{new int[]{0,2}, new int[]{1,2}, new int[]{1,3}, new int[]{2,4}};
        int[] values = new int[]{1,8,1,4,4};
        int k = 6;
        int sol = new Solution().maxKDivisibleComponents(n, edges, values, k);
        assert(sol == 2);
      }

      {
        int n = 7;
        int[][] edges = new int[][]{new int[]{0,1},new int[]{0,2},new int[]{1,3},new int[]{1,4},new int[]{2,5},new int[]{2,6}};
        int[] values = new int[]{3,0,6,1,5,2,1};
        int k = 3;
        int sol = new Solution().maxKDivisibleComponents(n, edges, values, k);
        assert(sol == 3);
      }

      {
        int n = 7;
        int[][] edges = new int[][]{new int[]{0,1},new int[]{0,2},new int[]{1,3},new int[]{1,4},new int[]{2,5},new int[]{2,6}};
        int[] values = new int[]{1000000000,1000000000,1000000000,1000000000,1000000000,1000000000,1000000000};
        int k = 7;
        int sol = new Solution().maxKDivisibleComponents(n, edges, values, k);
        assert(sol == 1);
      }

    }
}
