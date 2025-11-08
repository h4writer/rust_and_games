use std::collections::HashSet;
use std::collections::HashMap;
use std::time::Instant;
use std::time::Duration;

struct Solution {
}

impl Solution {
    pub fn find_station_group(group_ids: &mut Vec<i32>, station: i32) -> i32 {
        let gid = station as usize;

        if group_ids[gid] == -1 {
            group_ids[gid] = station;
            return station;
        }

        if group_ids[gid] == station {
            return station;
        }

        let gid = group_ids[gid];
        let root = Solution::find_station_group(group_ids, gid); 
        group_ids[gid as usize] = root;
        return root;
    }
    pub fn process_queries(c: i32, connections: Vec<Vec<i32>>, queries: Vec<Vec<i32>>, elapsed: &mut Duration) -> Vec<i32> {
        let c = c as usize + 1;

        let mut groups : HashMap<i32, Vec<i32>> = HashMap::new();
        let mut group_ids : Vec<i32> = Vec::with_capacity(c);
        group_ids.resize(c, -1);

        let now = Instant::now();

        for con in connections {
            let root0 = Solution::find_station_group(&mut group_ids, con[0]);
            let root1 = Solution::find_station_group(&mut group_ids, con[1]);
            if root0 != root1 {
                group_ids[root0 as usize] = root1;
            }
        }

        for i in 0..c {
            let root = Solution::find_station_group(&mut group_ids, i as i32);
            let entry = groups.entry(root).or_insert(vec![]);
            entry.push(i as i32);
        }


        for (_, stations) in &mut groups {
            stations.sort_by_key(|&w| std::cmp::Reverse(w));
        }

        *elapsed += now.elapsed();


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
        
        let mut solutions = Vec::new();
        let mut down = HashSet::new();
        for query in queries {
            if query[0] == 1 {
                if down.contains(&query[1]) {
                    let root = Solution::find_station_group(&mut group_ids, query[1]);
                    let stations = groups.get_mut(&root).expect("....");

                    loop {
                        if stations.len() == 0 {
                            solutions.push(-1);
                            break;
                        }

                        if !down.contains(stations.last().expect("...")) {
                            solutions.push(*stations.last().expect("..."));
                            break;
                        }

                        stations.pop();
                    }

                } else {
                    solutions.push(query[1]);
                }
            }
            if query[0] == 2 {
                down.insert(query[1]);
            }

        }

        return solutions;
    }
}

fn main() {
    let mut elapsed = Duration::new(0,0);

    let c = 5;
    let connections = vec![vec![1,2],vec![2,3],vec![3,4],vec![4,5]];
    let queries = vec![vec![1,3],vec![2,1],vec![1,1],vec![2,2],vec![1,2]];
    let sols = Solution::process_queries(c, connections, queries, &mut elapsed);

    for sol in sols {
        println!("{:?}", sol);
    }

    let c = 3;
    let connections = vec![];
    let queries = vec![vec![1,1],vec![2,1],vec![1,1]];
    let sols = Solution::process_queries(c, connections, queries, &mut elapsed);

    for sol in sols {
        println!("{:?}", sol);
    }
    
    let c = 3;
    let connections = vec![vec![3,2],vec![1,3],vec![2,1]];
    let queries = vec![vec![2,1]];
    let sols = Solution::process_queries(c, connections, queries, &mut elapsed);

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
        let sols = Solution::process_queries(c, connections, queries, &mut elapsed);
    }
    println!("Elapsed: {:.2?}", elapsed);
}
