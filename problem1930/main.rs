struct Solution {

}
use std::collections::HashSet;
use std::collections::HashMap;

impl Solution {
    pub fn combine(a: u8, b: u8) -> u16 {
        return ((a as u16) << 8) + b as u16;
    }

    pub fn count_palindromic_subsequence(s: String) -> i32 {
        let s = s.as_bytes();

        if s.len() < 3 {
            return 0;
        }

        let mut counted = HashSet::<u16>::new();

        // Compute first and last occurences of a character.
        let mut all_first = HashMap::<u8, usize>::new();
        let mut all_last = HashMap::<u8, usize>::new();

        for i in 0..s.len() {
            if !all_first.contains_key(&s[i]) {
                all_first.insert(s[i], i);
            }

            all_last.insert(s[i], i);
        }

        
        for (c, start) in all_first.iter() {
            let end = all_last[c];

            for i in start+1..end {
                counted.insert(Solution::combine(*c,s[i]));
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
