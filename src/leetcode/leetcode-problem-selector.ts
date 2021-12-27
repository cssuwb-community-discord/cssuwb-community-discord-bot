import { MessageEmbed } from "discord.js";
import { inject, injectable } from "inversify";
import { TYPES } from "../types";
import { LeetcodeEmbedCreator } from "./leetcode-embed-creator";
import { LeetcodeProblemPicker } from "./leetcode-problem-picker";
import { LeetcodeProblemParser } from "./leetcode-problem-parser";
import { readFile } from "fs/promises";

const savePath: string = `./leetcodeproblems.json`;
@injectable()
export class LeetcodeProblemSelector {
  private problemPicker: LeetcodeProblemPicker;
  private embedCreator: LeetcodeEmbedCreator;
  private problemParser: LeetcodeProblemParser;
  constructor(
    @inject(TYPES.LeetcodeProblemPicker) problemPicker: LeetcodeProblemPicker,
    @inject(TYPES.LeetcodeProblemParser) problemParser: LeetcodeProblemParser,
    @inject(TYPES.LeetcodeEmbedCreator) embedCreator: LeetcodeEmbedCreator
  ) {
    this.problemPicker = problemPicker;
    this.problemParser = problemParser;
    this.embedCreator = embedCreator;
  }
  selectProblem(): Promise<MessageEmbed> {
    return new Promise<MessageEmbed>((resolve, reject) => {
      readFile(savePath, { encoding: "utf-8" })
        .then((rawFileObjectListString) => {
          const rawProblemObject = this.problemPicker
            .pickRandomProblem(rawFileObjectListString);
          const parsedProblemObject = this.problemParser
            .parseFileObject(rawProblemObject);
          const embed = this.embedCreator
            .createEmbed(parsedProblemObject)
          resolve(embed);
      })
      .catch((err) => reject(err));
    });
  }
}
