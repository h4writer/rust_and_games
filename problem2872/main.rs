use std::cmp;

struct Solution {

}

impl Solution {

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
        return Solution::bruteforce(n, &edges, &values, k);
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
