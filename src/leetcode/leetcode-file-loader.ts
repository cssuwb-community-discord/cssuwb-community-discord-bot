import { inject, injectable } from "inversify";
import { TYPES } from "../types";
import { readFile } from "fs/promises";
import { LeetcodeProblem } from "./leetcode-problem";
import { LeetcodeProblemParser } from "./leetcode-problem-parser";
const savePath: string = `./leetcodeproblems.json`;
@injectable()
export class LeetcodeFileLoader {
  private parser: LeetcodeProblemParser;
  constructor(
    @inject(TYPES.LeetcodeProblemParser) parser: LeetcodeProblemParser
  ) {
    this.parser = parser;
  }
  pickRandomProblem(): Promise<LeetcodeProblem> {
    return new Promise<LeetcodeProblem>((resolve, reject) => {
      readFile(savePath, { encoding: "utf-8" })
        .then((rawFileObjectListString) => {
          const rawFileObjectList: Object[] = JSON.parse(
            rawFileObjectListString
          );
          const rawFileObjectListLength: number = rawFileObjectList.length;
          let rawFileObject: Object;
          do {
            const randomIndex: number = Math.floor(
              Math.random() * rawFileObjectListLength
            );
            rawFileObject = rawFileObjectList[randomIndex];
          } while (rawFileObject["paid"]);
          return rawFileObject;
        })
        .then((rawFileObject) => {
          const parsedProblemObject: LeetcodeProblem =
            this.parser.parseFileObject(rawFileObject);
          resolve(parsedProblemObject);
        })
        .catch((exception) => {
          reject(exception);
        });
    });
  }
}
