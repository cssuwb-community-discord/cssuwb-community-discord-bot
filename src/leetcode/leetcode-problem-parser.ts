import { inject, injectable } from "inversify";
import { LeetcodeDifficulty, LeetcodeProblem } from "./leetcode-problem";
@injectable()
export class LeetcodeProblemParser {
  parseRawObject(rawObject: Object): LeetcodeProblem {
    const problemName: string = rawObject["stat"]["question__title"];
    const problemNameSlug: string = rawObject["stat"]["question__title_slug"];
    const paid: boolean = rawObject["paid_only"];
    const difficulty: LeetcodeDifficulty = rawObject["difficulty"]["level"];
    const returnObject: LeetcodeProblem = new LeetcodeProblem(
      problemName,
      problemNameSlug,
      paid,
      difficulty
    );
    return returnObject;
  }
  parseFileObject(fileObject: Object): LeetcodeProblem {
    const problemName: string = fileObject["problemName"];
    const problemNameSlug: string = fileObject["problemNameSlug"];
    const difficulty: LeetcodeDifficulty = fileObject["difficulty"];
    const paid: boolean = fileObject["paid"];
    const returnObject: LeetcodeProblem = new LeetcodeProblem(
      problemName,
      problemNameSlug,
      paid,
      difficulty
    );
    return returnObject;
  }
}
