use std::cmp;
use std::collections::HashMap;
use std::collections::HashSet;

struct Solution {

}

impl Solution {

    pub fn merge_or_split(n: i32, vec_edges: &Vec<Vec<i32>>, values: &Vec<i32>, k: i32) -> i32 {
        let mut values = values.clone();

        let mut edges = HashMap::<usize, HashSet<usize>>::new(); 
        for edge in vec_edges {
            let edge = vec![edge[0] as usize, edge[1] as usize];
            edges.entry(edge[0]).or_insert(HashSet::new()).insert(edge[1]);
            edges.entry(edge[1]).or_insert(HashSet::new()).insert(edge[0]);
        }

        let mut no_splits = 1;

        let mut leafs = Vec::<usize>::new();
        for (from, conns) in &edges {
            if conns.len() == 1 {
                leafs.push(*from);
            }
        };


        while let Some(from) = leafs.pop() {
            //println!("{:?} - {} -> {:#?}", leafs, from, edges);
            let conns : HashSet<usize> = edges.remove(&from).unwrap();
            if conns.is_empty() {
                assert!(values[from] % k == 0);
                continue;
            }
            let to : usize = *conns.iter().next().unwrap();

            if values[from] % k == 0 {
                // Split
                no_splits += 1;
                //println!("split {}->{}", from, to);
            } else {
                // Merge
                values[to] = (values[to] + values[from]) % k;
                //println!("merge {}->{}", from, to);
            }

            let conns_to = edges.get_mut(&to).unwrap();
            conns_to.remove(&from);
            if conns_to.len() == 1 {
                leafs.push(to);
            }
        }

        return no_splits
    }


    pub fn bruteforce_inner(solution: &mut Vec<i32>, edges: &[Vec<i32>], values: &Vec<i32>, k: i32) -> i32 {
        // O(2^edges * n^2)
        // O(2^edges * n * alpha(N) ) with path compression.
        let debug = false;

        if edges.len() == 0 {
            let mut count = HashMap::new();
            for i in 0..solution.len() {
                let mut master = solution[i];
                while solution[master as usize] != master {
                    master = solution[master as usize]; // Do path compression!!!
                }
                *count.entry(master).or_insert(0) += values[i];
            }

            if debug {
                let mut groups = HashMap::new();
                for i in 0..solution.len() {
                    let mut master = solution[i];
                    while solution[master as usize] != master {
                        master = solution[master as usize];
                    }
                    groups.entry(master).or_insert(Vec::new()).push(i);
                }

                let mut keys: Vec<(i32, Vec<usize>)> = groups.clone().into_iter().collect();
                keys.sort_by(|x, y| x.0.cmp(&y.0));
                for (key, value) in keys {
                    print!("{}:{:?}, ", key, value);
                }
                println!();
                println!("{:?}", count); 
            }

            for (_key, val) in count.iter() {
                if (val % k) != 0 {
                    return 0;
                }
            }

            if debug {
                println!("solution: {}", count.len());
            }

            return count.len() as i32;
        }
        
        let sol1 = Solution::bruteforce_inner(solution, &edges[0..edges.len()-1], values, k);

        let last = edges.last().unwrap();
        let key = cmp::max(last[0],last[1]) as usize;
        let before = solution[key];
        solution[key] = cmp::min(last[0],last[1]); 
        let sol2 = Solution::bruteforce_inner(solution, &edges[0..edges.len()-1], values, k);
        solution[key] = before;

        return cmp::max(sol1, sol2);
    }
        
    pub fn bruteforce(n: i32, edges: &Vec<Vec<i32>>, values: &Vec<i32>, k: i32) -> i32 {

        let mut solution:Vec<i32> = Vec::new();
        for i in 0..n {
            solution.push(i);
        }

        return Solution::bruteforce_inner(&mut solution, edges, values, k);
    }

    pub fn max_k_divisible_components(n: i32, edges: Vec<Vec<i32>>, values: Vec<i32>, k: i32) -> i32 {
        return Solution::merge_or_split(n, &edges, &values, k);
    }
}

fn main() {

    let n = 5;
    let edges = vec![vec![0,2],vec![1,2],vec![1,3],vec![2,4]];
    let values = vec![1,8,1,4,4];
    let k = 6;
    let sol = Solution::max_k_divisible_components(n, edges, values, k);
    assert_eq!(sol, 2);


    let n = 7;
    let edges = vec![vec![0,1],vec![0,2],vec![1,3],vec![1,4],vec![2,5],vec![2,6]];
    let values = vec![3,0,6,1,5,2,1];
    let k = 3;
    let sol = Solution::max_k_divisible_components(n, edges, values, k);
    assert_eq!(sol, 3);
}
