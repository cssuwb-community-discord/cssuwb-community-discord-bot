import { MessageEmbed } from "discord.js";
import { inject, injectable } from "inversify";
import { TYPES } from "../types";
import { LeetcodeEmbedCreator } from "./leetcode-embed-creator";
import { LeetcodeFileLoader } from "./leetcode-file-loader";
@injectable()
export class LeetcodeProblemSelector {
  private fileLoader: LeetcodeFileLoader;
  private embedCreator: LeetcodeEmbedCreator;
  constructor(
    @inject(TYPES.LeetcodeFileLoader) fileLoader: LeetcodeFileLoader,
    @inject(TYPES.LeetcodeEmbedCreator) embedCreator: LeetcodeEmbedCreator
  ) {
    this.fileLoader = fileLoader;
    this.embedCreator = embedCreator;
  }
  selectProblem(): Promise<MessageEmbed> {
    return new Promise<MessageEmbed>((resolve, reject) => {
      this.fileLoader
        .pickRandomProblem()
        .then((parsedProblemObject) => {
          this.embedCreator
            .createEmbed(parsedProblemObject)
            .then((embed) => resolve(embed))
            .catch((err) => reject(err));
        })
        .catch((err) => reject(err));
    });
  }
}
