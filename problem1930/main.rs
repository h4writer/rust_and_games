struct Solution {

}
use std::collections::HashSet;

impl Solution {
    pub fn combine(a: u8, b: u8) -> u16 {
        return ((a as u16) << 8) + b as u16;
    }

    pub fn count_palindromic_subsequence(s: String) -> i32 {
        let s = s.as_bytes();

        if s.len() < 3 {
            return 0;
        }

        let mut count = 0;
        let mut counted = HashSet::<u16>::new();

        for i in 0..s.len()-2 {
            for j in i+1..s.len()-1 {
                if counted.contains(&Solution::combine(s[i], s[j])) {
                    continue;
                }

                for k in j+1..s.len() {
                    if s[i] == s[k] {
                        counted.insert(Solution::combine(s[i],s[j]));
                        break;
                    }
                }
            }
        }
         
        return counted.len() as i32;
    }
}

fn main() {
    let input = String::from("aabca");
    let sol = Solution::count_palindromic_subsequence(input.clone());
    println!("{}: {}", input, sol);
    assert_eq!(sol, 3);

    let input = String::from("adc");
    let sol = Solution::count_palindromic_subsequence(input.clone());
    println!("{}: {}", input, sol);
    assert_eq!(sol, 0);

    let input = String::from("bbcbaba");
    let sol = Solution::count_palindromic_subsequence(input.clone());
    println!("{}: {}", input, sol);
    assert_eq!(sol, 4);
    
    let input = String::from("ckafnafqo");
    let sol = Solution::count_palindromic_subsequence(input.clone());
    println!("{}: {}", input, sol);
    assert_eq!(sol, 4);
}
