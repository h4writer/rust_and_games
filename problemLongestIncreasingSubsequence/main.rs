

fn solve(input: &[i32]) -> i32 {
    let mut current = Vec::with_capacity(input.len());
    return solve_recursive(&input, &mut current);
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
