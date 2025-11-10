use std::collections::HashSet;
use std::collections::HashMap;
use std::time::Instant;
use std::time::Duration;
use std::collections::BinaryHeap;
use std::cmp::Reverse;

macro_rules! create_solver {
    ($name:ident, $typename:ty) => {

struct $name;

impl $name
{
    pub fn find_station_group(group_ids: &mut Vec<$typename>, station: $typename) -> $typename {
        let gid = station as usize;

        if group_ids[gid] == station {
            return station;
        }

        group_ids[station] = Self::find_station_group(group_ids, group_ids[station]);
        return group_ids[station];
    }

    pub fn process_queries(c: i32, connections: Vec<Vec<i32>>, queries: Vec<Vec<i32>>) -> Vec<i32> {
        let c = c as usize + 1;

        let mut groups : Vec<BinaryHeap<Reverse<$typename>>> = vec![BinaryHeap::new(); c];
        let mut group_ids : Vec<usize> = (0..c).collect();
        let mut group_sizes: Vec<usize> = vec![1; c];

        for con in connections {
            let mut root0 = Self::find_station_group(&mut group_ids, con[0] as $typename);
            let mut root1 = Self::find_station_group(&mut group_ids, con[1] as $typename);
            if root0 != root1 {
                if group_sizes[root0] < group_sizes[root1] {
                    std::mem::swap(&mut root0, &mut root1);
                }
                group_ids[root1] = root0;
                group_sizes[root0] += group_sizes[root1];
            }
        }

        for i in 0..c {
            let root = Self::find_station_group(&mut group_ids, i as $typename);
            groups[root].push(Reverse(i as $typename));
        }

        /*if false {
            println!("groups: ");
            for (gid, stations) in &groups {
                println!("{}: {:?}", gid, stations);
            }
            println!();

            println!("stations => gid");
            for i in 0..c {
                println!("{}: {}", i, group_ids[i]);
            }
            println!();
        }*/
        
        let mut solutions:Vec<i32> = Vec::with_capacity(queries.len() / 2);
        let mut down:Vec<bool> = vec![false; c];
        for query in queries {
            if query[0] == 1 {
                if down[query[1] as usize] {
                    let root = Self::find_station_group(&mut group_ids, query[1] as $typename);
                    let stations = &mut groups[root];

                    loop {
                        if stations.len() == 0 {
                            solutions.push(-1);
                            break;
                        }
                        let item = stations.peek().expect("...").0;
                        if !down[item as usize] {
                            solutions.push(item as i32);
                            break;
                        }

                        stations.pop();
                    }

                } else {
                    solutions.push(query[1]);
                }
            } else if query[0] == 2 {
                down[query[1] as usize] = true;
            }

        }

        return solutions;
    }

}
};
}


//create_solver!(SolverI32, u32);
//create_solver!(SolverI16, u16);
create_solver!(SolverI32, usize);

struct Solution {
}

impl Solution {

    pub fn process_queries(c: i32, connections: Vec<Vec<i32>>, queries: Vec<Vec<i32>>) -> Vec<i32> {
        //if (c < i16::MAX.into()) {
        //    return SolverI16::process_queries(c, connections, queries);
        //}
        return SolverI32::process_queries(c, connections, queries);
    }
}

fn main() {
    let now = Instant::now();

    let c = 5;
    let connections = vec![vec![1,2],vec![2,3],vec![3,4],vec![4,5]];
    let queries = vec![vec![1,3],vec![2,1],vec![1,1],vec![2,2],vec![1,2]];
    let sols = Solution::process_queries(c, connections, queries);

    for sol in sols {
        println!("{:?}", sol);
    }

    let c = 3;
    let connections = vec![];
    let queries = vec![vec![1,1],vec![2,1],vec![1,1]];
    let sols = Solution::process_queries(c, connections, queries);

    for sol in sols {
        println!("{:?}", sol);
    }
    
    let c = 3;
    let connections = vec![vec![3,2],vec![1,3],vec![2,1]];
    let queries = vec![vec![2,1]];
    let sols = Solution::process_queries(c, connections, queries);

    for sol in sols {
        println!("{:?}", sol);
    }

    for _ in 0..200 {
        let c = 10000;
        let mut connections = Vec::new();
        let mut queries = Vec::new();
        for i in 0..10000 {
            connections.push(vec![i,i+1]);
            queries.push(vec![1,i]);
        }
        let sols = Solution::process_queries(c, connections, queries);
    }

    println!("Elapsed: {:.2?}", now.elapsed());
}
