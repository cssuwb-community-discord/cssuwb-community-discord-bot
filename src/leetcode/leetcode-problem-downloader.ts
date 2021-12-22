import axios from "axios";
import { LeetcodeProblemParser } from "./leetcode-problem-parser";
import { inject, injectable } from "inversify";
import { TYPES } from "../types";
import * as fs from "fs";
const problemLink = "https://leetcode.com/api/problems/all/";
const savePath = `./leetcodeproblems.json`;
@injectable()
export class LeetcodeProblemDownloader {
  private parser: LeetcodeProblemParser;
  constructor(
    @inject(TYPES.LeetcodeProblemParser) parser: LeetcodeProblemParser
  ) {
    this.parser = parser;
  }
  downloadParsedProblems(): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      axios
        .get(problemLink)
        .then((r) => r.data)
        .then((responseData) => {
          const rawProblemObjects = responseData["stat_status_pairs"];
          return rawProblemObjects.map((rawProblemObject) =>
            this.parser.parseRawObject(rawProblemObject)
          );
        })
        .then((problemObjects) => {
          console.log(savePath);
          fs.writeFile(savePath, JSON.stringify(problemObjects), (err) => {
            if (err) {
              reject(err);
            }
            resolve(savePath);
          });
        })
        .catch((exception) => {
          reject(exception);
        });
    });
  }
}
