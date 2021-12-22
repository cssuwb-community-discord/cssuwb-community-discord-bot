class LeetcodeProblem {
  problemName: string;
  problemNameSlug: string;
  paid: boolean;
  difficulty: LeetcodeDifficulty;
  constructor(
    problemName: string,
    problemNameSlug: string,
    paid: boolean,
    difficulty: LeetcodeDifficulty
  ) {
    this.problemName = problemName;
    this.problemNameSlug = problemNameSlug;
    this.difficulty = difficulty;
    this.paid = paid;
  }
}
enum LeetcodeDifficulty {
  EASY = 1,
  MEDIUM = 2,
  HARD = 3,
}
function difficultyEnumToString(difficulty: LeetcodeDifficulty): string {
  switch(difficulty) {
    case LeetcodeDifficulty.EASY:
      return "Easy";
    case LeetcodeDifficulty.MEDIUM:
      return "Medium";
    case LeetcodeDifficulty.HARD:
      return "Hard";
    default:
      return "";
  }
}
export { 
  LeetcodeProblem, 
  LeetcodeDifficulty, 
  difficultyEnumToString
};
