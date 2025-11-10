use std::collections::HashSet;
use std::collections::HashMap;
use std::time::Instant;
use std::time::Duration;

macro_rules! create_solver {
    ($name:ident, $typename:ty) => {

struct $name;

impl $name
{
    pub fn find_station_group(group_ids: &mut Vec<$typename>, station: $typename) -> $typename {
        let gid = station as usize;

        if group_ids[gid] == -1 {
            group_ids[gid] = station;
            return station;
        }

        if group_ids[gid] == station {
            return station;
        }

        let gid = group_ids[gid];
        let root = Self::find_station_group(group_ids, gid); 
        group_ids[gid as usize] = root;
        return root;
    }

    pub fn process_queries(c: i32, connections: Vec<Vec<i32>>, queries: Vec<Vec<i32>>) -> Vec<i32> {
        let c = c as usize + 1;

        let mut groups : HashMap<$typename, Vec<$typename>> = HashMap::new();
        let mut group_ids : Vec<$typename> = Vec::with_capacity(c);
        group_ids.resize(c, -1);

        for con in connections {
            let root0 = Self::find_station_group(&mut group_ids, con[0] as $typename);
            let root1 = Self::find_station_group(&mut group_ids, con[1] as $typename);
            if root0 != root1 {
                group_ids[root0 as usize] = root1;
            }
        }

        for i in 0..c {
            let root = Self::find_station_group(&mut group_ids, i as $typename);
            let entry = groups.entry(root).or_insert(vec![]);
            entry.push(i as $typename);
        }


        for (_, stations) in &mut groups {
            stations.sort_by_key(|&w| std::cmp::Reverse(w));
        }

        if false {
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
        }
        
        let mut solutions:Vec<i32> = Vec::with_capacity(queries.len() / 2);
        let mut down:HashSet<$typename> = HashSet::new();
        for query in queries {
            if query[0] == 1 {
                if down.contains(&(query[1] as $typename)) {
                    let root = Self::find_station_group(&mut group_ids, query[1] as $typename);
                    let stations = groups.get_mut(&root).expect("....");

                    loop {
                        if stations.len() == 0 {
                            solutions.push(-1);
                            break;
                        }
                        let item = stations.last().expect("...");
                        if !down.contains(item) {
                            solutions.push(*item as i32);
                            break;
                        }

                        stations.pop();
                    }

                } else {
                    solutions.push(query[1]);
                }
            } else if query[0] == 2 {
                down.insert(query[1] as $typename);
            }

        }

        return solutions;
    }

}
};
}


create_solver!(SolverI32, i32);
create_solver!(SolverI16, i16);

struct Solution {
}

impl Solution {

    pub fn process_queries(c: i32, connections: Vec<Vec<i32>>, queries: Vec<Vec<i32>>) -> Vec<i32> {
        /*if (c < i16::MAX.into()) {
            return SolverI16::process_queries(c, connections, queries);
        }*/
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
