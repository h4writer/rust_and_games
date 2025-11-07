struct Solution {}

impl Solution {
    pub fn max_power(stations: Vec<i32>, r: i32, k: i32) -> i64 {
        let mut max_power = 0;
        let mut current_power = 0;
        let mut i = 0;
        while i < stations.len() {
            current_power += stations[i];
            if i >= r as usize {
                current_power -= stations[i - r as usize];
            }
            max_power = max_power.max(current_power);
            i += 1;
        }
        max_power as i64
    }
}



fn main() {
    let stations = vec![1,2,4,5,0];
    let r = 1;
    let k = 2;
    let result = Solution::max_power(stations, r, k);
    println!("{}", result);
}
