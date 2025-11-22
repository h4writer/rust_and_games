
fn solve(input: &[i32]) -> i32 {
    //let mut current = Vec::with_capacity(input.len());
    //return solve_recursive(&input, &mut current);

    return solve_second(&input);
}

struct Data {
    next: usize, // not used
    value: i32,
    length: i32
}

fn solve_second(input: &[i32]) -> i32 {
    if input.is_empty() {
        return 0; 
    }

    let mut list = Vec::<Data>::with_capacity(input.len()); // TODO: tree
    for i in input {

        let mut best_id = input.len();
        let mut best_length = 0;
        for k in 0..list.len() {
            if *i > list[k].value && best_length < list[k].length {
                best_length = list[k].length;
                best_id = k;
            }
        }

        if best_id == input.len() {
            list.push(Data {
                next: input.len(),
                value: *i,
                length: 1
            });
        } else {
            list.push(Data {
                next: best_id,
                value: *i,
                length: list[best_id].length+1
            });
        }
    }

    let mut best_length = 0;
    for k in 0..list.len() {
        if best_length < list[k].length {
            best_length = list[k].length;
        }
    }

    return best_length;
}

fn solve_recursive(input: &[i32], current: &mut Vec<i32>) -> i32 {
    if input.is_empty() {
        return 0; 
    }
    
    let next = input[0];

    let len_wo = solve_recursive(&input[1..], current);

    if current.is_empty() || next > *current.last().unwrap() {
        current.push(next);
        let len_with = 1 + solve_recursive(&input[1..], current);
        current.pop();

        let len_wo = solve_recursive(&input[1..], current);

        if len_with > len_wo {
            return len_with;
        } else {
            return len_wo;
        }
    } else {
        return len_wo;
    }
}




fn main() {
    let input = vec![1,4,2,7,3,5,3];
    let sol = solve(&input);
    println!("{:?}: {}", input, sol);
}

#[cfg(test)]
mod tests {
    use super::solve;

    #[test]
    fn test_empty() {
        let input: Vec<i32> = vec![];
        assert_eq!(solve(&input), 0);
    }

    #[test]
    fn test_single() {
        let input = vec![1];
        assert_eq!(solve(&input), 1);
    }

    #[test]
    fn test_increasing() {
        let input = vec![1, 2, 3, 4];
        assert_eq!(solve(&input), 4);
    }

    #[test]
    fn test_decreasing() {
        let input = vec![4, 3, 2, 1];
        assert_eq!(solve(&input), 1);
    }

    #[test]
    fn test_all_equal() {
        let input = vec![2, 2, 2];
        assert_eq!(solve(&input), 1);
    }

    #[test]
    fn test_classic_1() {
        let input = vec![10, 9, 2, 5, 3, 7, 101, 18];
        assert_eq!(solve(&input), 4);
    }

    #[test]
    fn test_classic_2() {
        let input = vec![0, 1, 0, 3, 2, 3];
        assert_eq!(solve(&input), 4);
    }

    #[test]
    fn test_all_negative_decreasing() {
        let input = vec![-1, -2, -3];
        assert_eq!(solve(&input), 1);
    }

    #[test]
    fn test_all_negative_increasing() {
        let input = vec![-3, -2, -1, 0];
        assert_eq!(solve(&input), 4);
    }

    #[test]
    fn test_mixed_signs() {
        let input = vec![3, 4, -1, 0, 6, 2, 3];
        assert_eq!(solve(&input), 4);
    }

    #[test]
    fn test_peak() {
        let input = vec![1, 3, 5, 4, 7];
        assert_eq!(solve(&input), 4);
    }

    #[test]
    fn test_waves() {
        let input = vec![2, 1, 5, 3, 6, 4, 8, 7];
        assert_eq!(solve(&input), 4);
    }

    #[test]
    fn test_all_same_large() {
        let input = vec![7, 7, 7, 7, 7];
        assert_eq!(solve(&input), 1);
    }

    #[test]
    fn test_long_classic() {
        let input = vec![0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15];
        assert_eq!(solve(&input), 6);
    }

    #[test]
    fn test_insert_in_middle() {
        let input = vec![1, 5, 2, 3, 4];
        assert_eq!(solve(&input), 4);
    }

    #[test]
    fn test_alternate_high_low() {
        let input = vec![5, 1, 6, 2, 7, 3, 8, 4];
        assert_eq!(solve(&input), 4);
    }

    #[test]
    fn test_zeros_and_one() {
        let input = vec![0, 0, 0, 1];
        assert_eq!(solve(&input), 2);
    }

    #[test]
    fn test_many_equals_with_growth() {
        let input = vec![1, 2, 3, 2, 2, 2, 4];
        assert_eq!(solve(&input), 4);
    }

    #[test]
    fn test_tail_large() {
        let input = vec![9, 1, 3, 7, 5, 6, 20];
        assert_eq!(solve(&input), 5);
    }

    #[test]
    fn test_given_example() {
        let input = vec![1, 4, 2, 7, 3, 5, 3];
        assert_eq!(solve(&input), 4);
    }

    #[test]
    fn test_small_mixed() {
        let input = vec![3, 10, 2, 1, 20];
        assert_eq!(solve(&input), 3);
    }
}
